package com.swp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {
    District1(5000.0), District10(4000.0), ThuDuc(3500.0);

    private final Double price;
}
