package io.github.tawdi.smartshop.auth.filter;

import io.github.tawdi.smartshop.auth.annotation.RequireRole;
import io.github.tawdi.smartshop.auth.annotation.Role;
import io.github.tawdi.smartshop.dto.auth.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.lang.reflect.Method;

@Component
@Order(2)
public class AuthorizationFilter extends OncePerRequestFilter {

    private final RequestMappingHandlerMapping handlerMapping;

    public AuthorizationFilter(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        LoginResponse currentUser = (LoginResponse) request.getAttribute("currentUser");

        if (currentUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            HandlerExecutionChain handlerChain = handlerMapping.getHandler(request);
            if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod handlerMethod) {
                Method method = handlerMethod.getMethod();

                RequireRole methodAnnotation = method.getAnnotation(RequireRole.class);
                RequireRole classAnnotation = method.getDeclaringClass().getAnnotation(RequireRole.class);

                RequireRole annotation = methodAnnotation != null ? methodAnnotation : classAnnotation;

                if (annotation != null) {
                    Role requiredRole = annotation.value();

                    boolean hasPermission = switch (requiredRole) {
                        case ADMIN -> "ADMIN".equals(currentUser.role());
                        case CLIENT -> "CLIENT".equals(currentUser.role());
                        case ANY -> true;
                    };

                    if (!hasPermission) {
                        sendForbidden(response, request.getRequestURI());
                        return;
                    }
                }
            }
        } catch (Exception e) {

        }

        filterChain.doFilter(request, response);
    }

    private void sendForbidden(HttpServletResponse response, String path) throws IOException {
        response.setStatus(403);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("""
            {
              "status": "ERROR",
              "message": "Accès refusé : vous n'avez pas les permissions nécessaires",
              "path": "%s"
            }
            """.formatted(path));
    }
}