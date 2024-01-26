package com.swp.cms.controller;

import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.services.BookingService;
import com.swp.services.PServiceService;
import com.swp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final PServiceService serviceService;
    private final UserService userService;
    private final BookingService bookingService;


    @GetMapping("/service/getall")
    public List<ServiceDto> getAllServices() {

        return serviceService.getAllServices();
    }
    @GetMapping("/list")
    public ResponseEntity<List<PackageDto>> viewListPackages() {
        List<PackageDto> packageDtoList = bookingService.viewListPackage();
        return new ResponseEntity<>(packageDtoList, HttpStatus.OK);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PackageDto> getPackageDetails(@PathVariable Integer packageId) {
        Optional<PackageDto> packageDtoOptional = bookingService.findPackageById(packageId);

        if (packageDtoOptional.isPresent()) {
            return new ResponseEntity<>(packageDtoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
