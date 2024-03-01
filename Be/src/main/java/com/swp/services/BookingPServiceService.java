package com.swp.services;

import com.swp.entity.BookingPackageService;
import com.swp.entity.PService;
import com.swp.repositories.BookingPServiceRepository;
import com.swp.repositories.BookingRepository;
import com.swp.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingPServiceService {
    private final ServiceRepository serviceRepository;
    private final BookingPServiceRepository bookingPServiceRepository;
    private final BookingRepository bookingRepository;

    public void addBookingPService(Integer serviceId, Integer amount, Integer bookingId) {
        PService pService = serviceRepository.getById(serviceId);
        double price = pService.getPrice();
        BookingPackageService b = new BookingPackageService();
        b.setBooking(bookingRepository.getById(bookingId));
        b.setService(pService);
        b.setPrice(price);
        b.setQuantity(amount);

        bookingPServiceRepository.save(b);
    }

    public BookingPackageService findDuplicateServices(Integer bookingId, Integer serviceId) {
        return bookingPServiceRepository.findByBooking_BookingIdAndService_ServiceId(bookingId, serviceId);
    }
}
