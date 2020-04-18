package ru.shakurov.spring_webapp.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalCreatingForm {
    private String title;
    private String description;
    private Long money;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private boolean visible;
    private Long userId;
}
