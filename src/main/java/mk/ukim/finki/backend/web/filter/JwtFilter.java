package mk.ukim.finki.backend.web.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.backend.config.JwtConstants;
import mk.ukim.finki.backend.model.domain.User;
import mk.ukim.finki.backend.service.domain.JwtService;
import mk.ukim.finki.backend.service.domain.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(JwtConstants.HEADER);
        if (headerValue == null || !headerValue.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = headerValue.substring(JwtConstants.TOKEN_PREFIX.length());

        try {
            String username = jwtService.extractUsername(token);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (username == null || authentication != null) {
                filterChain.doFilter(request, response);
                return;
            }

            Optional<User> user = userService.findByUsername(username);
            if (user.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (!jwtService.isExpired(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user.get(),
                        null,
                        user.get().getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (JwtException jwtException) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    jwtException
            );
            return;
        }

        filterChain.doFilter(request, response);

    }
}
