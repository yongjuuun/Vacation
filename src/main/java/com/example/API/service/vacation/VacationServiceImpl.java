package com.example.API.service.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.exception.NotEnoughVacationDaysException;
import com.example.API.exception.VacationNotFoundException;
import com.example.API.repository.VacationRepository;
import com.example.API.service.user.UserServiceImpl;
import com.example.API.web.dto.request.VacationRequestDto;
import com.example.API.web.dto.response.VacationResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
    private final VacationRepository vacationRepository;

    @Override
    public List<Vacation> findAll() {
        return vacationRepository.findAll();
    }

    public List<Vacation> findAllByUserId(Long userId) {
        List<Vacation> vacation = vacationRepository.findAllByUserId(userId);

        if (vacation.isEmpty()) {
            throw new VacationNotFoundException("해당 유저의 휴가를 찾을 수 없습니다.");
        }

        return vacation;
    }

    @Override
    public Vacation findById(Long vacationId) {
        return vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException("해당 ID의 휴가를 찾을 수 없습니다."));
    }

    @Override
    public Vacation requestVacation(VacationRequestDto vacationRequestDto) {
        // 1. 휴가 신청한 사람 id 가져오기
        // 2. 휴가 요청받은 일 수 가져오기
        // 3. 휴가 사용 가능한 일 수 가져오기 (유저의 남은 날짜 가져오기)
        // 4. 요청 - 사용 가능 비교
        // 5. 업데이트 값 수정
        // 저장

        Long userId = vacationRequestDto.getUserId();
        //        User user = UserServiceImpl.findById(userId);   // 사용자의 휴가 가져오기

        Double requestedVacation = vacationRequestDto.getRequestedVacDays();
        Double availableVacation = vacationRequestDto.getAvailableVacDays();

        if (requestedVacation > availableVacation) {
            throw new NotEnoughVacationDaysException("잔여 연차가 없습니다.");
        }

        Double dayUsed = vacationRequestDto.getDaysUsed();

        Vacation vacation = Vacation.builder()
                .availableVacDays(availableVacation - requestedVacation)
                .daysUsed(dayUsed)
                .build();

//        Vacation.update 사용법?

//        Vacation vacation = vacationRequestDto.toEntity();

        vacationRepository.save(vacation);

        return vacation;
    }

    @Override
    public Vacation editVacation(Long vacationId, VacationRequestDto vacationRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long vacationId) {
        vacationRepository.deleteById(vacationId);
    }

//    public checkVacationPossible() {
//
//    }

}
