package ru.shakurov.spring_webapp.timer;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class GoalTimer extends Thread {

    private static final Long SECOND_IN_MILLIS = TimeUnit.SECONDS.toMillis(1);

    public boolean doTimer = true;
    private Long durationLeft;
    private final Long goalId;
    private Consumer<Long> afterTimerConsumer;

    public GoalTimer(Long goalId, Long durationLeft) {
        this.goalId = goalId;
        this.durationLeft = durationLeft;
    }

    public GoalTimer onTimer(Consumer<Long> consumer) {
        this.afterTimerConsumer = consumer;
        return this;
    }

    @Override
    public void run() {
        while (doTimer) {
            try {
                Thread.sleep(SECOND_IN_MILLIS);
                durationLeft -= SECOND_IN_MILLIS;
                System.out.println(new Date() + "        and still " + durationLeft / 1000 + " second");
                if (durationLeft <= 0) {
                    break;
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        if (doTimer) {
            afterTimerConsumer.accept(goalId);
        }
    }

    public Long getDurationLeft() {
        return durationLeft;
    }

    public Long getGoalId() {
        return goalId;
    }
}