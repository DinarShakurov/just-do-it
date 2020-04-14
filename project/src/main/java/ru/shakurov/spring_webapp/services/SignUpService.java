package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.dto.EmailDto;
import ru.shakurov.spring_webapp.forms.SignUpForm;

public interface SignUpService {
    EmailDto signUp(SignUpForm signUpForm);

    boolean confirm(String link);
}
