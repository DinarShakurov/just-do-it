package ru.shakurov.spring_webapp.services;

import java.util.Date;

public class GoalTimer extends Thread {
    public boolean flag = true;
    public static final Long SECOND = 1000L;

    private Long durationLeft;
    private Long goalId;
    private GoalService goalService;

    public GoalTimer(Long goalId, Long durationLeft, GoalService goalService) {
        this.goalId = goalId;
        this.goalService = goalService;
        this.durationLeft = durationLeft;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                Thread.sleep(SECOND);
                durationLeft -= SECOND;
                System.out.println(new Date() +"        and still " + durationLeft /1000 + " second");
                if (durationLeft <= 0) {
                    break;
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        if (flag) {
            goalService.waitingGoal(goalId);
        }
    }

    public Long getDurationLeft() {
        return durationLeft;
    }

    public Long getGoalId() {
        return goalId;
    }


}