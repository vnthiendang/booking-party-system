package com.swp.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

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
}
