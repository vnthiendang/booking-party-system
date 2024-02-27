package com.swp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeShift {
    morning("Available"),
    afternoon("Available"),
    evening("Available");

    private final String status;
}