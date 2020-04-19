package ru.shakurov.spring_webapp.security.jwt.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.shakurov.spring_webapp.security.jwt.authentication.JwtAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component("jwtAuthenticationFilter")
public class JwtAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("Authorization");
        Authentication authentication = new JwtAuthentication(token);
        //кладём объект аутентификации в контекст для текущего потока
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(servletRequest, servletResponse);
    }
}
