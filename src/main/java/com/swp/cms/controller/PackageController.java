package com.swp.cms.controller;

import com.swp.entity.Package;
import com.swp.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping("/packages/create")
        public Package createPackage (@RequestBody Package packages) throws Exception {
            return packageService.createPackage(packages);
        }
}

