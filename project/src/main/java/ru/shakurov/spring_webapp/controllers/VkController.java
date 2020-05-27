package ru.shakurov.spring_webapp.controllers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.spring_webapp.vk.services.VkService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/vk")
public class VkController {
    @Autowired
    private VkService vkService;

    @GetMapping("/code")
    public String code(HttpServletRequest request) {
        String code = request.getParameter("code");
        if (code != null) {
            try {
                vkService.authenticateWithVkCode(code);
            } catch (ClientException | ApiException e) {
                return "redirect:/signUp?error=vk-auth";
            }
        }
        return "redirect:/signIn";
    }
}
