package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.exceptions.DurationException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.GoalCreatingForm;
import ru.shakurov.spring_webapp.forms.UpdateGoalResultForm;
import ru.shakurov.spring_webapp.security.jwt.details.UserDetailsJwtBasedImpl;
import ru.shakurov.spring_webapp.services.GoalService;

@RestController
@RequestMapping("/api/goal")
public class GoalRestController {
    @Autowired
    private GoalService goalService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('USER')")
    @ResponseStatus(value = HttpStatus.OK)
    //TODO: исправить exceptions
    public void createGoal(@AuthenticationPrincipal Authentication authentication,
                           @RequestBody GoalCreatingForm goalCreatingForm) throws BalanceException, MoneyException, DurationException {
        UserDetailsJwtBasedImpl userDetails = (UserDetailsJwtBasedImpl) authentication.getPrincipal();
        goalCreatingForm.setUserId(userDetails.getId());
        goalService.createGoal(goalCreatingForm);
    }

    @PostMapping("/change")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(value = HttpStatus.OK)
    public void changeGoalResult(@RequestBody UpdateGoalResultForm form) {
        goalService.updateResult(form);
    }

    @PostMapping("/complete")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(value = HttpStatus.OK)
    public void completeGoal(@RequestBody UpdateGoalResultForm form) {
        goalService.completingGoal(form);
    }
}
