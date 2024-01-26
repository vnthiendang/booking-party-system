package com.swp.repositories;

import com.swp.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
    Optional<Package> findByPackageName(String packageName);
    List<Package> findByHostId(Integer hostId);
    @Query("SELECT p FROM Package p LEFT JOIN FETCH p.services WHERE p.host.id = :hostId")
    List<Package> findByHostIdWithServices(@Param("hostId") Integer hostId);

    Optional<Package> findByIdAndHostId(Integer id, Integer hostId);
}
