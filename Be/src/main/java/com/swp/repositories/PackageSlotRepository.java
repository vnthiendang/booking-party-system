package com.swp.repositories;

import com.swp.entity.PackageSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PackageSlotRepository extends JpaRepository<PackageSlot, Integer> {
    List<PackageSlot> findAllByPackagesIdAndBookingTime(Integer packageId, Date date);
}
