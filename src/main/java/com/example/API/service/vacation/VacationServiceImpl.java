package com.example.API.service.vacation;

import com.example.API.domain.vacation.Vacation;
import com.example.API.exception.VacationNotFoundException;
import com.example.API.repository.VacationRepository;
import com.example.API.web.dto.request.VacRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
    private final VacationRepository vacationRepository;

    public List<Vacation> findAll() {
        return vacationRepository.findAll();
    }

    public boolean existByVacation(Long userId) {
        return vacationRepository.existsByUserId(userId);
    }

    @Override
    public Vacation findById(Long vacationId) {
        return vacationRepository.findById(vacationId)
                .orElseThrow(() -> new VacationNotFoundException("휴가를 찾을 수 없습니다."));
    }

//    @Override
//    public Vacation findByVacationDate(Long id) {
//        return null;
//    }

    @Override
    public Long save(VacRequestDto vacRequestDto) {
        Vacation vacation = vacRequestDto.toEntity();
        return vacationRepository.save(vacation).getVacationId();
    }
}
