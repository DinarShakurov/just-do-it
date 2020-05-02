package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.dto.CommentsDto;
import ru.shakurov.spring_webapp.forms.CommentForm;

public interface CommentService {
    void sendComment(CommentForm commentForm);

    CommentsDto getCommentsWithLongPolling(Long goalId, Boolean wait);
}
