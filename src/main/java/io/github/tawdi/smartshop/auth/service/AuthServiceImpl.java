package io.github.tawdi.smartshop.auth.service;

import io.github.tawdi.smartshop.common.dto.auth.LoginRequest;
import io.github.tawdi.smartshop.common.dto.auth.LoginResponse;
import io.github.tawdi.smartshop.common.domain.entity.User;
import io.github.tawdi.smartshop.common.domain.repository.UserRepository;
import io.github.tawdi.smartshop.common.exception.AuthenticationException;
import io.github.tawdi.smartshop.common.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        if (!PasswordUtil.check(request.password(), user.getPassword())) {
            throw new AuthenticationException("Mot de passe incorrect");
        }

        return new LoginResponse(user.getId(), user.getUsername(), user.getRole().name());

    }

}
