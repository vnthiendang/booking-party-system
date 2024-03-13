package com.swp.cms.dto;

import com.swp.entity.enums.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageCreateDto {

    @NotBlank(message = "Package name cannot be empty")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z0-9 ]+$", message = "Package name must only contain letters and numbers")
    private String name;

    @Valid
    private List<Integer> services = new ArrayList<>();
//    private List<CustomServiceDto> services;

    private Location venue;

    private String description;

    @NotNull(message = "Capacity cannot be null")
    private Integer capacity;
}
