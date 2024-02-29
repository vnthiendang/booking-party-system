package com.swp.cms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private Integer id;
    private String description;

    @NotNull(message = "Price cannot be empty")
    @PositiveOrZero(message = "Price must be 0 or more")
    private Double price;

    @PositiveOrZero(message = "Amount must be 0 or more")
    private Integer serviceAmount;

    private String serviceImage;

    private String serviceName;

    private String serviceType;
}
