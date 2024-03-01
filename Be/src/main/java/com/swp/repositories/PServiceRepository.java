package com.swp.repositories;

import com.swp.entity.PackageServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PServiceRepository extends JpaRepository<PackageServiceEntity, Integer> {
    @Query(value = "SELECT p.service_id " +
            "FROM package_service ps " +
            "INNER JOIN pservice p ON ps.service_id = p.service_id" +
            " WHERE ps.package_id = :id",
            nativeQuery = true)
    List<Object[]> getListServicePackageId(@Param("id") Integer id);
//    @Query(value = "SELECT p.serviceName, p.description " +
//            "FROM PackageService ps " +
//            "INNER JOIN ps.service p " +
//            "WHERE ps.package.id = :id")

    Optional<PackageServiceEntity> findByPackages_IdAndService_ServiceId(Integer packageId, Integer serviceId);
}
