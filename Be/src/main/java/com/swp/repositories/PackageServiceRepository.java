package com.swp.repositories;

import com.swp.entity.PackageServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageServiceRepository extends JpaRepository<PackageServiceEntity, Integer> {
//    Optional<Package> findByPackageName(String packageName);
//    @Query("SELECT p FROM Package p WHERE p.userId.usId = :userId")
//    List<Package> findByUserIdWithServices(@Param("userId") Integer userId);
//
//    Optional<Package> findByIdAndUserId_UsId(Integer id, Integer userId);
//
//    @Query("SELECT DISTINCT p FROM Package p LEFT JOIN FETCH p.pServices ps LEFT JOIN FETCH ps.service")
//    List<Package> findAllPackagesWithServices();

    //void addPackageService
}