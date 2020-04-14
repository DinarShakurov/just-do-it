package ru.shakurov.spring_webapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.forms.GoalFromProfileForm;
import ru.shakurov.spring_webapp.dto.GoalDto;
import ru.shakurov.spring_webapp.exceptions.DurationException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.GoalForm;
import ru.shakurov.spring_webapp.models.Goal;
import ru.shakurov.spring_webapp.models.GoalState;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.repositories.GoalRepository;
import ru.shakurov.spring_webapp.repositories.MoneyStorageRepository;
import ru.shakurov.spring_webapp.repositories.UserRepository;
import ru.shakurov.spring_webapp.services.GoalService;
import ru.shakurov.spring_webapp.services.GoalTimer;

import javax.annotation.PostConstruct;
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
    private static final Long MAX_DURATION = 31708800000L;
    private static final Long MIN_DURATION = 60000L;

    private static final Map<Long, Thread> timerMap = new HashMap<>();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private MoneyStorageRepository moneyStorageRepository;

    private Long superAdminId;

    @PostConstruct
    private void initial() {
        userRepository.findSuperAdmin().ifPresent(user -> superAdminId = user.getId());
    }

    @Override
    @Transactional
    public void create(GoalForm goalForm, User user) throws DurationException, MoneyException, BalanceException {
        Long duration = goalForm.getMinute() * MINUTE +
                goalForm.getHour() * HOUR +
                goalForm.getDay() * DAY;

        checkCreatingException(duration, goalForm, user);

        Goal goal = Goal.builder()
                .title(goalForm.getTitle())
                .description(goalForm.getDescription())
                .duration(duration)
                .money(goalForm.getMoney())
                .result(0)
                .state(GoalState.ACTIVE)
                .user(user)
                .visibleFotOther(goalForm.isVisible())
                .build();
        moneyStorageRepository.reduceUserBalanceByUserId(user.getId(), goal.getMoney());
        moneyStorageRepository.payMoneyToSuperAdmin(superAdminId, goal.getMoney());
        goalRepository.save(goal);

        GoalTimer goalTimer = new GoalTimer(goal.getId(), goal.getDuration(), this);
        goalTimer.start();
        timerMap.put(goal.getId(), goalTimer);
    }

    @Override
    public Map<GoalState, List<GoalDto>> getUsersGoals(Long userId) {
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
    public void updateResult(GoalFromProfileForm dto) {
        System.out.println(dto);
        goalRepository.updateResultByGoalId(dto.getResult(), dto.getGoalId());
    }

    //TODO
    @Override
    public void completingGoal(Long goalId) {
        goalRepository.makeGoalCompletedById(goalId);
        timerMap.remove(goalId);
    }

    //TODO
    @Override
    public void waitingGoal(Long goalId) {
        goalRepository.makeGoalWaitedById(goalId);
        timerMap.remove(goalId);
    }

    //TODO
    @Override
    public void deletingGoal(Long goalId) {
        GoalTimer goalTimer = (GoalTimer) timerMap.get(goalId);
        goalTimer.flag = false; //stop tread

        timerMap.remove(goalId); //remove from map
        goalRepository.makeGoalDeletedById(goalId);
    }


    private void checkCreatingException(Long duration, GoalForm goalForm, User user) throws DurationException, MoneyException, BalanceException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new DurationException();
        }
        if (goalForm.getMoney() < 1) {
            throw new MoneyException();
        }
        if (user.getMoneyStorage().getBalance() < goalForm.getMoney()) {
            throw new BalanceException();
        }
    }
}
