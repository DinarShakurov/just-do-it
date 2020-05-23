package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.shakurov.spring_webapp.dto.CommentsDto;
import ru.shakurov.spring_webapp.forms.CommentForm;
import ru.shakurov.spring_webapp.security.details.UserDetailsImpl;
import ru.shakurov.spring_webapp.services.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/get")
    public String getComments(@RequestParam("goal_id") Long goalId,
                              @RequestParam("w") Boolean wait,
                              ModelMap modelMap) {
        CommentsDto commentsDto = commentService.getCommentsWithLongPolling(goalId, wait);
        modelMap.put("commentsDto", commentsDto);
        return "comment_section";
    }

    @PostMapping("/send")
    @ResponseStatus(value = HttpStatus.OK)
    public void sendComment(@RequestBody CommentForm commentForm, Authentication authentication) {
        commentForm.setUser(((UserDetailsImpl) authentication.getPrincipal()).getUser());
        commentService.sendComment(commentForm);
    }

}
