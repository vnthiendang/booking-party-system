package com.swp.cms.controller;

import com.swp.cms.dto.BookingDto;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.cms.mapper.BookingMapper;
import com.swp.cms.reqDto.AvailablePackageAtTimeDto;
import com.swp.cms.reqDto.BookingUpdateDto;
import com.swp.cms.resDto.ApiMessageDto;
import com.swp.cms.resDto.ApiResponse;
import com.swp.cms.resDto.GetAvailablePackageResDto;
import com.swp.entity.Booking;
import com.swp.entity.Package;
import com.swp.entity.User;
import com.swp.entity.enums.EBookingStatus;
import com.swp.exception.BadRequestException;
import com.swp.services.BookingService;
import com.swp.services.PServiceService;
import com.swp.services.PackageService;
import com.swp.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final PServiceService serviceService;
    private final UserService userService;
    private final BookingService bookingService;
    private final PackageService packageService;
    private final PServiceService pserviceService;

    @Autowired
    private BookingMapper mapper;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/service/getall")
    public List<ServiceDto> getAllServices() {

        return serviceService.getAllServices();
    }

    @GetMapping("/packages")
    public List<PackageDto> viewListPackages() {
        return bookingService.viewListPackage();
    }

    @PostMapping("/available")
    public ApiMessageDto<Object> getAvailablePackages(@RequestBody AvailablePackageAtTimeDto availablePackageAtTimeDto) {
        Date startTime = availablePackageAtTimeDto.getTime();
        // End time is 4 hours after start time
        Date endTime = Date.from(Instant.ofEpochMilli(startTime.getTime()).plusSeconds(2*7200));
        List<Package> packages = packageService.getAll();
        List<Booking> reservations = bookingService.getAllAcceptedReservationsInTime(startTime, endTime);

        List<GetAvailablePackageResDto> availablePackageResponseDtoList = List.of(modelMapper.map(packages, GetAvailablePackageResDto[].class));

        for (Booking reservation : reservations) {
            for (GetAvailablePackageResDto availablePackageResponseDto : availablePackageResponseDtoList) {
                if (Objects.equals(reservation.getPackages().getId(), availablePackageResponseDto.getId())) {
                    availablePackageResponseDto.setIsBooked(true);
                }
            }
        }
        return makeResponse(true, availablePackageResponseDtoList, "Get all available packages successful!");
//        return new ResponseEntity.ok(new ApiResponse("Successfully get available package!"));
    }

    public ApiMessageDto<Object> makeResponse(Boolean result, Object data, String message){
        ApiMessageDto<Object> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setResult(result);
        apiMessageDto.setData(data);
        apiMessageDto.setMessage(message);
        return apiMessageDto;
    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<PackageDto> getPackageDetails(@PathVariable Integer packageId) {
        Optional<PackageDto> packageDtoOptional = bookingService.findPackageById(packageId);

        if (packageDtoOptional.isPresent()) {
            return new ResponseEntity<>(packageDtoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("service/{serviceId}")
    public ResponseEntity<ServiceDto> getServiceDetails(@PathVariable Integer serviceId) {
        Optional<ServiceDto> serviceDto = pserviceService.getServicesById(serviceId);

        if (serviceDto.isPresent()) {
            return new ResponseEntity<>(serviceDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bookPackage")
    public ApiMessageDto<Object> bookPackage(@Valid @RequestBody BookingDto bookReservationDto) {
        try {
            // Check if package exists
            packageService.findPackageById(bookReservationDto.getPackagesId());

            // Check if package is booked
            if (Boolean.TRUE.equals(packageService.isBooked(bookReservationDto.getPackagesId()))) {
                throw new BadRequestException("Package is already booked");
            }
            // Check if start time is before end time
            if (bookReservationDto.getStartTime().after(bookReservationDto.getEndTime())) {
                throw new BadRequestException("Start time is after end time");
            }
            Package aPackage = packageService.getById(bookReservationDto.getPackagesId());
            // Check if party size is greater than package capacity
            if (bookReservationDto.getPartySize() > aPackage.getCapacity()) {
                throw new BadRequestException("Party size is greater than package capacity");
            }
            Booking reservation = modelMapper.map(bookReservationDto, Booking.class);
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserByUsername(username);
            // Set package to booked
            reservation.setPackages(aPackage);
            reservation.setCustomer(user);
            reservation.setStatus(EBookingStatus.PENDING);
            reservation.setBookingDate(new Date());
            reservation.setPartySize(bookReservationDto.getPartySize());
            Booking savedReservation = bookingService.addReservation(reservation);
            return makeResponse(true, mapper.fromEntityToBookingDto(savedReservation), "Booking added successfully");
        }catch (Exception e){
            return makeResponse(false, "An error occurred during booking", e.getMessage());
        }
    }

    // Update reservation status
    @PostMapping("/update")
    public ApiMessageDto<Object> updateReservationStatus(@Valid @RequestBody BookingUpdateDto reservationUpdateDto) {
        try {
            Booking reservation = bookingService.getByUserIdAndPackageId(reservationUpdateDto.getUserId(), reservationUpdateDto.getPackageId());
            if (reservation == null) {
                throw new BadRequestException("Booking does not exist");
            }
            if (Boolean.TRUE.equals(bookingService.isValidStatus(reservationUpdateDto.getStatus()))) {
                throw new BadRequestException("Invalid status");
            }
            reservation.setStatus(EBookingStatus.valueOf(reservationUpdateDto.getStatus()));
            Booking updatedReservation = bookingService.addReservation(reservation);
            return makeResponse(true, mapper.fromEntityToBookingDto(updatedReservation), "Booking updated successfully");
        }catch (Exception e){
            return makeResponse(false, "An error occurred during updating status", e.getMessage());
        }
    }

    @GetMapping("/getByDate")
    public ApiMessageDto<Object> getBookingByDate(@RequestParam String dateStr) {

        try {
            String dateFormat = "dd-MM-yyyy";
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            try {
                Date date = formatter.parse(dateStr);
                return makeResponse(true, mapper.fromEntityToReservationDtoList(bookingService.getAllByDate(date)), "Booking retrieved successfully");
            } catch (Exception e) {
                throw new BadRequestException("Invalid date format");
            }
        }catch (Exception e){
            return makeResponse(false, "An error occurred during fetching booking", e.getMessage());
        }

    }

}
