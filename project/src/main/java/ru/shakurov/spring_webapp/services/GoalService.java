package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.UpdateGoalResultForm;
import ru.shakurov.spring_webapp.dto.GoalDto;
import ru.shakurov.spring_webapp.exceptions.DurationException;
import ru.shakurov.spring_webapp.forms.GoalCreatingForm;
import ru.shakurov.spring_webapp.models.GoalState;

import java.util.List;
import java.util.Map;

public interface GoalService {
    void completingGoal(UpdateGoalResultForm form);

    void waitingGoal(Long goalId);

    void deletingGoal(Long goalId);

    void createGoal(GoalCreatingForm goalCreatingForm) throws DurationException, BalanceException, MoneyException;

    Map<GoalState, List<GoalDto>> getUsersGoalsSortedByLeftTime(Long userId);

    void updateResult(UpdateGoalResultForm dto);
}
