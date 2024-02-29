package com.swp.repositories;

import com.swp.entity.PService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<PService, Integer> {
    @Query("SELECT serviceId FROM PService")
    List<Integer> getServiceIds();
}
