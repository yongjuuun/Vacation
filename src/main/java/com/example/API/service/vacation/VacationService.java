package com.example.API.service.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.web.dto.request.VacRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VacationService {

    List<Vacation> findAll();
    Vacation findById(Long id);
    List<Vacation> findByUser(User user);
    Vacation request(User user, VacRequestDto vacRequestDto);
    Vacation update(Long vacationId, VacRequestDto dto);
    void cancel(Long vacationId);
}
