package com.swp.services;

import com.swp.entity.TimeSlot;
import com.swp.entity.enums.ESlotStatus;
import com.swp.repositories.TimeSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    @Transactional
    public void updateTimeSlot(TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
    }
}
