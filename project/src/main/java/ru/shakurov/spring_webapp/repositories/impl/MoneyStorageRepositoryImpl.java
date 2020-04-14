package ru.shakurov.spring_webapp.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.shakurov.spring_webapp.models.MoneyStorage;
import ru.shakurov.spring_webapp.repositories.MoneyStorageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MoneyStorageRepositoryImpl implements MoneyStorageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(MoneyStorage moneyStorage) {
        entityManager.persist(moneyStorage);
    }

    @Override
    public void reduceUserBalanceByUserId(Long id, Long money) {
        entityManager.createQuery("update MoneyStorage set balance = balance-:money where user.id = :user_id")
                .setParameter("money", money)
                .setParameter("user_id", id)
                .executeUpdate();
    }

    @Override
    public void payMoneyToSuperAdmin(Long superAdminId, Long money) {
        entityManager.createQuery("update MoneyStorage set balance = balance+:money where user.id = :super_admin_id")
                .setParameter("money", money)
                .setParameter("super_admin_id", superAdminId)
                .executeUpdate();
    }
}
