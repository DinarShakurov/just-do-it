package ru.shakurov.spring_webapp.security.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.models.UserSessionData;
import ru.shakurov.spring_webapp.security.details.UserDetailsImpl;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {
    /*@Autowired
    private UserSessionData userSessionData;*/

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
        /*Authentication authentication = authenticationEvent.getAuthentication();
        if (authentication.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userDetails.getUser();
            userSessionData.alias(user.getAlias())
                    .email(user.getEmail())
                    .id(user.getId())
                    .role(user.getRole())
                    .user(user);
        }*/
        /*if (authenticationEvent instanceof InteractiveAuthenticationSuccessEvent) {
            return;
        }
        Authentication authentication = authenticationEvent.getAuthentication();
        String auditMessage = "Login attempt with username: " +
                authentication.getName() + " " +
                authentication.getAuthorities() + " " +
                authentication.getCredentials() + " " +
                authentication.getPrincipal() + " " +
                "\t\tSuccess: " + authentication.isAuthenticated();
        System.out.println(auditMessage);*/
    }

}