package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/goal")
public class GoalController {
    private static final String SUCCESS = "Goal was created successfully";
    private static final String BALANCE_ERROR = "You don't have enough money";

    private static final Map<String, String> messageMap = new HashMap<>();
    private static final Map<String, String> radioValues = new HashMap<>();

    static {
        messageMap.put("success", SUCCESS);
        messageMap.put("balance", BALANCE_ERROR);
        radioValues.put("true", "Yes");
        radioValues.put("false", "No");
    }

    @Autowired
    private GoalService goalService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/create")
    public String getCreatingGoalPage(ModelMap modelMap, HttpServletRequest request) {

        modelMap.put("goalCreatingForm", new GoalCreatingForm());
        modelMap.put("radioValues",radioValues);
        if (request.getParameterMap().containsKey("status")) {
            modelMap.put("status", messageMap.get(request.getParameter("status")));
        }
        return "goal_creating";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public String createGoal(Authentication authentication,
                             @Valid GoalCreatingForm goalCreatingForm,
                             BindingResult bindingResult,
                             ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.put("radioValues",radioValues);
            return "goal_creating";
        }

        modelMap.put("status", "success");
        try {
            User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
            goalCreatingForm.setUserId(user.getId());
            goalService.createGoal(goalCreatingForm);
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
