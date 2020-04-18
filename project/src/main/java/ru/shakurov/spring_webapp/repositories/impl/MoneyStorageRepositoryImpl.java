package ru.shakurov.spring_webapp.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.shakurov.spring_webapp.models.MoneyStorage;
import ru.shakurov.spring_webapp.repositories.MoneyStorageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class MoneyStorageRepositoryImpl implements MoneyStorageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(MoneyStorage moneyStorage) {
        entityManager.persist(moneyStorage);
    }

    @Override
    public void updateBalanceByUserId(Long id, Long money) {
        entityManager.createQuery("update MoneyStorage set balance = balance+:money where user.id = :user_id")
                .setParameter("money", money)
                .setParameter("user_id", id)
                .executeUpdate();
    }

    @Override
    public Long getBalanceByUserId(Long userId) {
        return entityManager.createQuery("select balance from MoneyStorage where user.id = :user_id", Long.class)
                .setParameter("user_id", userId)
                .getSingleResult();
    }
}
