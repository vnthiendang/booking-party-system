package com.swp.services;

import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.cms.dto.UserDto;
import com.swp.entity.Booking;
import com.swp.entity.PService;
import com.swp.entity.Package;
import com.swp.repositories.BookingRepository;
import com.swp.repositories.PackageRepository;
import com.swp.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    private PackageRepository packageRepository;
    private ServiceRepository serviceRepository;
    public List<PackageDto> viewListPackage(){
        List<Package> packageList = packageRepository.findAll();
        return packageList.stream()
                .map(this::mapPackageToPackageDto)
                .collect(Collectors.toList());
    }
    public PackageDto mapPackageToPackageDto(Package aPackage){
        return PackageDto.builder()
                .id(aPackage.getId())
                         .name(aPackage.getPackageName())
                         .description(aPackage.getDescription())
                         .checkinTime(aPackage.getCheckinTime())
                         .checkoutTime(aPackage.getCheckoutTime())
                         .capacity(aPackage.getCapacity())
                         .venue(aPackage.getVenue())
                         .serviceDtos(mapServicesToDto(aPackage.getServices()))
                         .build();
    }
    public List<PService> getAllServices() {
        return serviceRepository.findAll();
    }
    private List<ServiceDto> mapServicesToDto(List<PService> services) {
        return services.stream()
                .map(service -> ServiceDto.builder()
                        .id(service.getServiceId())
                        .packageId(service.getPackageId())
                        .serviceType(service.getServiceType())
                        .serviceAmount(service.getServiceAmount())
                        .price(service.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
    public List<ServiceDto> viewlistService(List<PService> services) {
        return mapServicesToDto(services);
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



    private ServiceDto mapServiceToServiceDto(PService pService) {
        return ServiceDto.builder()
                .id(pService.getServiceId())
                .packageId(pService.getPackageId())
                .serviceType(pService.getServiceType())
                .serviceAmount(pService.getServiceAmount())
                .price(pService.getPrice())
                .build();
    }






}
