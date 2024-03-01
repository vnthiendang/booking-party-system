package com.swp.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookedServiceDto {
    private Integer bookingId;
    private List<CustomServiceDto> customServices;
    private Integer customerId;
    private Double deposit;
}
