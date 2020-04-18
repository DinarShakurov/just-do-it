package ru.shakurov.spring_webapp.repositories;

import ru.shakurov.spring_webapp.forms.UpdateGoalResultForm;
import ru.shakurov.spring_webapp.models.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalRepository {
    void save(Goal goal);

    void makeGoalDeletedById(Long goalId);

    void makeGoalCompletedById(UpdateGoalResultForm form);

    void makeGoalWaitedById(Long goalId);

    List<Goal> findAllByUserId(Long userId);

    void updateResultByGoalId(Integer result, Long goalId);

    Optional<Goal> findById(Long goalId);
}
