package ru.shakurov.spring_webapp.security.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AfterSuccessAuthenticationHandler implements AuthenticationSuccessHandler {
/*    @Autowired
    private UserSessionData userSessionData;*/

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        /*UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        userSessionData.alias(user.getAlias())
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole())
                .user(user);
*/
        httpServletResponse.sendRedirect("/profile");
    }
}
