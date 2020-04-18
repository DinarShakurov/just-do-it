package ru.shakurov.spring_webapp.repositories;

public interface BankRepository {
    void updateReservedBalance(Long id, Long moneyToReservedBalance);
    void update(Long id, Long moneyToBalance, Long moneyToReservedBalance);
}
