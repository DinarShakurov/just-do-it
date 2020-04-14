package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.forms.GoalFromProfileForm;
import ru.shakurov.spring_webapp.dto.GoalDto;
import ru.shakurov.spring_webapp.exceptions.DurationException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.GoalForm;
import ru.shakurov.spring_webapp.models.GoalState;
import ru.shakurov.spring_webapp.models.User;

import java.util.List;
import java.util.Map;

public interface GoalService {
    void completingGoal(Long goalId);

    void waitingGoal(Long goalId);

    void deletingGoal(Long goalId);

    void create(GoalForm goalForm, User user) throws DurationException, MoneyException, BalanceException;

    Map<GoalState, List<GoalDto>> getUsersGoals(Long userId);

    void updateResult(GoalFromProfileForm dto);
}
