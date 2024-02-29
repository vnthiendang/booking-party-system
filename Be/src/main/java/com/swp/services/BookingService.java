package com.swp.services;

import com.swp.cms.dto.BookingDto;
import com.swp.cms.dto.CheckSlotDto;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.entity.*;
import com.swp.entity.Package;
import com.swp.entity.enums.EBookingStatus;
import com.swp.entity.enums.ESlotStatus;
import com.swp.entity.enums.Location;
import com.swp.entity.enums.ServiceType;
import com.swp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final PServiceRepository pServiceRepository;
    private final ServiceRepository serviceRepository;
    private final PServiceService pServiceService;
    private final BookingPServiceService bookingPServiceService;


    @Transactional
    public List<PackageDto> viewListPackage(){
        List<Package> packageList = packageRepository.findAllPackagesWithServices();
        //

        return packageList.stream()
                .map(this::mapPackageToPackageDto)
                .collect(Collectors.toList());
    }

    public Booking getByUserIdAndPackageId(Integer userId, Integer packageId) {
        return bookingRepository.findByCustomerUsIdAndPackagesId(userId, packageId);}


//    public List<Object[]> getServicesPackage(Integer id){
//        return pServiceRepository.getListServicePackageId(id);
//    }

    public List<ServiceDto> getServicesPackage(Integer id) {
        List<Object[]> resultList = pServiceRepository.getListServicePackageId(id);
        List<ServiceDto> dtoList = new ArrayList<>();
        for (Object[] result : resultList) {
            Integer serviceId = (Integer) result[0];
            String desc = (String) result[1];
            Double price = (Double) result[2];
            Integer amount = (Integer) result[3];
            String img = (String) result[4];
            String serviceName = (String) result[5];
            String type = (String) result[6];

            ServiceDto dto = new ServiceDto(serviceId, desc, price, amount, img, serviceName, type);
            dtoList.add(dto);
        }
        return dtoList;
    }


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
            if (e.name().equals(status)) {
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
    public boolean isPackageBookedInDateRange(CheckSlotDto bookingDto){
        if(bookingDto.getStartTime().compareTo(bookingDto.getEndTime()) > 0)
            throw new IllegalArgumentException("StartDate must before EndDate and not equal");
        Integer packageId = bookingDto.getPackagesId();
        Date startDate = bookingDto.getStartTime();
        Date endDate = bookingDto.getEndTime();
        List<Booking> bookingList = bookingRepository.findByDateRangeOverlap(startDate , endDate , packageId);
        return bookingList != null && !bookingList.isEmpty();
    }

    public List<Object[]> getBookingHistoryForCustomer(Integer userId) {
        return bookingRepository.findBookingHistoryForCustomer(userId);
    }

}
