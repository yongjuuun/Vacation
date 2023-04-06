package com.example.API.web.dto.response;

import lombok.Data;

@Data
public class VacationResponseDto {
    private Long vacationId;
    private String vacationType;
    private String status;
    private Double daysUsed;
    private Double startDate;
    private Double endDate;
    private Long userId;
    private String comment;
}
