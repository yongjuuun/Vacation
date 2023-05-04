package com.example.API.web.dto.request;

import com.example.API.domain.user.User;
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

    private LocalDate startDate;
    private LocalDate endDate;
    private VacationType vacationType;
    private VacationStatus status;
    //    private Double availableVacDays;
//    private Double requestedVacDays;
    private String comment;

    public Vacation toEntity(User user, double day_used, VacationStatus status) {
        return Vacation.builder()
                .startDate(startDate)
                .endDate(endDate)
                .vacationType(this.vacationType)
                .comment(this.comment)
                .daysUsed(day_used)
                .status(status)
                .user(user)
                .build();
    }

//    public Vacation toEntity(User user, Double days_used, VacationStatus status,
//                             LocalDate startDate, LocalDate endDate)
//    {
//        return Vacation.builder()
//                .startDate(startDate)
//                .endDate(endDate)
//                .vacationType(this.vacationType)
//                .comment(this.comment)
//                .daysUsed(days_used)
//                .status(status)
//                .user(user)
////                .availableVacDays(this.availableVacDays)
////                .requestedVacDays(this.requestedVacDays)
//                .build();
//    }
}
