package ru.shakurov.spring_webapp.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.shakurov.spring_webapp.repositories.BankRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BankRepositoryImpl implements BankRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateReservedBalance(Long id, Long moneyToReservedBalance) {
        entityManager.createQuery("update Bank set reservedBalance = reservedBalance + :money where id = :id")
                .setParameter("money", moneyToReservedBalance)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void update(Long id, Long moneyToBalance, Long moneyToReservedBalance) {
        entityManager.createQuery("update Bank set reservedBalance = reservedBalance + :moneyToReservedBalance," +
                "balance = balance + :moneyToBalance where id = :id")
                .setParameter("moneyToReservedBalance", moneyToReservedBalance)
                .setParameter("moneyToBalance", moneyToBalance)
                .setParameter("id", id)
                .executeUpdate();
    }
}
