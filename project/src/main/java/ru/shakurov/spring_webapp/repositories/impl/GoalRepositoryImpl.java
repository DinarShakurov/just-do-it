package ru.shakurov.spring_webapp.repositories.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.forms.UpdateGoalResultForm;
import ru.shakurov.spring_webapp.models.Goal;
import ru.shakurov.spring_webapp.repositories.GoalRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

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
    public void makeGoalCompletedById(UpdateGoalResultForm form) {
        entityManager.createQuery("update Goal set state = :state, result = :result where id = :goalId")
                .setParameter("goalId", form.getGoalId())
                .setParameter("result", form.getResult())
                .setParameter("state", form.getGoalState())
                .executeUpdate();
    }

    @Override
    @Transactional
    public void makeGoalWaitedById(Long goalId) {
        Query query = entityManager.createQuery("update Goal set state = 'WAITING' where id = :goalId");
        query.setParameter("goalId", goalId);
        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    public Optional<Goal> findById(Long goalId) {
        return Optional.ofNullable(entityManager.createQuery("from Goal where id = :id", Goal.class)
                .setParameter("id", goalId)
                .getSingleResult());
    }
}
