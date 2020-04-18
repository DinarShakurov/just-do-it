package ru.shakurov.spring_webapp.logic;

import java.util.Date;
import java.util.function.Consumer;

public class GoalTimer extends Thread {
    public boolean doTimer = true;
    public static final Long SECOND = 1000L;

    private Long durationLeft;
    private final Long goalId;
    private Consumer<Long> waitingGoalConsumer;

    public GoalTimer(Long goalId, Long durationLeft) {
        this.goalId = goalId;
        this.durationLeft = durationLeft;
    }

    public GoalTimer onTimer(Consumer<Long> consumer) {
        this.waitingGoalConsumer = consumer;
        return this;
    }

    @Override
    public void run() {
        while (doTimer) {
            try {
                Thread.sleep(SECOND);
                durationLeft -= SECOND;
                System.out.println(new Date() + "        and still " + durationLeft / 1000 + " second");
                if (durationLeft <= 0) {
                    break;
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        if (doTimer) {
            waitingGoalConsumer.accept(goalId);
        }
    }

    public Long getDurationLeft() {
        return durationLeft;
    }

    public Long getGoalId() {
        return goalId;
    }
}