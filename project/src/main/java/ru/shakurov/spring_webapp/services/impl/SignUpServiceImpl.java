package ru.shakurov.spring_webapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.dto.EmailDto;
import ru.shakurov.spring_webapp.forms.SignUpForm;
import ru.shakurov.spring_webapp.models.*;
import ru.shakurov.spring_webapp.repositories.MoneyStorageRepository;
import ru.shakurov.spring_webapp.repositories.UserRepository;
import ru.shakurov.spring_webapp.services.SignUpService;

import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MoneyStorageRepository moneyStorageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public EmailDto signUp(SignUpForm signUpForm) {
        String uuid = UUID.randomUUID().toString();
        String link = "http://localhost:8080/signUp/confirmation/" + uuid;

        User user = User.builder()
                .email(signUpForm.getEmail())
                .hashPassword(passwordEncoder.encode(signUpForm.getPassword()))
                .name(signUpForm.getUsername())
                .role(Role.USER)
                .state(State.NOT_CONFIRMED)
                .status(Status.ACTIVE)
                .confirmationLink(uuid)
                .build();
        userRepository.save(user);
        MoneyStorage storage = MoneyStorage.builder()
                .balance(0L)
                .reservedBalance(0L)
                .user(user)
                .build();
        moneyStorageRepository.save(storage);

        return EmailDto.builder()
                .link(link)
                .to(user.getEmail())
                .templateName("email_confirmation.ftlh")
                .build();

    }

    @Override
    public boolean confirm(String link) {
        return userRepository.updateStateByLink(link) > 0;
    }
}
