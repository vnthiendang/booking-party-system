package com.swp.cms.controller;

import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.cms.reqDto.AvailablePackageAtTimeDto;
import com.swp.cms.resDto.GetAvailablePackageResDto;
import com.swp.entity.Booking;
import com.swp.entity.Package;
import com.swp.services.BookingService;
import com.swp.services.PServiceService;
import com.swp.services.PackageService;
import com.swp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/service/getall")
    public List<ServiceDto> getAllServices() {

        return serviceService.getAllServices();
    }

    @GetMapping("/packages")
    public List<PackageDto> viewListPackages() {
        return bookingService.viewListPackage();
    }

//    @PostMapping("/available")
//    public ResponseEntity<List<PackageDto>> getAvailablePackages(@RequestBody AvailablePackageAtTimeDto availablePackageAtTimeDto) {
//        Date startTime = availablePackageAtTimeDto.getTime();
//        // End time is 2 hours after start time
//        Date endTime = Date.from(Instant.ofEpochMilli(startTime.getTime()).plusSeconds(7200));
//        List<Package> packages = packageService.findAllPackages();
//        List<Booking> reservations = bookingService.getAllAcceptedReservationsInTime(startTime, endTime);
//
//        List<GetAvailablePackageResDto> availablePackageResponseDtoList = List.of(modelMapper.map(packages, GetAvailablePackageResDto[].class));
//
//        for (Booking reservation : reservations) {
//            for (GetAvailablePackageResDto availablePackageResponseDto : availablePackageResponseDtoList) {
//                if (Objects.equals(reservation.getPackageId().getId(), availablePackageResponseDto.getId())) {
//                    availablePackageResponseDto.setIsBooked(true);
//                }
//            }
//        }
//        //return makeResponse(true, , "Get all available successful!");
//        return new ResponseEntity<>(availablePackageResponseDtoList, HttpStatus.OK);
//    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<PackageDto> getPackageDetails(@PathVariable Integer packageId) {
        Optional<PackageDto> packageDtoOptional = bookingService.findPackageById(packageId);

        if (packageDtoOptional.isPresent()) {
            return new ResponseEntity<>(packageDtoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{packageId}/services")
    public ResponseEntity<List<ServiceDto>> getServicesForPackage(@PathVariable Integer packageId) {
        List<ServiceDto> serviceDtoList = bookingService.getServicesForPackage(packageId);

        if (!serviceDtoList.isEmpty()) {
            return new ResponseEntity<>(serviceDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
//    @GetMapping("/{serviceId}")
//    public ResponseEntity<ServiceDto> getServiceDetails(@PathVariable Integer serviceId) {
//        Optional<ServiceDto> serviceDtoOptional = pserviceService.getServiceDetails(serviceId);
//
//        if (serviceDtoOptional.isPresent()) {
//            return new ResponseEntity<>(serviceDtoOptional.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

}
