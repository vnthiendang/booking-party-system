package com.swp.cms.controller;

import com.swp.cms.dto.LocationDto;
import com.swp.cms.dto.PackageCreateDto;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.mapper.BookingMapper;
import com.swp.cms.mapper.PackageMapper;
import com.swp.cms.reqDto.BookingUpdateDto;
import com.swp.cms.reqDto.PackageUpdateDto;
import com.swp.cms.resDto.ApiMessageDto;
import com.swp.cms.resDto.ApiResponse;
import com.swp.entity.Booking;
import com.swp.entity.Package;
import com.swp.entity.enums.EBookingStatus;
import com.swp.entity.enums.EPackageStatus;
import com.swp.entity.enums.Location;
import com.swp.exception.BadRequestException;
import com.swp.exception.PackageAlreadyExistException;
import com.swp.services.BookingService;
import com.swp.services.PackageService;
import com.swp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/host")
@RequiredArgsConstructor
@Slf4j
public class HostController {

    private final PackageService packageService;
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    private PackageMapper mapper;
    @Autowired
    private BookingMapper bookingMapper;

    @GetMapping("/locations")
    public List<LocationDto> getLocations() {
        return Arrays.stream(Location.values())
                .map(LocationDto::fromLocation)
                .collect(Collectors.toList());
    }

    @PostMapping("/createPackage")
    public ResponseEntity<ApiResponse> createPackage(@Valid @RequestBody PackageCreateDto dto) {

        try{
            packageService.createPackage(dto);
            return ResponseEntity.ok(new ApiResponse("Successfully saved new package!"));
        } catch (PackageAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error"));
        }
    }
    @GetMapping("/packages")
    public List<PackageDto> listPackages() {
        Integer hostId = getCurrentHostId();

        return packageService.findAllPackageDtosByHostId(hostId);
    }

    @GetMapping("/packages/{id}")
    public PackageDto viewPackageDetails(@PathVariable Integer id) {
        Integer hostId = getCurrentHostId();

        List<PackageDto> dtoList = packageService.findAllPackageDtosByHostId(hostId);

//        PackageDto dto = packageService.findPackageById(id);
        PackageDto dto = dtoList.stream()
                .filter(packageDto -> packageDto.getId().equals(id))
                .findFirst()
                .orElse(null);

        return dto;
    }

    @PutMapping("/editPackage/{id}")
    public ResponseEntity<ApiResponse> editPackage(@PathVariable Integer id, @Valid @RequestBody PackageDto packageDto) {
        try {
            Integer hostId = getCurrentHostId();
            packageDto.setId(id);
            packageService.updatePackageByUserId(packageDto, hostId);

            return ResponseEntity.ok(new ApiResponse("Successfully updated package!"));

        } catch (PackageAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage()));
        }
    }

    @PostMapping("/deletePackage/{id}")
    public ResponseEntity<ApiResponse> deletePackage(@PathVariable Integer id) {
        try {
            Integer hostId = getCurrentHostId();
            packageService.deletePackageByIdAndUserId(id, hostId);

            return ResponseEntity.ok(new ApiResponse("Deleted package successfully!"));
        }catch (PackageAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage()));
        }
    }

    // Update booking status
    @PostMapping("/updateBookingStatus")
    public ApiMessageDto<Object> updateReservationStatus(@Valid @RequestBody BookingUpdateDto reservationUpdateDto) {
        try {
            Booking reservation = bookingService.getByUserIdAndPackageId(reservationUpdateDto.getUserId(), reservationUpdateDto.getPackageId());
            if (reservation == null) {
                throw new BadRequestException("Booking not exit");
            }
            if (Boolean.TRUE.equals(bookingService.isValidStatus(reservationUpdateDto.getStatus()))) {
                throw new BadRequestException("Invalid status");
            }
            reservation.setStatus(EBookingStatus.valueOf(reservationUpdateDto.getStatus()));
            Booking updatedReservation = bookingService.addReservation(reservation);
            return makeResponse(true, bookingMapper.fromEntityToBookingDto(updatedReservation), "Booking updated successfully");
        }catch (Exception e){
            return makeResponse(false, " Error, occurred during updating status", e.getMessage());
        }
    }

    @PostMapping("/changeStatus")
    public ApiMessageDto<Object> changeStatus(@Valid @RequestBody PackageUpdateDto dto) {
        try {
            Package packages = packageService.getById(dto.getPackageId());
            if (packages == null) {
                throw new BadRequestException("Package not exit");
            }
            //ONLY switch on or off status
          /*  if (Boolean.TRUE.equals(packageService.isValidStatus(dto.getStatus()))) {
                throw new BadRequestException("Invalid status");
            }*/
            packages.setStatus(EPackageStatus.valueOf(dto.getStatus()));
            Package updatedPackage = packageService.savePackage(packages);
            return makeResponse(true, mapper.fromEntityToPackageDto(updatedPackage), "Package updated successfully");
        }catch (Exception e){
            return makeResponse(false, " Error, occurred during updating status", e.getMessage());
        }
    }

    @GetMapping("/getByDate")
    public ApiMessageDto<Object> getBookingByDate(@RequestParam String dateStr) {

        try {
            String dateFormat = "dd-MM-yyyy";
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            try {
                Date date = formatter.parse(dateStr);
                return makeResponse(true, bookingMapper.fromEntityToReservationDtoList(bookingService.getAllByDate(date)), "Booking retrieved successfully");
            } catch (Exception e) {
                throw new BadRequestException("Invalid date format");
            }
        }catch (Exception e){
            return makeResponse(false, "An error occurred during fetching booking", e.getMessage());
        }

    }

    public ApiMessageDto<Object> makeResponse(Boolean result, Object data, String message){
        ApiMessageDto<Object> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setResult(result);
        apiMessageDto.setData(data);
        apiMessageDto.setMessage(message);
        return apiMessageDto;
    }

    private Integer getCurrentHostId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByUsername(username).getUsId();
    }
}
