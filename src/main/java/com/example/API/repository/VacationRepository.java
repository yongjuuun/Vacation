package com.example.API.repository;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Optional<Vacation> findByVacationId(Long vacationId);       // 인자에 무엇을?

    // 남은 휴가일 조회

//    Optional<User> findByUsername(String username);
//    boolean existsByUsername(String username);
}
