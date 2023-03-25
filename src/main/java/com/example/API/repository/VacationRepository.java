package com.example.API.repository;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Optional<Vacation> findByUserVacation(Long userId);
//    Optional<Vacation> findByVacationDayUsed(String vacationId);

    boolean existsByUserId(Long userId);

}
