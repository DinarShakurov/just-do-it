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
import ru.shakurov.spring_webapp.forms.GoalFromProfileForm;
import ru.shakurov.spring_webapp.forms.GoalForm;
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
    public String getCreatingGoalPage(ModelMap modelMap, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("status")) {
            modelMap.put("status", messageMap.get(request.getParameter("status")));
        }
        return "goal_creating";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    private String createGoal(Authentication authentication, GoalForm goalForm, ModelMap modelMap) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        modelMap.put("status", "success");
        try {
            goalService.create(goalForm, userDetails.getUser());
        } catch (DurationException durationException) {
            modelMap.put("status", "duration");
        } catch (MoneyException moneyException) {
            modelMap.put("status", "money");
        } catch (BalanceException balanceException){
            modelMap.put("status", "balance");
        }
        return "redirect:/goal/create";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/change")
    public String changeGoalResult(GoalFromProfileForm dto) {
        goalService.updateResult(dto);
        return "redirect:/profile";
    }

    //TODO
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/complete")
    public String completeGoal(GoalFromProfileForm dto) {
        return "redirect:/profile";
    }
}
