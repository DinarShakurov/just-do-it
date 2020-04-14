package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.dto.GoalDto;
import ru.shakurov.spring_webapp.dto.UserDto;
import ru.shakurov.spring_webapp.models.GoalState;
import ru.shakurov.spring_webapp.models.Role;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.security.details.UserDetailsImpl;
import ru.shakurov.spring_webapp.services.GoalService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private GoalService goalService;

    @GetMapping
    public String getProfilePage(Authentication authentication, ModelMap modelMap) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (!user.getRole().equals(Role.USER))
            return "redirect:/admin";
        Map<GoalState, List<GoalDto>> goals = goalService.getUsersGoals(user.getId());

        modelMap.put("user", UserDto.from(user));
        modelMap.put("active", goals.get(GoalState.ACTIVE));
        modelMap.put("waiting", goals.get(GoalState.WAITING));
        modelMap.put("deleted", goals.get(GoalState.DELETED));
        modelMap.put("completed", goals.get(GoalState.COMPLETED));
        return "personal_profile";
    }


}
