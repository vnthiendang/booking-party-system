package com.swp.services;

import com.swp.entity.Package;
import com.swp.entity.Roles;
import com.swp.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;
    private boolean userHasCreatePackagePermission(Roles roles){
        return roles.getPermissions().contains(Roles.HOST);
    }
    public Package createPackage(Package createpackage) throws Exception {
        if(userHasCreatePackagePermission(Roles.HOST)){
            return packageRepository.save(createpackage);
        }else {
            throw new Exception("Hosts are not authorized to create packages.");
        }
    }



}

