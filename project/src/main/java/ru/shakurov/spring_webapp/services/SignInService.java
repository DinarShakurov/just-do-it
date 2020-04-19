package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.dto.SignInDto;
import ru.shakurov.spring_webapp.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(SignInDto dto);
}
