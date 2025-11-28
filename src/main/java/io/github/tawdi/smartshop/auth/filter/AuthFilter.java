package io.github.tawdi.smartshop.auth.filter;

import io.github.tawdi.smartshop.dto.auth.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Routes public
        if (path.startsWith("/") ||
                path.startsWith("/api/auth/") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger") ||
                path.contains("swagger") ||
                path.contains("api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        LoginResponse currentUser = session != null ?
                (LoginResponse) session.getAttribute("CURRENT_USER") : null;

        if (currentUser == null) {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("""
                {
                 "status": "ERROR",
                  "message": "Authentification requise",
                  "path": "%s"
                }
                """.formatted(path));
            return;
        }

        request.setAttribute("currentUser", currentUser);

        filterChain.doFilter(request, response);
    }
}