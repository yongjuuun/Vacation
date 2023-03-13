package com.example.API.web;

import com.example.API.config.VacationConfig;
import com.example.API.domain.user.User;
import com.example.API.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final UserService userService;
//    private final VacationConfig vacationConfig;


    @GetMapping(value = "")
    public ModelAndView home() {
//        vacationConfig.ResetVacation();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        List<User> users = userService.findAll();
        modelAndView.addObject("users", users);

        return modelAndView;
    }
}
