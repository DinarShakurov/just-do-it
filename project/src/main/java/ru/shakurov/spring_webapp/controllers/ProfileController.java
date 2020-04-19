package ru.shakurov.spring_webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.dto.ProfileDto;
import ru.shakurov.spring_webapp.models.Role;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.security.details.UserDetailsImpl;
import ru.shakurov.spring_webapp.services.ProfileService;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public String getProfilePage(Authentication authentication, ModelMap modelMap) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (!user.getRole().equals(Role.USER))
            return "redirect:/admin";

        ProfileDto profileDto = profileService.getUserInfo(user.getId());
        modelMap.put("profile", profileDto);
        return "index";
    }
}

