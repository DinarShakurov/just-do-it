package ru.shakurov.spring_webapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(fluent = true)
@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionData {
    private User user;
    private Long id;
    private String email;
    private String alias;
    private Role role;
}
