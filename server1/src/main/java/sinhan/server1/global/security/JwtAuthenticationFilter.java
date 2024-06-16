package sinhan.server1.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import sinhan.server1.global.utils.exception.AuthException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    JwtService jwtService;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        if (requestURI.startsWith("/auth")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = jwtService.getAccessToken();

        if (token != null) {
            Authentication authentication = null;
            try {
                authentication = jwtService.getAuthentication(token);
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
