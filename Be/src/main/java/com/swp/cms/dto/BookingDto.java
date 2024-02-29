package com.swp.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BookingDto {

    @NotNull
    private Integer packagesId;
    @NotNull
    private Date startTime;
    @NotNull
    private Date endTime;
    @NotNull
    private Integer partySize;
    private List<Integer> serviceIds;
    private Integer quantity;

    private Double totalCost;
}
