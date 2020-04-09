package ru.shakurov.spring_webapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shakurov.spring_webapp.dto.EmailDto;
import ru.shakurov.spring_webapp.dto.SignUpDto;
import ru.shakurov.spring_webapp.models.Role;
import ru.shakurov.spring_webapp.models.State;
import ru.shakurov.spring_webapp.models.Status;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.repositories.UserRepository;
import ru.shakurov.spring_webapp.services.SignUpService;

import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public EmailDto signUp(SignUpDto signUpDto) {
        String uuid = UUID.randomUUID().toString();
        String link = "http://localhost:8080/signUp/confirmation/" + uuid;

        User user = User.builder()
                .email(signUpDto.getEmail())
                .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                .name(signUpDto.getUsername())
                .role(Role.USER)
                .state(State.NOT_CONFIRMED)
                .status(Status.ACTIVE)
                .confirmationLink(uuid)
                .build();

        userRepository.save(user);

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
