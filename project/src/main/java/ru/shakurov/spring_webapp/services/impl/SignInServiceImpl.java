package ru.shakurov.spring_webapp.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shakurov.spring_webapp.dto.SignInDto;
import ru.shakurov.spring_webapp.dto.TokenDto;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.repositories.UserRepository;
import ru.shakurov.spring_webapp.services.SignInService;

import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public TokenDto signIn(SignInDto dto) {
        Optional<User> userOptional = userRepository.findByEmail(dto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(dto.getPassword(), user.getHashPassword())) {
                String token = Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("email", user.getEmail())
                        .claim("name", user.getName())
                        .claim("role", user.getRole())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not founded");
    }
}
