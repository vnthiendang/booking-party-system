package com.swp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EBookingStatus {
    PENDING,
    APPROVED,
    CANCELLED,
    COMPLETED,
    REFUNDED
}
