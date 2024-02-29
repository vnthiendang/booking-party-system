package com.swp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPaymentStatus {
    NOT_PAID,
    PAID,
    DEPOSITED
}
