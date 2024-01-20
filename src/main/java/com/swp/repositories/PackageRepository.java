package com.swp.repositories;

import com.swp.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PackageRepository extends JpaRepository<Package, Integer>, JpaSpecificationExecutor<Package> {
}
