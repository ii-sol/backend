package sinhan.server1.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import sinhan.server1.global.utils.exception.AuthException;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    JwtService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtService.getAccessToken();

        if (token != null) {
            try {
                Authentication authentication = jwtService.getAuthentication();
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
