package com.swp.services;

import com.swp.repositories.BookingPServiceRepository;
import com.swp.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingPServiceService {
    private final ServiceRepository serviceRepository;
    private final BookingPServiceRepository bookingPServiceRepository;


}
