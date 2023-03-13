//package com.example.API.web.vacation;
//
//import com.example.API.domain.vacation.Vacation;
//import com.example.API.service.vacation.VacationService;
//import com.example.API.web.dto.response.VacResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/vacation")
//public class VacationController {
//
//    // 휴가 신청, 조회, 취소
//    private final VacationService vacationService;
//    private final ModelMapper modelMapper;
//
//    // 모든 유저의 휴가 요청 목록 조회
//    @GetMapping("")
//    public ResponseEntity<List<VacResponseDto>> findAllVacation() {
//        List<Vacation> vacations = vacationService.findAllVacation();
//
//    }
//
//    @GetMapping("/{userId}")
//    public get
//
//    // user 의
//    @GetMapping("/{vacationId")
//    public ResponseEntity<VacResponseDto>
//
//
//
//    // 휴가 취소
//}
