package ru.shakurov.spring_webapp.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ProfileDto> getProfile(@AuthenticationPrincipal Authentication authentication) {
        UserDetailsJwtBasedImpl userDetails = (UserDetailsJwtBasedImpl) authentication.getPrincipal();
        return ResponseEntity.ok(profileService.getUserInfo(userDetails.getId()));
    }


}
