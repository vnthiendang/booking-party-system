package com.swp.repositories;

import com.swp.entity.PService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<PService, Integer> {
    @Query("SELECT serviceId FROM PService")
    List<Integer> getServiceIds();
    @Query("SELECT s FROM PService s WHERE s.serviceId not in " +
            "(select y.service.serviceId from PackageServiceEntity y where y.packages.id=:packageId)")
    List<PService> getServiceNotInPackage(@Param("packageId") Integer packageId);

    @Query("SELECT s FROM PService s WHERE s.serviceId in " +
            "(select y.service.serviceId from PackageServiceEntity y where y.packages.id=:packageId)")
    List<PService> getServiceInPackage(@Param("packageId") Integer packageId);

    @Query("SELECT s FROM PService s WHERE s.serviceId in " +
            "(select y.service.serviceId from BookingPackageService y where y.id=:bookingId)")
    List<PService> getServiceInBooking(@Param("bookingId") Integer bookingId);
}
