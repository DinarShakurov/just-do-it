package ru.shakurov.spring_webapp.vk.handler;

import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.vk.authentication.VkAuthentication;

public interface VkAuthenticationHandler {
    VkAuthentication creatVkAuthentication(String vkToken, User user);

    void addVkAuthenticationToSpring(VkAuthentication vkAuthentication);
}
