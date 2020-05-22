package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shakurov.spring_webapp.dto.CommentsDto;
import ru.shakurov.spring_webapp.forms.CommentForm;
import ru.shakurov.spring_webapp.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/get")
    public ResponseEntity<CommentsDto> getComments(@RequestParam("goal_id") Long goalId,
                                                   @RequestParam("w") Boolean wait) {
        CommentsDto commentsDto = commentService.getCommentsWithLongPolling(goalId, wait);
        return ResponseEntity.ok(commentsDto);
    }

    @PostMapping("/send")
    @ResponseStatus(value = HttpStatus.OK)
    public void sendComment(@RequestBody CommentForm commentForm) {
        commentService.sendComment(commentForm);
    }
}
