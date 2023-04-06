package com.example.API.domain.vacation;

import com.example.API.domain.BaseEntity;
import com.example.API.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name = "vacations")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vacation extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long id;

    @Column(name = "vacation_type")
    @Enumerated(EnumType.STRING)
    private VacationType vacationType;

    @Enumerated(EnumType.STRING)
    private VacationStatus status;

    @Column(name = "days_used", length = 3, nullable = false)
    private Double daysUsed;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "available_vac_days", columnDefinition ="Default Value: 15.0")
    private Double availableVacDays;

    @Column(name = "requested_vac_days", columnDefinition ="Default Value: 0.0")
    private Double requestedVacDays;

    @Column(length = 500)
    private String comment;

    public void update(VacationStatus status) {
        this.status = status;
        this.availableVacDays = availableVacDays;
        this.requestedVacDays = requestedVacDays;
    }
}
