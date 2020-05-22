package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shakurov.spring_webapp.forms.SignUpForm;
import ru.shakurov.spring_webapp.services.SignUpService;

@RestController
@RequestMapping("/api/signUp")
public class SignUpRestController {
    @Autowired
    private SignUpService signUpService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void signUp(@RequestBody SignUpForm signUpForm) {
        signUpService.signUp(signUpForm);
    }

    @GetMapping("/confirmation/{link}")
    public ResponseEntity<String> confirmRegistration(@PathVariable("link") String link) {
        String message = signUpService.confirm(link) ? "Success" : "Error";
        return ResponseEntity.ok(message);
    }
}
