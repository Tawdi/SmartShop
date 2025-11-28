package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.dto.auth.LoginRequest;
import io.github.tawdi.smartshop.dto.auth.LoginResponse;
import io.github.tawdi.smartshop.auth.service.AuthService;
import io.github.tawdi.smartshop.dto.ApiResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(
            @Valid  @RequestBody LoginRequest request, HttpServletRequest httpRequest) {

        LoginResponse response = authService.login(request);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("CURRENT_USER", response);
        session.setMaxInactiveInterval(30 * 60); // 30 min

        return ResponseEntity.ok(ApiResponseDTO.success("Connexion réussie", response));

    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO<Void>> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(ApiResponseDTO.success("Déconnexion réussie",null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<?>> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("CURRENT_USER") == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponseDTO.error("Non authentifié"));
        }

        LoginResponse loggedUser = (LoginResponse) session.getAttribute("CURRENT_USER");

        return ResponseEntity.ok(ApiResponseDTO.success("Connecté", loggedUser));
    }
}
