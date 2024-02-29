package com.swp.services;

import com.swp.cms.dto.PackageCreateDto;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.cms.mapper.PackageMapper;
import com.swp.entity.*;
import com.swp.entity.Package;
import com.swp.entity.enums.EBookingStatus;
import com.swp.entity.enums.EPackageStatus;
import com.swp.entity.enums.Location;
import com.swp.exception.PackageAlreadyExistException;
import com.swp.repositories.PServiceRepository;
import com.swp.repositories.PackageRepository;
import com.swp.repositories.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageService {
    private final PackageRepository packageRepository;

    private final PServiceService serviceServiceService;
    private final PServiceRepository pServiceRepository;
    private final ServiceRepository serviceRepository;
    private final UserService userService;

    @Autowired
    private PackageMapper modelMapper;

    @Transactional
    public void createPackage(PackageCreateDto createDto) {
        Optional<Package> existingPackage = packageRepository.findByPackageName(createDto.getName());
        if (existingPackage.isPresent()) {
            throw new PackageAlreadyExistException("This package name is already registered!");
        }

        Package aPackage = mapPackageDtoToPackage(createDto);
        aPackage.setVenueWithPrice(createDto.getVenue());
        aPackage.setCapacity(createDto.getCapacity());
        aPackage.setPackageName(createDto.getName());
        aPackage.setStatus(EPackageStatus.OFF);
        aPackage.setDescription(createDto.getDescription());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User usId = userService.findUserByUsername(username);
        aPackage.setUserId(usId);

        //aPackage = packageRepository.save(aPackage);
        Package savedPackage = packageRepository.save(aPackage);
        List<Integer> serviceIds = serviceRepository.getServiceIds();

        if (serviceIds != null && !serviceIds.isEmpty()) {
            List<PService> savedServices = serviceServiceService.getServicesByIds(serviceIds);

            // Create PackageServiceEntity instances and associate them with the package
            List<PackageServiceEntity> packageServiceEntities = new ArrayList<>();
            for (PService service : savedServices) {
                PackageServiceEntity packageServiceEntity = new PackageServiceEntity();
                packageServiceEntity.setPackages(savedPackage);
                packageServiceEntity.setService(service);
                packageServiceEntities.add(packageServiceEntity);
            }

            // Save the PackageServiceEntity instances
            pServiceRepository.saveAll(packageServiceEntities);

            // Refresh the package to get the updated services
            savedPackage = packageRepository.findById(savedPackage.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid action"));
        }

        log.info("Successfully saved new package with ID: {}", savedPackage.getId());
    }

    private Package mapPackageDtoToPackage(PackageCreateDto dto) {
        return Package.builder()
                .packageName(formatText(dto.getName()))
                .build();
    }
    public PackageDto mapPackageToPackageDto(Package packages) {
        List<Integer> serviceIds = packages.getPServices().stream()
                .map(packageServiceEntity -> serviceServiceService.mapServiceToServiceDto(packageServiceEntity.getService()))
                .map(ServiceDto::getId)
                .collect(Collectors.toList());

        return PackageDto.builder()
                .id(packages.getId())
                .packageName(packages.getPackageName())
                .venue(Location.valueOf(String.valueOf(packages.getVenue())))
                .description(packages.getDescription())
                .capacity(packages.getCapacity())
                .services(serviceIds)
                .price(packages.getPrice())
                .hostEmail(packages.getUserId().getUsername())
                .status(packages.getStatus().name())
                .build();
    }

    public Package getById(Integer id) {
        return packageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Boolean isBooked(Integer id) {
        Package table = packageRepository.findById(id).orElse(null);
        return table.getStatus().name().equals("BOOKED");
    }

    public Boolean isValidStatus(String status) {
        for (EPackageStatus e : EPackageStatus.values()) {
            if (e.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
    public Package savePackage(Package packages) {
        return packageRepository.save(packages);
    }
    public Optional<Package> findPackageById(Integer id) {
        return packageRepository.findById(id);
    }

    
    public List<PackageDto> findAllPackages() {
        List<Package> packages = packageRepository.findAll();
        return packages.stream()
                .map(this::mapPackageToPackageDto)
                .collect(Collectors.toList());
    }

    public List<Package> getAll() {
        return packageRepository.findAll();
    }

    @Transactional
    public PackageDto updatePackage(PackageDto packageDTO) {
        Package existingPackage = packageRepository.findById(packageDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        if (PackageNameExistsAndNotSamePackage(packageDTO.getPackageName(), packageDTO.getId())) {
            throw new PackageAlreadyExistException("This Package name is already registered!");
        }

        existingPackage.setPackageName(packageDTO.getPackageName());

        existingPackage.setVenueWithPrice(packageDTO.getVenue());

//        packageDTO.getServiceDtos().forEach(serviceServiceService::updateService);

        packageRepository.save(existingPackage);
        log.info("Successfully updated existing Package with ID: {}", packageDTO.getId());
        return mapPackageToPackageDto(existingPackage);
    }

    
    public List<PackageDto> findAllPackageDtosByHostId(Integer hostId) {
        List<Package> packages = packageRepository.findByUserIdWithServices(hostId);
        if (packages != null) {
            return packages.stream()
                    .map(this::mapPackageToPackageDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public boolean validateNumberOfServices(List<Integer> serviceIds) {
        List<PService> availableServices = serviceRepository.findAll();

        // Check if the number of requested services is greater than available services
        return serviceIds.size() <= availableServices.size();
    }

    @Transactional
    public void updatePackageByUserId(PackageDto updateDto, Integer userId) {

        Package existingPackage = packageRepository.findByIdAndUserId_UsId(updateDto.getId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        if (existingPackage.getStatus().equals(EPackageStatus.BOOKED)) {
            throw new PackageAlreadyExistException("This package is being booked by customer, can not be edited!");
        }

        if (!existingPackage.getPackageName().equals(updateDto.getPackageName())) {
            Optional<Package> existingPackageWithName = packageRepository.findByPackageName(updateDto.getPackageName());
            if (existingPackageWithName.isPresent()) {
                throw new PackageAlreadyExistException("This package name is already registered!");
            }
        }

        // Update basic package information
        existingPackage.setPackageName(updateDto.getPackageName());
        existingPackage.setDescription(updateDto.getDescription());
        existingPackage.setVenueWithPrice(updateDto.getVenue());
        existingPackage.setCapacity(updateDto.getCapacity());
        existingPackage.setStatus(EPackageStatus.OFF);

//        // Validate the number of services
//        if (!validateNumberOfServices(updateDto.getServices())) {
//            throw new IllegalArgumentException("Number of requested services exceeds available services.");
//        }

        // Save the updated package
        packageRepository.save(existingPackage);
    }

    public void deletePackageByIdAndUserId(Integer packageId, Integer userId) {
        Package packages = packageRepository.findByIdAndUserId_UsId(packageId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));
        if (packages.getStatus().equals(EPackageStatus.BOOKED)) {
            throw new PackageAlreadyExistException("This package is being booked by customer, can not be deleted!");
        }
        packageRepository.delete(packages);
        log.info("Successfully deleted Package with ID: {} for Host ID: {}", packageId, userId);
    }

    private boolean PackageNameExistsAndNotSamePackage(String name, Integer PackageId) {
        Optional<Package> existingPackageWithSameName = packageRepository.findByPackageName(name);
        if (existingPackageWithSameName.isEmpty()) return false;
        existingPackageWithSameName.get();
        return true;
    }

    private String formatText(String text) {
        return StringUtils.capitalize(text.trim());
    }
}

