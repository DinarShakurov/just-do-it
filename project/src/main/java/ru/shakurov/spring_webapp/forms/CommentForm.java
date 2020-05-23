package ru.shakurov.spring_webapp.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shakurov.spring_webapp.models.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentForm {
    private Long goalId;
    private String text;
    private User user;
}
