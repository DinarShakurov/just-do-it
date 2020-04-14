package ru.shakurov.spring_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.spring_webapp.models.Goal;
import ru.shakurov.spring_webapp.models.GoalState;
import ru.shakurov.spring_webapp.services.impl.GoalServiceImpl;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalDto {
    private Long id;
    private String title;
    private String description;
    private Long money;
    private Long durationLeft;
    private Integer result;
    private boolean visibleFotOther;
    private GoalState goalState;

    private Integer day;
    private Integer hour;
    private Integer minute;

    public static GoalDto from(Goal goal, Long durationLeft) {
        Long duration = goal.getDuration();
        Long day = duration / GoalServiceImpl.DAY;
        duration %= GoalServiceImpl.DAY;
        Long hour = duration / GoalServiceImpl.HOUR;
        duration %= GoalServiceImpl.HOUR;
        Long minute = duration / GoalServiceImpl.MINUTE;

        return GoalDto.builder()
                .description(goal.getDescription())
                .durationLeft(durationLeft)
                .day(day.intValue())
                .hour(hour.intValue())
                .minute(minute.intValue())
                .id(goal.getId())
                .money(goal.getMoney())
                .result(goal.getResult())
                .title(goal.getTitle())
                .visibleFotOther(goal.isVisibleFotOther())
                .goalState(goal.getState())
                .build();
    }
}
