package com.example.API.web.vacation;

import com.example.API.domain.user.User;
import com.example.API.domain.vacation.Vacation;
import com.example.API.service.user.UserService;
import com.example.API.service.vacation.VacationService;
import com.example.API.web.dto.request.VacRequestDto;
import com.example.API.web.dto.response.VacResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class VacationController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final VacationService vacationService;

    @GetMapping("/test")
    public void test() {
        System.out.println("test");
    }

    @GetMapping("/request")
    public ModelAndView request() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vacation_request");
        User user = userService.getUser();
        modelAndView.addObject("count", user.getAvailableVacDays());

        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vacation_detail");
        User user = userService.findById(Long.parseLong(id));
        List<Vacation> vacations = vacationService.findByUser(user);
        modelAndView.addObject("vacations", vacations);

        return modelAndView;
    }
    @PostMapping("/request")
    public ModelAndView request(@RequestBody VacRequestDto vacRequestDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vacation_request");
        User user = userService.getUser();
        vacationService.request(user, vacRequestDto);
        modelAndView.addObject("count", user.getAvailableVacDays());

        return modelAndView;
    }

    @GetMapping("")
    public ResponseEntity<List<VacResponseDto>> findByUser() {
        User user = userService.getUser();
        List<Vacation> vacations = vacationService.findByUser(user);
        List<VacResponseDto> dto =
                vacations.stream().map(p -> modelMapper.map(p, VacResponseDto.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacResponseDto> getVacation(@PathVariable String id) {
        Vacation vacation = vacationService.findById(Long.parseLong(id));
        VacResponseDto dto = modelMapper.map(vacation, VacResponseDto.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacResponseDto> update(@PathVariable String id, @RequestBody VacRequestDto vacRequestDto) {
        Vacation vacation = vacationService.update(Long.parseLong(id), vacRequestDto);
        VacResponseDto dto = modelMapper.map(vacation, VacResponseDto.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable String id) {
        vacationService.cancel(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
