package com.example.API.service.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.domain.vacation.VacationStatus;
import com.example.API.exception.CanNotBeUsedVacationException;
import com.example.API.exception.FailRequestVacationException;
import com.example.API.exception.VacationNotFoundException;
import com.example.API.repository.VacationRepository;
import com.example.API.web.dto.request.VacationRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;

    @Override
    public Vacation getVacation(Long id) {
        return null;
    }

    @Override
    public List<Vacation> getVacationsByUser(User user) {
        return vacationRepository.findByUser(user);
    }

    @Override
    public Vacation requestVacation(User user, VacationRequestDto vacationRequestDto) {
        Double availableVacDays = user.getAvailableVacDays();

        if (availableVacDays <= 0) {
            throw new CanNotBeUsedVacationException("사용 가능한 연차가 없습니다.");
        }

        LocalDate requestStartDate = vacationRequestDto.getStartDate();
        LocalDate requestEndDate = vacationRequestDto.getEndDate();

        if (requestStartDate.isAfter(requestEndDate)) {
            throw new FailRequestVacationException("종료 날짜가 시작 날짜 보다 커야 합니다.");
        }

        List<Vacation> vacations = vacationRepository.findByUserId(user.getId())/*.get()*/;

        for (Vacation vacation : vacations) {
            LocalDate startDate = vacation.getStartDate();
            LocalDate endDate = vacation.getEndDate();

            if (!requestStartDate.isBefore(startDate) && !requestStartDate.isAfter(endDate)) {
                throw new FailRequestVacationException("이미 휴가 일정이 포함되어 있습니다.");
            }
        }

        Period betweenPeriod = Period.between(requestStartDate, requestEndDate);

        Double daysUsed = user.getRequestedVacDays() + betweenPeriod.getDays();
        Double availableResultDays = availableVacDays - betweenPeriod.getDays();

        user.update(availableResultDays, daysUsed);

        Vacation vacation = VacationRequestDto.toEntity(
                user,
                (double) betweenPeriod.getDays(),
                VacationStatus.APPROVED
        );

        return vacationRepository.save(vacation);
    }

    @Override
    public Vacation updateVacation(Long vacationId, VacationRequestDto dto) {
        Vacation existingVacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException("Vacation not found"));

        existingVacation.update(dto.getStatus());
        return vacationRepository.save(existingVacation);
    }

    @Override
    public void cancelVacation(Long vacationId) {
        Vacation existingVacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException("Vacation not found"));

        //TODO validation check 사용했는지?? 정책에 따라 조건부가 바뀔거 같음

//        if (existingVacation.getStatus().equals(VacationStatus.USED)) {
//            throw new FailCancelVacationException("이미 휴가를 사용하였습니다.");
//        }

        User user = existingVacation.getUser();

        Double daysUsed = user.getRequestedVacDays() - existingVacation.getDaysUsed();
        Double availableResultDays = user.getAvailableVacDays() + existingVacation.getDaysUsed();

        user.update(availableResultDays, daysUsed);
//        existingVacation.update(VacationStatus.REQ_CANCELED);
        existingVacation.update(VacationStatus.CANCELED);
    }

    @Override
    public double calculateVacationDaysExcludingHolidays(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 12),
                LocalDate.of(2023, 3, 1)
                // Add more holidays as needed
        );

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        long weekends = IntStream.range(0, (int) totalDays)
                .mapToObj(startDate::plusDays)
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .count();

        long holidaysCount = holidays.stream()
                .filter(date -> !date.isBefore(startDate) && !date.isAfter(endDate))
                .count();

        long daysExcludingHolidays = totalDays - weekends - holidaysCount;

        return (double) daysExcludingHolidays;
    }

    @Override
    @Scheduled(cron = "0 0 0 1 1 ?")
    @Transactional
    public void VacationReset() {
        System.out.println("VACATION RESET");
//        em.createQuery("update Account set annualCnt = 15").executeUpdate();
    }
}