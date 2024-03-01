package com.swp.services;

import com.swp.cms.dto.CheckSlotDto;
import com.swp.cms.dto.ListOrderDTO;
import com.swp.cms.dto.PackageDto;
import com.swp.cms.dto.ServiceDto;
import com.swp.entity.Booking;
import com.swp.entity.PService;
import com.swp.entity.Package;
import com.swp.entity.enums.EBookingStatus;
import com.swp.entity.enums.Location;
import com.swp.repositories.BookingRepository;
import com.swp.repositories.PServiceRepository;
import com.swp.repositories.PackageRepository;
import com.swp.repositories.ServiceRepository;
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
    private final PServiceRepository pServiceRepository;
    private final ServiceRepository serviceRepository;
    private final PServiceService pServiceService;
    private final ServiceRepository serviceRepository2;

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
        List<PService> services = serviceRepository.findAll();
        List<ServiceDto> serviceDtos = mapServicesToServiceDtos(services);
        List<Object[]> resultList = pServiceRepository.getListServicePackageId(id);

        if(resultList != null){
            for (Object[] result : resultList) {
                Integer serviceId = (Integer) result[0];

                for (ServiceDto serviceDto : serviceDtos) {
                    if (serviceDto.getId().equals(serviceId)) {
                        serviceDto.setSet(true);
                        break;
                    }
                }
            }
        }
        return serviceDtos;
    }
    List<ServiceDto> mapServicesToServiceDtos(List<PService> services){
        return services.stream()
                .map(this::mapServiceToServiceDto)
                .collect(Collectors.toList());
    }
    public ServiceDto mapServiceToServiceDto(PService service) {
        return ServiceDto.builder()
                .id(service.getServiceId())
                .serviceType(String.valueOf(service.getServiceType()))
                .serviceAmount(service.getServiceAmount())
                .serviceImage(service.getServiceImage())
                .price(service.getPrice())
                .description(service.getDescription())
                .serviceName(service.getServiceName())

                .build();
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
    public ListOrderDTO getOrderDetailList(Integer bookingId){
        ListOrderDTO listOrderDTO = new ListOrderDTO();

        listOrderDTO.setAPackage(bookingRepository.findPackageByBookingId(bookingId));
        int packageId = listOrderDTO.aPackage.getId();
        listOrderDTO.setPServicesList(pServiceService.getServiceInPackage(packageId));
        return listOrderDTO;
    }
    public void updateAfterBooking(Integer bookingId) {
        //update status
        Booking booking = bookingRepository.getById(bookingId);
        booking.setStatus(EBookingStatus.APPROVED);



        //
        ListOrderDTO listOrderDTO = new ListOrderDTO();

        listOrderDTO.setAPackage(bookingRepository.findPackageByBookingId(bookingId));
        int packageId = listOrderDTO.aPackage.getId();
        listOrderDTO.setPServicesList(pServiceService.getServiceInPackage(packageId));
        double totalPrice = listOrderDTO.getAPackage().getPrice();

        for(int i=0; i<listOrderDTO.getPServicesList().size();i++){
            totalPrice += listOrderDTO.getPServicesList().get(i).getPrice();
        }
        booking.setTotalCost(totalPrice);
        bookingRepository.save(booking);

        //update amount service
        //int amount =
        //int packageIds = bookingRepository.findPackageByBookingId(bookingId).getId();
        List<PService> pServiceList = pServiceService.getServiceInPackage(packageId);
        for (int i = 0; i < pServiceList.size(); i++) {
            PService pService = pServiceList.get(i);

            int n = packageRepository.countService(pService.getServiceId(), packageId);
            pService.setServiceAmount(pService.getServiceAmount() - n);
            serviceRepository2.save(pService);
        }
    }

}
