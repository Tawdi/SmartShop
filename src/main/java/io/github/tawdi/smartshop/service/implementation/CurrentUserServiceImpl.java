package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.Client;
import io.github.tawdi.smartshop.domain.repository.ClientRepository;
import io.github.tawdi.smartshop.dto.auth.LoginResponse;
import io.github.tawdi.smartshop.exception.AuthenticationException;
import io.github.tawdi.smartshop.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.service.CurrentUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserServiceImpl implements CurrentUserService {

    private final ClientRepository clientRepository;

    public CurrentUserServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getCurrentClient(HttpServletRequest request) {
        LoginResponse user = (LoginResponse) request.getAttribute("currentUser");
        if (user == null || !"CLIENT".equals(user.role())) {
            throw new AuthenticationException("Accès refusé");
        }
        return clientRepository.findByUserId(user.id())
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé"));
    }

    public String getCurrentClientId(HttpServletRequest request) {
        return getCurrentClient(request).getId();
    }
}