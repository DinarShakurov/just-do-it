package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shakurov.spring_webapp.dto.SignInDto;
import ru.shakurov.spring_webapp.dto.TokenDto;
import ru.shakurov.spring_webapp.services.SignInService;

@RestController
@RequestMapping("/api/signIn")
public class SignInRestController {
    @Autowired
    private SignInService signInService;

    @PostMapping
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(signInService.signIn(signInDto));
    }
}
