package com.swp.repositories;

import com.swp.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
    Optional<Package> findByPackageName(String packageName);
//    List<Package> findAllByHost_Id(Integer id);

//    Optional<Package> findByIdAndHost_Id(Integer id, Integer hostId);
}
