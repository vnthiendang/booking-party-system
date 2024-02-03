package com.swp.repositories;

import com.swp.entity.Booking;
import com.swp.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking> {
    Booking findByCustomerUsIdAndPackagesId(Integer customer, Integer packages);

    @Query("SELECT r FROM Booking r WHERE r.startTime >= ?1 AND r.endTime <= ?2 AND r.status = 'APPROVED'")
    List<Booking> findAllApprovedReservationsInTime(Date startTime, Date endTime);

    // This is a custom query that finds all reservation has startTime and endTime in the same day as the given date
    @Query("SELECT r FROM Booking r WHERE DATE(r.startTime) = :date")
    List<Booking> findAllByDate(@Param("date") Date date);
}

