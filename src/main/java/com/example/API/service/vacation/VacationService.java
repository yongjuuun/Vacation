package com.example.API.service.vacation;

import com.example.API.domain.vacation.Vacation;
import com.example.API.web.dto.request.VacationRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VacationService {
    List<Vacation> findAll();
    List<Vacation> findAllByUserId(Long userId);
    Vacation findById(Long vacationId);

    Vacation requestVacation(VacationRequestDto vacationRequestDto);

    Vacation editVacation(Long vacationId, VacationRequestDto vacationRequestDto);

    void deleteById(Long vacationId);
}
