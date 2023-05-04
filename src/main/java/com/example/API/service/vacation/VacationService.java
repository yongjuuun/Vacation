package com.example.API.service.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.web.dto.request.VacationRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface VacationService {

    Vacation getVacation(Long id);
    List<Vacation> getVacationsByUser(User user);
    Vacation requestVacation(User user, VacationRequestDto vacationRequestDto);
    Vacation updateVacation(Long vacationId, VacationRequestDto dto);
    void cancelVacation(Long vacationId);
    double calculateVacationDaysExcludingHolidays(LocalDate startDate, LocalDate endDate);

    void VacationReset();
}
