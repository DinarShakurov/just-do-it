package ru.shakurov.spring_webapp.repositories;

import ru.shakurov.spring_webapp.models.Goal;

import java.util.List;

public interface GoalRepository {
    void save(Goal goal);

    void makeGoalDeletedById(Long goalId);

    void makeGoalCompletedById(Long goalId);

    void makeGoalWaitedById(Long goalId);

    List<Goal> findAllByUserId(Long userId);

    void updateResultByGoalId(Integer result, Long goalId);
}
