package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.exceptions.BalanceException;
import ru.shakurov.spring_webapp.exceptions.DurationException;
import ru.shakurov.spring_webapp.exceptions.MoneyException;
import ru.shakurov.spring_webapp.forms.UpdateGoalResultForm;
import ru.shakurov.spring_webapp.forms.GoalCreatingForm;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.security.details.UserDetailsImpl;
import ru.shakurov.spring_webapp.services.GoalService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/goal")
public class GoalController {
    private static final String DURATION_ERROR = "Duration can not be less than 1 minute";
    private static final String MONEY_ERROR = "MONEY can not be less than 1 minute";
    private static final String SUCCESS = "Goal was created successfully";
    private static final String BALANCE_ERROR = "You don't have enough money";

    private static final Map<String, String> messageMap = new HashMap<>();

    static {
        messageMap.put("success", SUCCESS);
        messageMap.put("money", MONEY_ERROR);
        messageMap.put("duration", DURATION_ERROR);
        messageMap.put("balance", BALANCE_ERROR);
    }

    @Autowired
    private GoalService goalService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/create")
    public String getCreatingGoalPage( ModelMap modelMap, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("status")) {
            modelMap.put("status", messageMap.get(request.getParameter("status")));
        }
        return "goal_creating";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public String createGoal(Authentication authentication, GoalCreatingForm goalCreatingForm, ModelMap modelMap) {
        modelMap.put("status", "success");
        try {
            User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
            goalCreatingForm.setUserId(user.getId());
            goalService.createGoal(goalCreatingForm);
        } catch (DurationException durationException) {
            modelMap.put("status", "duration");
        } catch (MoneyException e) {
            modelMap.put("status", "money");
        } catch (BalanceException e) {
            modelMap.put("status", "balance");
        }
        return "redirect:/goal/create";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/change")
    public String changeGoalResult(UpdateGoalResultForm form) {
        goalService.updateResult(form);
        return "redirect:/profile";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/complete")
    public String completeGoal(UpdateGoalResultForm form) {
        goalService.completingGoal(form);
        return "redirect:/profile";
    }
}
