package com.example.API.domain.vacation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VacationType {

    ANNUAL_DAY(1.0, "연차"),
    ANNUAL_HALF_DAY(0.5,"반차"),
    ANNUAL_QUARTER_DAY(0.25, "반반차");

    private final double days;
    private final String message;
}
