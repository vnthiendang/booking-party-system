package com.swp.services;

import com.swp.entity.PService;
import com.swp.entity.PackageServiceEntity;
import com.swp.entity.PackageSlot;
import com.swp.entity.TimeSlot;
import com.swp.repositories.PServiceRepository;
import com.swp.repositories.PackageSlotRepository;
import com.swp.repositories.ServiceRepository;
import com.swp.repositories.TimeSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final PackageSlotRepository packageSlotRepository;

    @Transactional
    public List<PackageSlot> saveAll(List<PackageSlot> packageSlots) {
        return packageSlotRepository.saveAll(packageSlots);
    }

    public List<TimeSlot> getSlotsById(List<Integer> slotIds) {
        return timeSlotRepository.findAllById(slotIds);
    }
}
