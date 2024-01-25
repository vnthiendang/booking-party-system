package com.swp.services;

import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.UserDto;
import com.swp.entity.Booking;
import com.swp.entity.Package;
import com.swp.repositories.BookingRepository;
import com.swp.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    private PackageRepository packageRepository;
    public List<PackageDto> viewListPackage(){
        List<Package> packageList = packageRepository.findAll();
        return packageList.stream()
                .map(this::mapPackageToPackageDto)
                .collect(Collectors.toList());
    }
    private PackageDto mapPackageToPackageDto(Package aPackage){
        return PackageDto.builder()
                .id(aPackage.getId())
                .name(aPackage.getPackageName())
                .venue(aPackage.getVenue())
                .description(aPackage.getDescription())
                .capacity(aPackage.getCapacity())
                .build();
    }

}
