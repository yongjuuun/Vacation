package com.example.API.repository;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    //
    Optional<Vacation> findByVacationId(Long vacationId);       // 인자에 무엇을?


    // save - 휴가 저장

    // findById - id로 휴가 조회

    // findAll - 모든 휴가 조회

    // remainVacation - 남은 휴가일 조회

    // daysVacation - 시작일과 종료일로 휴가일 조회
}
