package com.swp.cms.controller;

import com.swp.cms.dto.LocationDto;
import com.swp.cms.dto.PackageCreateDto;
import com.swp.cms.dto.PackageDto;
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
@RequestMapping("/host/package")
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

    @PostMapping("/create")
    public ResponseEntity<Package> createPackage(@Valid @RequestBody PackageCreateDto dto, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Package setup failed due to validation errors: {}", result.getAllErrors());
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Package createdPackage = packageService.createPackage(dto);
            return ResponseEntity.ok(createdPackage);
        } catch (PackageAlreadyExistException e) {
            result.rejectValue("name", "package.exists", e.getMessage());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping
    public List<PackageDto> listPackages() {
        Integer hostId = getCurrentHostId();

        return packageService.findAllPackageDtosByHostId(hostId);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Package> editPackage(@PathVariable Integer id, @Valid @RequestBody PackageDto packageDto, BindingResult result) {
        try {
            Integer hostId = getCurrentHostId();
            packageDto.setId(id);
            packageService.updatePackageByHostId(packageDto, hostId);

            return ResponseEntity.ok().build();

        } catch (PackageAlreadyExistException e) {
            result.rejectValue("name", "package.exists", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Integer id) {
        Integer hostId = getCurrentHostId();
        packageService.deletePackageByIdAndHostId(id, hostId);
        return ResponseEntity.ok("Deleted package successfully!");
    }

    private Integer getCurrentHostId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByUsername(username).getUsId();
    }
}
