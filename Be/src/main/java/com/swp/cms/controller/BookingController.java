package com.swp.cms.controller;

import com.swp.cms.dto.ServiceDto;
import com.swp.services.BookingService;
import com.swp.services.PServiceService;
import com.swp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final PServiceService serviceService;
    private final UserService userService;
    private final BookingService bookingService;

    @GetMapping("/service/getall")
    public List<ServiceDto> getAllServices() {

        return serviceService.getAllServices();
    }
}
