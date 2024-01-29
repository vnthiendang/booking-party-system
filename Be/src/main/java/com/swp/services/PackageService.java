package com.swp.services;

import com.swp.cms.dto.PackageCreateDto;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.entity.PService;
import com.swp.entity.Package;
import com.swp.entity.User;
import com.swp.entity.enums.Location;
import com.swp.exception.EntityNotFoundException;
import com.swp.exception.PackageAlreadyExistException;
import com.swp.repositories.PackageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageService {
    private final PackageRepository packageRepository;

    private final PServiceService serviceServiceService;
    private final UserService userService;

    @Transactional
    public PackageDto createPackage(PackageCreateDto createDto) {
        Optional<Package> existingPackage = packageRepository.findByPackageName(createDto.getName());
        if (existingPackage.isPresent()) {
            throw new PackageAlreadyExistException("This package name is already registered!");
        }

        Package aPackage = mapPackageDtoToPackage(createDto);
        aPackage.setVenueWithPrice(createDto.getVenue());
        aPackage.setCapacity(createDto.getCapacity());
        aPackage.setPackageName(createDto.getName());
        //aPackage.setPrice(createDto.getVenue().getPrice());
        aPackage.setDescription(createDto.getDescription());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User usId = userService.findUserByUsername(username);
        aPackage.setUserId(usId);

        //aPackage = packageRepository.save(aPackage);
        List<Integer> serviceIds = createDto.getServices();

        if (serviceIds != null && !serviceIds.isEmpty()){
            List<PService> savedServices = serviceServiceService.getServicesByIds(serviceIds);
            aPackage.setServices(savedServices);
        }
        Package savedPackage = packageRepository.save(aPackage);
        log.info("Successfully saved new package with ID: {}", aPackage.getId());
        return mapPackageToPackageDto(savedPackage);
    }

    private Package mapPackageDtoToPackage(PackageCreateDto dto) {
        return Package.builder()
                .packageName(formatText(dto.getName()))
                .build();
    }
    public PackageDto mapPackageToPackageDto(Package packages) {
        List<ServiceDto> serviceDtos = packages.getServices().stream()
                .map(serviceServiceService::mapServiceToServiceDto)
                .collect(Collectors.toList());

        return PackageDto.builder()
                .id(packages.getId())
                .name(packages.getPackageName())
                .venue(Location.valueOf(String.valueOf(packages.getVenue())))
                .description(packages.getDescription())
                .capacity(packages.getCapacity())
                .services(serviceDtos)
                .hostEmail(packages.getUserId().getUsername())
                .build();
    }

    public PackageDto findPackageDtoByName(String name) {
        Package aPackage = packageRepository.findByPackageName(name)
                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));
        return mapPackageToPackageDto(aPackage);
    }

    
    public PackageDto findPackageDtoById(Integer id) {
        Package aPackage = packageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));
        return mapPackageToPackageDto(aPackage);
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

    
    @Transactional
    public PackageDto updatePackage(PackageDto packageDTO) {
        log.info("Attempting to update Package with ID: {}", packageDTO.getId());

        Package existingPackage = packageRepository.findById(packageDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));

        if (PackageNameExistsAndNotSamePackage(packageDTO.getName(), packageDTO.getId())) {
            throw new PackageAlreadyExistException("This Package name is already registered!");
        }

        existingPackage.setPackageName(packageDTO.getName());

        existingPackage.setVenueWithPrice(packageDTO.getVenue());

//        packageDTO.getServiceDtos().forEach(serviceServiceService::updateService);

        packageRepository.save(existingPackage);
        log.info("Successfully updated existing Package with ID: {}", packageDTO.getId());
        return mapPackageToPackageDto(existingPackage);
    }

    
    public void deletePackageById(Integer id) {
        log.info("Attempting to delete Package with ID: {}", id);
        packageRepository.deleteById(id);
        log.info("Successfully deleted Package with ID: {}", id);
    }
    
//    public List<Package> findAllPackagesByHostId(Integer hostId) {
//        List<Package> Packages = packageRepository.findAllByHostId(hostId);
//        return (Packages != null) ? Packages : Collections.emptyList();
//    }

    
    public List<PackageDto> findAllPackageDtosByHostId(Integer hostId) {
        List<Package> Packages = packageRepository.findByUserIdWithServices(hostId);
        if (Packages != null) {
            return Packages.stream()
                    .map(this::mapPackageToPackageDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    
//    public PackageDto findPackageByIdAndHostId(Integer PackageId, Integer hostId) {
//        Package Package = packageRepository.findByIdAndHostId(PackageId, hostId)
//                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));
//        return mapPackageToPackageDto(Package);
//    }

    
    @Transactional
    public PackageDto updatePackageByUserId(PackageDto packageDTO, Integer userId) {
        Package existingPackage = packageRepository.findByIdAndUserId_UsId(packageDTO.getId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));

        if (PackageNameExistsAndNotSamePackage(packageDTO.getName(), packageDTO.getId())) {
            throw new PackageAlreadyExistException("This Package name is already registered!");
        }

        existingPackage.setPackageName(packageDTO.getName());
        existingPackage.setDescription(packageDTO.getDescription());

        existingPackage.setVenueWithPrice(packageDTO.getVenue());

        packageDTO.getServices().forEach(serviceServiceService::updateService);

        packageRepository.save(existingPackage);
        log.info("Successfully updated existing Package with ID: {} for Host ID: {}", packageDTO.getId(), userId);
        return mapPackageToPackageDTO(existingPackage);
    }

    
    public void deletePackageByIdAndUserId(Integer packageId, Integer userId) {
        Package Package = packageRepository.findByIdAndUserId_UsId(packageId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));
        packageRepository.delete(Package);
        log.info("Successfully deleted Package with ID: {} for Host ID: {}", packageId, userId);
    }
    
    public PackageDto mapPackageToPackageDTO(Package Package) {
        List<ServiceDto> serviceDtos = Package.getServices().stream()
                .map(serviceServiceService::mapServiceToServiceDto)
                .toList();

        return PackageDto.builder()
                .id(Package.getId())
                .name(Package.getPackageName())
                .venue(Package.getVenue())
                .services(serviceDtos)
                .hostEmail(Package.getUserId().getUsername())
                .build();
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

