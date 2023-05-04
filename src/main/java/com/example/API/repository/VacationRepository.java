package com.example.API.repository;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {
    List<Vacation> findByUser(User user);

    void deleteById(Long vacationId);

    List<Vacation> findByUserId(Long userId);
}
