package ru.shakurov.spring_webapp.repositories;

import ru.shakurov.spring_webapp.models.MoneyStorage;

public interface MoneyStorageRepository {
    void save(MoneyStorage moneyStorage);

    void updateBalanceByUserId(Long userId, Long money);

    Long getBalanceByUserId(Long userId);
}
