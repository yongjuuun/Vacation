package com.example.API.web.dto.request;

import com.example.API.domain.vacation.Vacation;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VacRequestDto {
    private String vacationType;
    private String status;
    private Double daysUsed;
    private Double startDate;
    private Double endDate;
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
                .userId(this.userId)
                .build();
    }
}
