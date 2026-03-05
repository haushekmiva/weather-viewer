package com.haushekmiva.controller;

import com.haushekmiva.dto.*;
import com.haushekmiva.service.AuthService;
import com.haushekmiva.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/sign-up")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserRegisterRequest());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String processRegistrationForm(@Valid @ModelAttribute("userForm") UserRegisterRequest user,
                                          BindingResult result,
                                          HttpServletResponse httpResponse) {

        if (result.hasErrors()) {
            return "sign-up";
        }

        AuthResponse authResponse = authService.registerUser(user);

        switch (authResponse) {
            case AuthSuccess authSuccess -> {
                Cookie cookie = CookieUtils.cookie(
                        "session_id",
                        authSuccess.sessionId().toString(),
                        authSuccess.maxAge(),
                        "/"
                );
                httpResponse.addCookie(cookie);
                return "redirect:/";
            }
            case AuthError authError -> {
                result.reject("auth.register.error", authError.message());
                return "sign-up";
            }
        }
    }

    @GetMapping("/sign-in")
    public String showLoginForm(Model model) {
        model.addAttribute("userForm", new UserLoginRequest());
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String processLoginForm(@Valid @ModelAttribute("userForm") UserLoginRequest user,
                                   BindingResult result,
                                   HttpServletResponse httpResponse) {

        if (result.hasErrors()) {
            return "sign-in";
        }

        AuthResponse authResponse = authService.loginUser(user);

        switch (authResponse) {
            case AuthSuccess authSuccess -> {
                Cookie cookie = CookieUtils.cookie("session_id",
                        authSuccess.sessionId().toString(),
                        authSuccess.maxAge(),
                        "/");
                httpResponse.addCookie(cookie);
                return "redirect:/";
            }
            case AuthError authError -> {
                result.reject("auth.login.error", authError.message());
                return "sign-in";
            }
        }

    }

    // TODO: когда сделаю главную страницу оставить только POST и убрать GET
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutUser(@CookieValue(value = "session_id", required = false    ) UUID sessionId) {
        if (sessionId == null) {
            return "redirect:/";
        }

        authService.logoutUser(sessionId);
        return "redirect:/";
    }

}
