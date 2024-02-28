package com.swp.services;

import com.swp.cms.dto.ServiceDto;
import com.swp.entity.PService;
import com.swp.entity.PackageServiceEntity;
import com.swp.repositories.PServiceRepository;
import com.swp.repositories.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PServiceService {
    private final ServiceRepository serviceRepository;
    private final PServiceRepository pServiceRepository;

    public List<ServiceDto> getAllServices() {
        List<PService> services = serviceRepository.findAll();
        return mapServicesToServiceDtos(services);
    }

    @Transactional
    public List<PackageServiceEntity> saveAll(List<PackageServiceEntity> services) {
        return pServiceRepository.saveAll(services);
    }

    public List<PService> getServicesByIds(List<Integer> serviceIds) {
        return serviceRepository.findAllById(serviceIds);
    }
    public Optional<ServiceDto> getServicesById(Integer serviceId) {
        PService pService = serviceRepository.findById(serviceId).orElseThrow(() -> new EntityNotFoundException("Package not found"));
        return Optional.ofNullable(mapServiceToServiceDto(pService));
    }

    public ServiceDto mapServiceToServiceDto(PService service) {
        return ServiceDto.builder()
                .id(service.getServiceId())
                .serviceType(service.getServiceType())
                .serviceAmount(service.getServiceAmount())
                .serviceImage(service.getServiceImage())
                .price(service.getPrice())
                .description(service.getDescription())
                .serviceName(service.getServiceName())

                .build();
    }

    List<ServiceDto> mapServicesToServiceDtos(List<PService> services){
        return services.stream()
                .map(this::mapServiceToServiceDto)
                .collect(Collectors.toList());
    }
}
