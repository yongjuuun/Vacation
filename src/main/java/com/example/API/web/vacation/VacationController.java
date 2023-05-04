package com.example.API.web.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.service.user.UserService;
import com.example.API.service.vacation.VacationService;
import com.example.API.web.dto.request.VacationRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class VacationController {

    private final VacationService vacationService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Vacation> getVacation(@PathVariable Long id) {
        Vacation vacationDto = vacationService.getVacation(id);
        //TODO: mapping response dto
        return new ResponseEntity<>(vacationDto, HttpStatus.OK);
    }

    @GetMapping("/request")
    public ModelAndView request() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vacation_request");

        String currentUserName  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        Map<String, Object> map = new HashMap<>();
        map.put("count", user.getAvailableVacDays());
        modelAndView.addObject("data", map);

        return modelAndView;
    }

    @GetMapping("/detail")
    public ModelAndView detail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vacation_detail");
        return modelAndView;
    }
    @PostMapping("/request")
    public ModelAndView requestVacation(@RequestBody VacationRequestDto vacationRequestDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vacation_request");

        String currentUserName  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);

        vacationService.requestVacation(user, vacationRequestDto);

        Map<String, Object> map = new HashMap<>();
        map.put("count", user.getAvailableVacDays());

        modelAndView.addObject("data", map);
        return modelAndView;
    }

//    @GetMapping
//    public ResponseEntity<List<VacResponseDto>> getAllVacations() {
//        List<VacResponseDto> vacationDtos = vacationService.getAllVacations();
//        return new ResponseEntity<>(vacationDtos, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelVacation(@PathVariable String id) {
        vacationService.cancelVacation(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}