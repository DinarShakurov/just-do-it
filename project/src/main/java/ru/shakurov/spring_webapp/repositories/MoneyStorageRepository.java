package ru.shakurov.spring_webapp.repositories;

import ru.shakurov.spring_webapp.models.MoneyStorage;

public interface MoneyStorageRepository {
    void save(MoneyStorage moneyStorage);

    void reduceUserBalanceByUserId(Long id, Long money);

    void payMoneyToSuperAdmin(Long superAdminId, Long money);
}
