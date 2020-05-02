package ru.shakurov.spring_webapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shakurov.spring_webapp.components.CashedIdPool;
import ru.shakurov.spring_webapp.dto.CommentsDto;
import ru.shakurov.spring_webapp.forms.CommentForm;
import ru.shakurov.spring_webapp.models.Comment;
import ru.shakurov.spring_webapp.models.UserSessionData;
import ru.shakurov.spring_webapp.repositories.CommentRepository;
import ru.shakurov.spring_webapp.repositories.GoalRepository;
import ru.shakurov.spring_webapp.services.CommentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private CashedIdPool cashedIdPool;
    @Autowired
    private UserSessionData userSessionData;

    @Override
    public void sendComment(CommentForm commentForm) {
        Comment comment = Comment.builder()
                .user(userSessionData.user())
                .sendingTime(LocalDateTime.now())
                .text(commentForm.getText())
                .goal(goalRepository.findById(commentForm.getGoalId())
                        .orElseThrow(IllegalArgumentException::new))
                .build();

        commentRepository.save(comment);

        Long goalId = cashedIdPool.cashedOf(commentForm.getGoalId());
        synchronized (goalId) {
            goalId.notifyAll();
            cashedIdPool.dispose(goalId);
        }
    }

    @Override
    public CommentsDto getCommentsWithLongPolling(Long goalId, Boolean wait) {
        if (wait) {
            goalId = cashedIdPool.cashedOf(goalId);
            synchronized (goalId) {
                try {
                    goalId.wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return CommentsDto.builder()
                .goalId(goalId)
                .comments(getCommentsByGoalId(goalId))
                .build();
    }

    private List<Comment> getCommentsByGoalId(Long goalId) {
        return commentRepository.findAllByGoalId(goalId);
    }
}
