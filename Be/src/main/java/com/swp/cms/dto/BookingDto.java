package com.swp.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BookingDto {
    private Integer bookingId;

    private Date bookingDate;

    @NotNull
    private Date endTime;

    @NotNull
    private Integer partySize;

    @NotNull
    private Date startTime;

    private String bookingStatus;

    @NotNull
    private Integer packagesId;

    private Double totalCost;
    private String paymentStatus;

    private List<Integer> customServices;
}
