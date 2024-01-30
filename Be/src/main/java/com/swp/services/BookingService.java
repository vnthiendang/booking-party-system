package com.swp.services;

import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.entity.PService;
import com.swp.entity.Package;
import com.swp.repositories.BookingRepository;
import com.swp.repositories.PackageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PackageRepository packageRepository;
    private final PServiceService pServiceService;
    @Transactional
    public List<PackageDto> viewListPackage(){
        List<Package> packageList = packageRepository.findAllPackagesWithServices();

        return packageList.stream()
                .map(this::mapPackageToPackageDto)
                .collect(Collectors.toList());
    }

    public PackageDto mapPackageToPackageDto(Package aPackage){
        List<ServiceDto> serviceDtos = aPackage.getServices().stream()
                .map(pServiceService::mapServiceToServiceDto)
                .collect(Collectors.toList());

        return PackageDto.builder()
                .id(aPackage.getId())
                .name(aPackage.getPackageName())
                .venue(aPackage.getVenue())
                .services(serviceDtos)
                .description(aPackage.getDescription())
                .capacity(aPackage.getCapacity())
                .hostEmail(aPackage.getUserId().getUsername())
                .build();
    }
    public Optional<PackageDto> findPackageById(Integer packageId) {
        Optional<Package> packageOptional = packageRepository.findById(packageId);
        return packageOptional.map(this::mapPackageToPackageDto);
    }

    public List<ServiceDto> getServicesForPackage(Integer packageId) {
        Optional<Package> packageOptional = packageRepository.findById(packageId);

        return packageOptional.map(pkg ->
                pkg.getServices().stream()
                        .map(this::mapServiceToServiceDto)
                        .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
    }

    // ... các phương thức khác ...

    private ServiceDto mapServiceToServiceDto(PService pService) {
        return ServiceDto.builder()
                .id(pService.getServiceId())
                .serviceType(pService.getServiceType())
                .serviceAmount(pService.getServiceAmount())
                .price(pService.getPrice())
                .build();
    }
}
