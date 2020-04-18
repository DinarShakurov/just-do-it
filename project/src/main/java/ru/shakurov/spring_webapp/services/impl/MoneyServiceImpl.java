package ru.shakurov.spring_webapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.GoalCreatingForm;
import ru.shakurov.spring_webapp.repositories.BankRepository;
import ru.shakurov.spring_webapp.repositories.MoneyStorageRepository;
import ru.shakurov.spring_webapp.services.MoneyService;

@Service
public class MoneyServiceImpl implements MoneyService {
    public static Long MIN_PAYMENT = 1L;
    @Autowired
    private MoneyStorageRepository moneyStorageRepository;
    @Autowired
    private BankRepository bankRepository;

    @Override
    @Transactional
    public void replenishUserBalance(Long userId, Long money) {
        moneyStorageRepository.updateBalanceByUserId(userId, money);
    }

    @Override
    public void paymentForGoal(GoalCreatingForm goalCreatingForm) throws BalanceException, MoneyException {
        checkPaymentForGoalExceptions(goalCreatingForm);
        moneyStorageRepository.updateBalanceByUserId(goalCreatingForm.getUserId(), -goalCreatingForm.getMoney());
        bankRepository.updateReservedBalance(1L, goalCreatingForm.getMoney());
    }

    @Override
    public void returnMoneyAfterCompletingGoal(Long moneyToReturnForUser, Long moneyPenalty, Long userId) {
        moneyStorageRepository.updateBalanceByUserId(userId, moneyToReturnForUser);
        bankRepository.update(1L, moneyPenalty, -(moneyToReturnForUser + moneyPenalty));
    }

    private void checkPaymentForGoalExceptions(GoalCreatingForm goalCreatingForm) throws MoneyException, BalanceException {
        if (goalCreatingForm.getMoney() < MIN_PAYMENT) {
            throw new MoneyException();
        }
        if (moneyStorageRepository.getBalanceByUserId(goalCreatingForm.getUserId()) < goalCreatingForm.getMoney()) {
            throw new BalanceException();
        }

    }
}
