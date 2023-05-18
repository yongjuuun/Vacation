//package com.example.API.service.vacation;
//
//import com.example.API.domain.user.User;
//import com.example.API.domain.vacation.Vacation;
//import com.example.API.web.dto.request.VacRequestDto;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public interface VacationService_OLD {
//
//    Vacation getVacation(Long id);
//    List<Vacation> getVacationsByUser(User user);
//    Vacation requestVacation(User user, VacRequestDto vacationRequestDto);
//    Vacation updateVacation(Long vacationId, VacRequestDto dto);
//    void cancelVacation(Long vacationId);
//    double calculateVacationDaysExcludingHolidays(LocalDate startDate, LocalDate endDate);
//
//    void VacationReset();
//}