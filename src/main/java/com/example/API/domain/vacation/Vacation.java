package com.example.API.domain.vacation;

import com.example.API.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@DynamicInsert
@Table(name = "vacation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class Vacation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long vacationId;

    @Column(name = "vacation_type", length = 20)    // 연차 반차 반반차?
    private String vacationType;

    @Column(name = "status", length = 10)       // 연차 시작? 아직?
    private String status;

    @Column(name = "days_used", nullable = false) // 사용된 날짜
    private Double daysUsed;

    @Column(name = "start_date", nullable = false) // 시작 날짜
    private Double startDate;

    @Column(name = "end_date", nullable = false) // 종료 날짜
    private Double endDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(length = 500, nullable = true)
    private String comment;

//    private Double createdAt;

//    private Double updatedAt;

    // PRIMARY KEY (vacation_id)

    // CONSTRAINT vacations_ibfk_1 FOREIGN KEY (user_id) REFERENCES
}
