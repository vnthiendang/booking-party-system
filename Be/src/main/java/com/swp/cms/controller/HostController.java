package com.swp.cms.controller;

import com.swp.cms.dto.LocationDto;
import com.swp.cms.dto.PackageCreateDto;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.resDto.ApiResponse;
import com.swp.entity.Package;
import com.swp.entity.enums.Location;
import com.swp.exception.PackageAlreadyExistException;
import com.swp.services.BookingService;
import com.swp.services.PackageService;
import com.swp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        Integer hostId = getCurrentHostId();
        packageService.deletePackageByIdAndUserId(id, hostId);
        return ResponseEntity.ok(new ApiResponse("Deleted package successfully!"));
    }

    private Integer getCurrentHostId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByUsername(username).getUsId();
    }
}
