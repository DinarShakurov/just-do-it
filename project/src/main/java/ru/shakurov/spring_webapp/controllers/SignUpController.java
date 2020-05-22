package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.forms.SignUpForm;
import ru.shakurov.spring_webapp.services.SignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/signUp")
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @GetMapping
    public String getSignUpPage(Authentication authentication, ModelMap modelMap, HttpServletRequest request) {
        if (authentication != null) {
            return "redirect:/profile";
        }
        modelMap.addAttribute("signUpForm", new SignUpForm());
        if (request.getParameterMap().containsKey("error")) {
            modelMap.addAttribute("error", true);
        }
        return "sign_up";
    }

    @PostMapping
    public String signUp(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "sign_up";
        signUpService.signUp(signUpForm);
        return "redirect:/signIn";
    }

    @GetMapping("/confirmation/{link}")
    public String confirmRegistration(@PathVariable("link") String link, ModelMap modelMap) {
        String message = signUpService.confirm(link) ? "Success" : "Error";
        modelMap.addAttribute("message", message);
        return "sign_up_confirmation";
    }
}
