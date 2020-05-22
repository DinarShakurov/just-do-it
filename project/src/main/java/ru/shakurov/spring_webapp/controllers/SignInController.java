package ru.shakurov.spring_webapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.dto.SignInErrorMessageDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/signIn")
@PreAuthorize("permitAll()")
public class SignInController {

    @GetMapping
    public String getSignInPage(Authentication authentication, Model model, HttpServletRequest request) {
        if (authentication != null) {
            return "redirect:/profile";
        }
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", SignInErrorMessageDto.builder()
                    .message("Error")
                    .build());
        }

        return "sign_in";
    }
}
