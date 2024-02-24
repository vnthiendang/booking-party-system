package com.swp.services;

import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.entity.Booking;
import com.swp.entity.Package;
import com.swp.entity.enums.EBookingStatus;
import com.swp.entity.enums.Location;
import com.swp.repositories.BookingRepository;
import com.swp.repositories.PackageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public Booking getByUserIdAndPackageId(Integer userId, Integer packageId) {
        return bookingRepository.findByCustomerUsIdAndPackagesId(userId, packageId);}

    public PackageDto mapPackageToPackageDto(Package packages){

        List<Integer> serviceIds = packages.getPServices().stream()
                .map(packageServiceEntity -> pServiceService.mapServiceToServiceDto(packageServiceEntity.getService()))
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
                .build();
    }

    public Boolean isValidStatus(String status) {
        for (EBookingStatus e : EBookingStatus.values()) {
            if (e.equals(status)) {
                return true;
            }
        }
        return false;
    }


    public List<Booking> getAllByDate(Date date) {
        return bookingRepository.findAllByDate(date);
    }

    public Optional<PackageDto> findPackageById(Integer packageId) {
        Optional<Package> packageOptional = packageRepository.findById(packageId);
        return packageOptional.map(this::mapPackageToPackageDto);
    }

    public List<Booking> getAllAcceptedReservationsInTime(Date startTime, Date endTime) {
        return bookingRepository.findAllApprovedReservationsInTime( startTime, endTime);
    }

    public Booking addReservation(Booking reservation) {
        return bookingRepository.save(reservation);
    }

    public Booking findBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(EntityNotFoundException::new);
    }
}
