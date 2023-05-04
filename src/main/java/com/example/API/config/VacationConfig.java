package com.example.API.config;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.VacationStatus;
import com.example.API.service.user.UserService;
import com.example.API.service.vacation.VacationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class VacationConfig {
    private final UserService userService;

//    private final VacationService vacationService;
    @Value("${vacation.default.reset_date}")
    public String RESET_DATE;
    @Value("${vacation.default.annual_days}")
    public Double DEFAULT_VACATION;

    //    @Scheduled(cron = "0 0 0 1 1 ?")
    @Transactional
    public void ResetVacation() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        LocalDateTime now = LocalDateTime.now();

        if (dtf.format(now).equals(RESET_DATE)) {
            String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(currentUserName);
//            user.update(DEFAULT_VACATION, 0.0);   수정
        }
    }

//    @Transactional
//    @Scheduled(cron = "0 1 1 * * *")
//    public void todayVacationCheck() {
//        LocalDate now = LocalDate.now();
//        List<Vacation> vacations = vacationService.findAll();
//
//        for (Vacation vacation : vacations) {
//            if (vacation.getStatus().equals(VacationStatus.APPROVED)
//                    && vacation.getStartDate().isEqual(now)) {
//                vacation.update(VacationStatus.USED);
//            }
//        }
//    }
}

