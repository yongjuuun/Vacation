package com.example.API.service.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.domain.vacation.VacationStatus;
import com.example.API.domain.vacation.VacationType;
import com.example.API.exception.CanNotBeUsedVacationException;
import com.example.API.exception.FailCancelVacationException;
import com.example.API.exception.FailRequestVacationException;
import com.example.API.exception.VacationNotFoundException;
import com.example.API.repository.VacationRepository;
import com.example.API.service.vacation.VacationService;
import com.example.API.util.Utils;
import com.example.API.web.dto.request.VacRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    private final Utils utils;
    private final VacationRepository vacationRepository;

    @Override
    public List<Vacation> findAll() {
        return vacationRepository.findAll();
    }

    @Override
    public Vacation findById(Long id) {
        return vacationRepository.findById(id)
                .orElseThrow(() -> new VacationNotFoundException("휴가를 찾을 수 없습니다."));
    }

    @Override
    public List<Vacation> findByUser(User user) {
        return vacationRepository.findByUser(user);
    }

    @Override
    public Vacation request(User user, VacRequestDto vacRequestDto) {
        Double availableVacDays = user.getAvailableVacDays();

        if (availableVacDays <= 0) {
            throw new CanNotBeUsedVacationException("사용 가능한 연차가 없습니다.");
        }

        LocalDate requestStartDate = vacRequestDto.getStartDate();
        LocalDate requestEndDate = vacRequestDto.getEndDate();

        double daysUsed;
        double totalDaysUsed;
        double remainAvailableDays;

        if (!vacRequestDto.getVacationType().equals(VacationType.ANNUAL_DAY)) {

            if (vacRequestDto.getStartDate() != null) {
                if (vacRequestDto.getEndDate() != null
                        && !vacRequestDto.getStartDate().isEqual(vacRequestDto.getEndDate())) {
                    throw new FailRequestVacationException("시작 날짜와 종료 날짜가 동일하지 않습니다.");
                }

                requestStartDate = vacRequestDto.getStartDate();
                requestEndDate = vacRequestDto.getStartDate();
            } else {
                requestStartDate = LocalDate.now();
                requestEndDate = LocalDate.now();
            }

            utils.calculateVacationDaysExcludingHolidays(requestStartDate, requestEndDate);

            totalDaysUsed = user.getRequestedVacDays() + vacRequestDto.getVacationType().getDays();
            remainAvailableDays = availableVacDays - vacRequestDto.getVacationType().getDays();
            daysUsed = vacRequestDto.getVacationType().getDays();
        } else {
            if (vacRequestDto.getStartDate() == null
                    || vacRequestDto.getEndDate() == null) {
                throw new FailRequestVacationException("시작 날짜와 종료 날짜를 입력해주세요.");
            }

            double daysExcludingHolidays =
                    utils.calculateVacationDaysExcludingHolidays(requestStartDate, requestEndDate);

            totalDaysUsed = user.getRequestedVacDays() + daysExcludingHolidays;
            remainAvailableDays =  availableVacDays - daysExcludingHolidays;
            daysUsed = daysExcludingHolidays;
        }

        requestValidation(user, requestStartDate, requestEndDate);

        VacationStatus status;
        if (LocalDate.now().isEqual(requestStartDate)) {
            status = VacationStatus.USED;
        } else {
            status = VacationStatus.APPROVED;
        }

        user.update(remainAvailableDays, totalDaysUsed);
        Vacation vacation = vacRequestDto.toEntity(
                user,
                daysUsed,
                status,
                requestStartDate,
                requestEndDate
        );

        return vacationRepository.save(vacation);
    }

    public void requestValidation(User user, LocalDate requestStartDate, LocalDate requestEndDate) {
        if (LocalDate.now().isAfter(requestStartDate)) {
            throw new FailRequestVacationException("시작 날짜가 오늘 보다 커야 합니다.");
        }

        if (requestStartDate.isAfter(requestEndDate)) {
            throw new FailRequestVacationException("종료 날짜가 시작 날짜 보다 커야 합니다.");
        }

        List<Vacation> vacations = vacationRepository.findByUserId(user.getId());

        for (Vacation vacation : vacations) {
            LocalDate startDate = vacation.getStartDate();
            LocalDate endDate = vacation.getEndDate();

            if ((vacation.getStatus().equals(VacationStatus.APPROVED)
                    || vacation.getStatus().equals(VacationStatus.PENDING)
                    || vacation.getStatus().equals(VacationStatus.USED))
                    && (!requestStartDate.isBefore(startDate) && !requestStartDate.isAfter(endDate))) {
                throw new FailRequestVacationException("이미 휴가 일정이 포함되어 있습니다.");
            }
        }
    }


    @Override
    public Vacation update(Long vacationId, VacRequestDto dto) {
        Vacation existingVacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException("휴가를 찾을 수 없습니다."));

        // 휴가 상태 변경 ex) VacationStatus.REQ_CANCELED -> VacationStatus.CANCELED
        existingVacation.update(dto.getStatus());
        return vacationRepository.save(existingVacation);
    }

    @Override
    public void cancel(Long vacationId) {
        Vacation existingVacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException("휴가를 찾을 수 없습니다."));

        if (existingVacation.getStatus().equals(VacationStatus.CANCELED)
                || existingVacation.getStatus().equals(VacationStatus.REQ_CANCEL)
                || existingVacation.getStatus().equals(VacationStatus.REJECTED)
                || existingVacation.getStatus().equals(VacationStatus.USED)) {
            throw new FailCancelVacationException("이미 사용된 휴가이거나, 취소된 휴가이거나, 거부된 휴가입니다.");
        }

        if (existingVacation.getStatus().equals(VacationStatus.APPROVED)
                || existingVacation.getStatus().equals(VacationStatus.PENDING)) {
            System.out.println("CANCEL");
            User user = existingVacation.getUser();

            Double daysUsed = user.getRequestedVacDays() - existingVacation.getDaysUsed();
            Double availableResultDays = user.getAvailableVacDays() + existingVacation.getDaysUsed();

            user.update(availableResultDays, daysUsed);
            existingVacation.update(VacationStatus.REQ_CANCEL);
        }
    }
}
