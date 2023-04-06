package com.example.API.web.vacation;

import com.example.API.domain.vacation.Vacation;
import com.example.API.service.vacation.VacationService;
import com.example.API.web.dto.request.VacationRequestDto;
import com.example.API.web.dto.response.VacationResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class VacationController {

    private final VacationService vacationService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<Vacation> findAll() {
        return vacationService.findAll();
    }

    @GetMapping("")
    public ResponseEntity<List<VacationResponseDto>> findAllByUserId(@RequestParam("userId") Long userId) {

        List<Vacation> vacation = vacationService.findAllByUserId(userId);
        List<VacationResponseDto> dto =
                vacation.stream().map(p -> modelMapper.map(p, VacationResponseDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/find/{vacationId}")
    public ResponseEntity<VacationResponseDto> findById(@PathVariable Long vacationId) {
        Vacation vacation = vacationService.findById(vacationId);
        VacationResponseDto dto = modelMapper.map(vacation, VacationResponseDto.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("request")
    public ResponseEntity<VacationResponseDto> requestVacation(@RequestBody VacationRequestDto vacationRequestDto) {
        Vacation vacation = vacationService.requestVacation(vacationRequestDto);
        VacationResponseDto vacationResponseDto = modelMapper.map(vacation, VacationResponseDto.class);

        return ResponseEntity.ok(vacationResponseDto);
    }

    @PutMapping("/edit/{vacationId}")
    public ResponseEntity<VacationResponseDto> editVacation(@PathVariable Long vacationId,
                                                            @RequestBody VacationRequestDto vacationRequestDto) {
        Vacation vacation = vacationService.editVacation(vacationId, vacationRequestDto);
        VacationResponseDto vacationResponseDto = modelMapper.map(vacation, VacationResponseDto.class);

        return ResponseEntity.ok(vacationResponseDto);
    }

    @DeleteMapping("/delete/{vacationId}")
    public ResponseEntity<?> deleteById(@PathVariable Long vacationId) {
        vacationService.deleteById(vacationId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }

}
