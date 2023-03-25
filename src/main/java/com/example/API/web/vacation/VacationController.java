package com.example.API.web.vacation;

import com.example.API.domain.vacation.Vacation;
import com.example.API.service.vacation.VacationService;
import com.example.API.web.dto.response.VacResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class VacationController {

    private final VacationService vacationService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<VacResponseDto>> findAllVacation() {
        List<Vacation> vacations = vacationService.findAll();
        List<VacResponseDto> dto =
                vacations.stream().map(p -> modelMapper.map(p, VacResponseDto.class)).collect(Collectors.toList());
        return  ResponseEntity.ok(dto);
    }

    @GetMapping("/{vacationId")
    public ResponseEntity<VacResponseDto> findById(@PathVariable Long vacationId) {
        Vacation vacation = vacationService.findById(vacationId);
        VacResponseDto dto = modelMapper.map(vacation, VacResponseDto.class);
        return ResponseEntity.ok(dto);
    }


}
