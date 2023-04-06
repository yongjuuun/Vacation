package com.example.API.web.dto.request;

import com.example.API.domain.vacation.Vacation;
import com.example.API.domain.vacation.VacationStatus;
import com.example.API.domain.vacation.VacationType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VacationRequestDto {
    private VacationType vacationType;
    private VacationStatus status;
    private Double daysUsed;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double availableVacDays;
    private Double requestedVacDays;
    private Long userId;
    private String comment;

    public Vacation toEntity() {
        return Vacation.builder()
                .vacationType(this.vacationType)
                .status(this.status)
                .daysUsed(this.daysUsed)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .comment(this.comment)
                .availableVacDays(this.availableVacDays)
                .requestedVacDays(this.requestedVacDays)
                .userId(this.userId)
                .build();
    }
}
