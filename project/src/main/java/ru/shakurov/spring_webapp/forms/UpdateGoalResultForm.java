package ru.shakurov.spring_webapp.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.spring_webapp.models.GoalState;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoalResultForm {
    private Integer result;
    private Long goalId;
    private GoalState goalState;
}
