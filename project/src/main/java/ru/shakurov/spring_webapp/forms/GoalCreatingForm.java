package ru.shakurov.spring_webapp.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.spring_webapp.validation.ValidGoalCreatingForm;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ValidGoalCreatingForm(groups = GoalCreatingForm.class, message = "{error.goal.create.duration}")
@GroupSequence(value = {GoalCreatingForm.class})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalCreatingForm {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    @Min(value = 1, message = "{error.goal.create.money_is_tight}")
    private Long money;
    @NotNull
    private Integer day;
    @NotNull
    private Integer hour;
    @NotNull
    private Integer minute;
    private boolean visible;
    private Long userId;
}
