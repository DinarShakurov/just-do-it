package ru.shakurov.spring_webapp.repositories.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.models.Goal;
import ru.shakurov.spring_webapp.repositories.GoalRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GoalRepositoryImpl implements GoalRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(Goal goal) {
        entityManager.persist(goal);
    }

    @Override
    @Transactional
    public void makeGoalDeletedById(Long goalId) {
        Query query = entityManager.createQuery("update Goal set state = 'DELETED' where id = :goalId");
        query.setParameter("goalId", goalId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void makeGoalCompletedById(Long goalId) {
        Query query = entityManager.createQuery("update Goal set state = 'COMPLETED' where id = :goalId");
        query.setParameter("goalId", goalId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void makeGoalWaitedById(Long goalId) {
        Query query = entityManager.createQuery("update Goal set state = 'WAITING' where id = :goalId");
        query.setParameter("goalId", goalId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public List<Goal> findAllByUserId(Long userId) {
        return entityManager.createQuery("from Goal where user.id = :userId ", Goal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public void updateResultByGoalId(Integer result, Long goalId) {
        entityManager.createQuery("update Goal set result=:result where id = :id")
                .setParameter("result", result)
                .setParameter("id", goalId)
                .executeUpdate();
    }
}
