package com.swp.repositories;

import com.swp.entity.PackageSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageSlotRepository extends JpaRepository<PackageSlot, Integer> {
}
