package com.swp.cms.dto;

import com.swp.entity.enums.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PackageDto {
    private Integer id;

    @NotBlank(message = "Package name cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z0-9])[A-Za-z0-9 ]+$", message = "Package name must contain at least one letter or number")
    private String name;

    @Valid
    private List<ServiceDto> services;

    private Location venue;

    private String description;

    private LocalDateTime checkinTime;

    private LocalDateTime checkoutTime;

    private Integer capacity;
    private String hostEmail;
}
