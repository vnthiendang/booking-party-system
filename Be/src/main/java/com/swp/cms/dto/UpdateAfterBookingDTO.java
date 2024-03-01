package com.swp.cms.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAfterBookingDTO {

    @NotNull
    private Integer bookingId;
}
