package com.swp.repositories;

import com.swp.entity.PackageServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PServiceRepository extends JpaRepository<PackageServiceEntity, Integer> {
    @Query(value = "SELECT service_id " +
            "FROM package_service b " +
            "WHERE b.package_id = :id",
            nativeQuery = true)
    List<Integer> getListServicePackageId(@Param("id") Integer id);
}
