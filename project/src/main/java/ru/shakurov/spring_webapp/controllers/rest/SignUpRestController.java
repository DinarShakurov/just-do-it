package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.shakurov.spring_webapp.forms.SignUpForm;
import ru.shakurov.spring_webapp.services.SignUpService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/signUp")
public class SignUpRestController {
    @Autowired
    private SignUpService signUpService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void signUp(@Valid @RequestBody SignUpForm signUpForm) {
        signUpService.signUp(signUpForm);
    }

    @GetMapping("/confirmation/{link}")
    public ResponseEntity<String> confirmRegistration(@PathVariable("link") String link) {
        String message = signUpService.confirm(link) ? "Success" : "Error";
        return ResponseEntity.ok(message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String errorMessage = objectError.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }
}
