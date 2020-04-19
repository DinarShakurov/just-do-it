package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shakurov.spring_webapp.dto.ProfileDto;
import ru.shakurov.spring_webapp.security.jwt.details.UserDetailsJwtBasedImpl;
import ru.shakurov.spring_webapp.services.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsJwtBasedImpl userDetails = (UserDetailsJwtBasedImpl) authentication.getDetails();
        return ResponseEntity.ok(profileService.getUserInfo(userDetails.getId()));
    }

    @GetMapping("/test")
    public ResponseEntity<ProfileDto> test(@AuthenticationPrincipal UserDetailsJwtBasedImpl userDetails) {
        System.out.println(userDetails);
        return null;
    }
}
