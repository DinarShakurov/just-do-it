package ru.shakurov.spring_webapp.vk.handler;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.security.details.UserDetailsImpl;
import ru.shakurov.spring_webapp.vk.authentication.VkAuthentication;

@Component
public class VkAuthenticationHandlerImpl implements VkAuthenticationHandler {
    @Override
    public VkAuthentication creatVkAuthentication(String vkToken, User user) {
        VkAuthentication vkAuthentication = new VkAuthentication(vkToken);
        vkAuthentication.setUserDetails(new UserDetailsImpl(user));
        vkAuthentication.setAuthenticated(true);
        return vkAuthentication;
    }

    @Override
    public void addVkAuthenticationToSpring(VkAuthentication vkAuthentication) {
        SecurityContextHolder.getContext().setAuthentication(vkAuthentication);
    }


}
