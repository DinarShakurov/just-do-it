package ru.shakurov.spring_webapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.UpdateGoalResultForm;
import ru.shakurov.spring_webapp.dto.GoalDto;
import ru.shakurov.spring_webapp.exceptions.DurationException;
import ru.shakurov.spring_webapp.forms.GoalCreatingForm;
import ru.shakurov.spring_webapp.models.Goal;
import ru.shakurov.spring_webapp.models.GoalState;
import ru.shakurov.spring_webapp.repositories.GoalRepository;
import ru.shakurov.spring_webapp.repositories.UserRepository;
import ru.shakurov.spring_webapp.services.GoalService;
import ru.shakurov.spring_webapp.timer.GoalTimer;
import ru.shakurov.spring_webapp.services.MoneyService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {
    public static final Long MINUTE = 60000L;
    public static final Long HOUR = 3600000L;
    public static final Long DAY = 86400000L;
    /*public static final Long MAX_DURATION = 31708800000L;
    public static final Long MIN_DURATION = 60000L;*/

    private static final Map<Long, Thread> timerMap = new HashMap<>();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private MoneyService moneyService;

    @Override
    @Transactional
    public void createGoal(GoalCreatingForm goalCreatingForm) throws /*DurationException, */BalanceException/*, MoneyException*/ {
        Long duration = goalCreatingForm.getMinute() * MINUTE +
                goalCreatingForm.getHour() * HOUR +
                goalCreatingForm.getDay() * DAY;

        /*checkCreatingExceptions(duration);*/

        moneyService.paymentForGoal(goalCreatingForm);

        Goal goal = Goal.builder()
                .title(goalCreatingForm.getTitle())
                .description(goalCreatingForm.getDescription())
                .duration(duration)
                .money(goalCreatingForm.getMoney())
                .result(0)
                .state(GoalState.ACTIVE)
                .user(userRepository.findById(goalCreatingForm.getUserId()).get())
                .visibleFotOther(goalCreatingForm.isVisible())
                .build();

        goalRepository.save(goal);

        GoalTimer goalTimer = new GoalTimer(goal.getId(), goal.getDuration());
        goalTimer.onTimer(this::waitingGoal).start();

        timerMap.put(goal.getId(), goalTimer);
    }

    @Override
    public Map<GoalState, List<GoalDto>> getUsersGoalsSortedByLeftTime(Long userId) {
        List<Goal> goals = goalRepository.findAllByUserId(userId);

        Map<GoalState, List<GoalDto>> map = goals.stream()
                .map(goal -> {
                    GoalTimer goalTimer = (GoalTimer) timerMap.get(goal.getId());
                    if (goalTimer == null)
                        return GoalDto.from(goal, 0L);
                    return GoalDto.from(goal, goalTimer.getDurationLeft());
                })
                .collect(Collectors.groupingBy(GoalDto::getGoalState));

        List<GoalDto> activeList = map.get(GoalState.ACTIVE);
        if (activeList != null && activeList.size() > 0) {
            activeList.sort(Comparator.comparing(GoalDto::getDurationLeft));
        }
        return map;
    }

    @Override
    public void updateResult(UpdateGoalResultForm dto) {
        System.out.println(dto);
        goalRepository.updateResultByGoalId(dto.getResult(), dto.getGoalId());
    }

    @Override
    @Transactional
    public void completingGoal(UpdateGoalResultForm form) {
        form.setGoalState(GoalState.COMPLETED);
        timerMap.remove(form.getGoalId());
        goalRepository.makeGoalCompletedById(form);

        Goal goal = goalRepository.findById(form.getGoalId()).get();
        Long moneyToReturnForUser = goal.getMoney() * goal.getResult() / 100;
        Long moneyPenalty = goal.getMoney() - moneyToReturnForUser;
        moneyService.returnMoneyAfterCompletingGoal(moneyToReturnForUser, moneyPenalty, goal.getUser().getId());

    }

    /**
     * TODO: возможно стоит сделать отдельный таймер, чтобы Goal после окончания таймера, был доступен для заверешния только определённое кол-во времени
     */
    @Override
    public void waitingGoal(Long goalId) {
        goalRepository.makeGoalWaitedById(goalId);
        timerMap.remove(goalId);
    }

    /**
     * TODO: возможно понадобится дополнительная логика при удалении, мб для какой-нибудь статистики (надо будет узнать)
     */
    @Override
    public void deletingGoal(Long goalId) {
        GoalTimer goalTimer = (GoalTimer) timerMap.get(goalId);
        goalTimer.doTimer = false; //stop thread

        timerMap.remove(goalId);
        goalRepository.makeGoalDeletedById(goalId);
    }


    /*private void checkCreatingExceptions(Long duration) throws DurationException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new DurationException();
        }
    }*/

}
