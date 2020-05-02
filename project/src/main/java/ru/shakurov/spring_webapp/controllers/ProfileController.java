package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.dto.ProfileDto;
import ru.shakurov.spring_webapp.models.Role;
import ru.shakurov.spring_webapp.models.UserSessionData;
import ru.shakurov.spring_webapp.services.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserSessionData userSessionData;

    @GetMapping
    public String getProfilePage(ModelMap modelMap) {
        if (!userSessionData.role().equals(Role.USER))
            return "redirect:/admin";
        ProfileDto profileDto = profileService.getUserInfo(userSessionData.id());
        modelMap.put("profile", profileDto);
        return "index";
    }
}

