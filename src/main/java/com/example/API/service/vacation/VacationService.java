package com.example.API.service.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.web.dto.request.VacRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VacationService {
    List<Vacation> findAll();
    Vacation findById(Long vacationId);
//    Vacation findByVacationDate(Long id, start_date, end_date);
    Long save(VacRequestDto vacRequestDto);
}
