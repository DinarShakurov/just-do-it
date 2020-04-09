package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.dto.EmailDto;
import ru.shakurov.spring_webapp.dto.SignUpDto;

public interface SignUpService {
    EmailDto signUp(SignUpDto signUpDto);

    boolean confirm(String link);
}
