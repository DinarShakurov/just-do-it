package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.GoalCreatingForm;

public interface MoneyService {
    void replenishUserBalance(Long userId, Long money);

    void paymentForGoal(GoalCreatingForm goalCreatingForm) throws BalanceException/*, MoneyException*/;

    void returnMoneyAfterCompletingGoal(Long moneyToReturnForUser, Long moneyForCompleting, Long userId);
}
