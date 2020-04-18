package ru.shakurov.spring_webapp.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shakurov.spring_webapp.dto.EmailDto;
import ru.shakurov.spring_webapp.components.EmailSender;

@Component
@Aspect
public class EmailAspect {
    @Autowired
    private EmailSender emailSender;

    @AfterReturning(value = "execution(* ru.shakurov.spring_webapp.services.SignUpService.signUp(*))", returning = "emailDto")
    public void signUpAdvice(EmailDto emailDto) {
        String emailTo = emailDto.getTo();
        String templateName = emailDto.getTemplateName();
        String link = emailDto.getLink();

        emailSender
                .setSubject("Registration confirmation")
                .setLink(link)
                .setTemplateName(templateName)
                .setTo(emailTo)
                .send();
    }
}
