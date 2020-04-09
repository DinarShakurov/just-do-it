package ru.shakurov.spring_webapp.repositories.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.repositories.UserRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> tq = entityManager.createQuery("from User where email = :email", User.class);
        tq.setParameter("email", email);
        try {
            return Optional.ofNullable(tq.getSingleResult());
        } catch (NoResultException | NonUniqueResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);

    }

    @Override
    @Transactional
    public List<User> findAll() {
        TypedQuery<User> tq = entityManager.createQuery("from User", User.class);
        return tq.getResultList();
    }

    @Override
    @Transactional
    public int updateStateByLink(String link) {
        Query query = entityManager.createQuery("update User set state = 'CONFIRMED' where confirmationLink = :link");
        query.setParameter("link", link);
        return query.executeUpdate();
    }
}
