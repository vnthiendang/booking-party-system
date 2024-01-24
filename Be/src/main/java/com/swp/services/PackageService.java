package com.swp.services;

import com.swp.cms.dto.PackageCreateDto;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.entity.Host;
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
import org.springframework.security.core.Authentication;
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
    private final HostService hostService;

    @Transactional
    public Package createPackage(PackageCreateDto createDto) {
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
        aPackage.setCheckinTime(createDto.getCheckinTime());
        aPackage.setCheckoutTime(createDto.getCheckoutTime());

        //String username = String.valueOf(SecurityContextHolder.getContext().getAuthentication());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User userDetails = (User) authentication.getPrincipal();
//        String usName = userDetails.getEmail();
//        Host host = hostService.findByUser(userService.findUserByUsername(usName));
//        aPackage.setHost(host);

        //aPackage = packageRepository.save(aPackage);
        List<Integer> serviceIds = createDto.getServiceDtos();

        if (serviceIds != null && !serviceIds.isEmpty()){
            List<PService> savedServices = serviceServiceService.getServicesByIds(serviceIds);
            aPackage.setServices(savedServices);
        }
        Package savedPackage = packageRepository.save(aPackage);
        log.info("Successfully saved new package with ID: {}", aPackage.getId());
        return savedPackage;
    }

    private Package mapPackageDtoToPackage(PackageCreateDto dto) {
        return Package.builder()
                .packageName(formatText(dto.getName()))
                .build();
    }
    public PackageDto mapPackageToPackageDto(Package packages) {
        List<ServiceDto> serviceDtos = packages.getServices().stream()
                .map(serviceServiceService::mapServiceToServiceDto)
                .collect(Collectors.toList());  // collect results to a list

        return PackageDto.builder()
                .id(packages.getId())
                .name(packages.getPackageName())
                .venue(Location.valueOf(String.valueOf(packages.getVenue())))
                .serviceDtos(serviceDtos)
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
//        List<Package> Packages = packageRepository.findAllByHost_Id(hostId);
//        return (Packages != null) ? Packages : Collections.emptyList();
//    }

    
//    public List<PackageDto> findAllPackageDtosByHostId(Integer hostId) {
//        List<Package> Packages = packageRepository.findAllByHost_Id(hostId);
//        if (Packages != null) {
//            return Packages.stream()
//                    .map(this::mapPackageToPackageDto)
//                    .collect(Collectors.toList());
//        }
//        return Collections.emptyList();
//    }

    
//    public PackageDto findPackageByIdAndManagerId(Integer PackageId, Integer hostId) {
//        Package Package = packageRepository.findByIdAndHost_Id(PackageId, hostId)
//                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));
//        return mapPackageToPackageDto(Package);
//    }

    
//    @Transactional
//    public PackageDto updatePackageByHostId(PackageDto packageDTO, Integer hostId) {
//        log.info("Attempting to update Package with ID: {} for Manager ID: {}", packageDTO.getId(), hostId);
//
//        Package existingPackage = packageRepository.findByIdAndHost_Id(packageDTO.getId(), hostId)
//                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));
//
//        if (PackageNameExistsAndNotSamePackage(packageDTO.getName(), packageDTO.getId())) {
//            throw new PackageAlreadyExistException("This Package name is already registered!");
//        }
//
//        existingPackage.setPackageName(packageDTO.getName());
//
//        existingPackage.setVenueWithPrice(packageDTO.getVenue());
//
////        packageDTO.getServiceDtos().forEach(serviceServiceService::updateService);
//
//        packageRepository.save(existingPackage);
//        log.info("Successfully updated existing Package with ID: {} for Host ID: {}", packageDTO.getId(), hostId);
//        return mapPackageTopackageDTO(existingPackage);
//    }

    
//    public void deletePackageByIdAndHostId(Integer packageId, Integer hostId) {
//        log.info("Attempting to delete Package with ID: {} for Manager ID: {}", packageId, hostId);
//        Package Package = packageRepository.findByIdAndHost_Id(packageId, hostId)
//                .orElseThrow(() -> new EntityNotFoundException("Package not found".getClass()));
//        packageRepository.delete(Package);
//        log.info("Successfully deleted Package with ID: {} for Manager ID: {}", packageId, hostId);
//    }

    private Package mapPackageCreateDtoToPackage(PackageCreateDto dto) {
        return Package.builder()
                .packageName(formatText(dto.getName()))
                .build();
    }

    
    public PackageDto mapPackageTopackageDTO(Package Package) {
        List<ServiceDto> serviceDtos = Package.getServices().stream()
                .map(serviceServiceService::mapServiceToServiceDto)  // convert each Room to RoomDTO
                .toList();  // collect results to a list

        return PackageDto.builder()
                .id(Package.getId())
                .name(Package.getPackageName())
                .venue(Package.getVenue())
                .serviceDtos(serviceDtos)
//                .managerUsername(Package.getHost().getUser().getUsername())
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

