package com.swp.repositories;

import com.swp.entity.BookingPackageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingPServiceRepository extends JpaRepository<BookingPackageService, Integer> {
    BookingPackageService findByBooking_BookingIdAndService_ServiceId(Integer bookingId, Integer serviceId);
    List<BookingPackageService> findByBooking_BookingId(Integer bookingId);

}
