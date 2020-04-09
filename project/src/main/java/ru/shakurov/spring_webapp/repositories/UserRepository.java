package ru.shakurov.spring_webapp.repositories;

import ru.shakurov.spring_webapp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    void save(User user);

    List<User> findAll();

    int updateStateByLink(String link);
}
