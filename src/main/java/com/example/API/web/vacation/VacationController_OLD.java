//
//
//
//package com.example.API.web.vacation;
//
//import com.example.API.domain.user.User;
//import com.example.API.domain.vacation.Vacation;
//import com.example.API.service.user.UserService;
//import com.example.API.service.vacation.VacationService;
//import com.example.API.web.dto.request.VacRequestDto;
//import com.example.API.web.dto.response.VacResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/vacation")
//public class VacationController_OLD {
//
//    private final UserService userService;
//    private final VacationService vacationService;
//    private final ModelMapper modelMapper;
//
//    @GetMapping("/request")
//    public ModelAndView request() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("vacation_request");
//        User user = userService.getUser();
//        List<Vacation> vacations = user.getVacations();
//
//        double availableDays = vacationService.getAvailableDays(user, vacations);
//
//        modelAndView.addObject("count", availableDays);
//
//        return modelAndView;
//    }
//
//    @GetMapping("/detail/{id}")
//    public ModelAndView vacationDetail(@PathVariable Long id) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("vacation_detail");
//
//        User user = userService.findById(id);
//        List<Vacation> vacations = vacationService.findByUser(user);
//        modelAndView.addObject("vacations", vacations);
//
//        return modelAndView;
//    }
//
//    @PostMapping("/request")
//    public ModelAndView request(@RequestBody VacRequestDto vacRequestDto) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("vacation_request");
//
//        User user = userService.getUser();
//        vacationService.request(user, vacRequestDto);
//
//        return modelAndView;
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<VacResponseDto>> findAll() {
//        List<Vacation> vacations = vacationService.findAll();
//        List<VacResponseDto> dto =
//                vacations.stream().map(p -> modelMapper.map(p, VacResponseDto.class)).collect(Collectors.toList());
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping("")
//    public ResponseEntity<List<VacResponseDto>> findByUser() {
//        User user = userService.getUser();
//        List<Vacation> vacations = vacationService.findByUser(user);
//        List<VacResponseDto> dto =
//                vacations.stream().map(p -> modelMapper.map(p, VacResponseDto.class)).collect(Collectors.toList());
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<VacResponseDto> findById(@PathVariable Long vacationId) {
//        Vacation vacation = vacationService.findById(vacationId);
//        VacResponseDto dto = modelMapper.map(vacation, VacResponseDto.class);
//        return ResponseEntity.ok(dto);
//    }
//
//    @PutMapping("/edit/{vacationId}")
//    public ResponseEntity<VacResponseDto> edit(@PathVariable Long vacationId,
//                                               @RequestBody VacRequestDto vacRequestDto) {
//        Vacation vacation = vacationService.edit(vacationId, vacRequestDto);
//        VacResponseDto dto = modelMapper.map(vacation, VacResponseDto.class);
//        return ResponseEntity.ok(dto);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable Long vacationId) {
//        vacationService.deleteById(vacationId);
//        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
//    }
//}
