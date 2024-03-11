package com.swp.services;

import com.swp.entity.BookingPackageService;
import com.swp.repositories.BookingPServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingPServiceService {
    private final BookingPServiceRepository bookingPServiceRepository;

    public BookingPackageService findDuplicateServices(Integer bookingId, Integer serviceId) {
        return bookingPServiceRepository.findByBooking_BookingIdAndService_ServiceId(bookingId, serviceId);
    }

    public List<BookingPackageService> findAllByBookingId(Integer bookingId){
        return bookingPServiceRepository.findByBooking_BookingId(bookingId);
    }
}
