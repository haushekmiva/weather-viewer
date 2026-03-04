package com.haushekmiva.controller;

import com.haushekmiva.dto.UserLoginRequest;
import com.haushekmiva.dto.UserRegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {

    @GetMapping("/sign-up")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegisterRequest());
        return "sign-up";
    }

    @GetMapping("/sign-in")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserLoginRequest());
        return "sign-in";
    }

}
