package io.github.tawdi.smartshop.client.service;

import io.github.tawdi.smartshop.auth.entity.User;
import io.github.tawdi.smartshop.auth.entity.UserRole;
import io.github.tawdi.smartshop.auth.repository.UserRepository;
import io.github.tawdi.smartshop.client.dto.ClientMapper;
import io.github.tawdi.smartshop.client.dto.ClientRequestDTO;
import io.github.tawdi.smartshop.client.dto.ClientResponseDTO;
import io.github.tawdi.smartshop.client.entity.Client;
import io.github.tawdi.smartshop.client.repository.ClientRepository;
import io.github.tawdi.smartshop.common.service.implementation.StringCrudServiceImpl;
import io.github.tawdi.smartshop.util.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends StringCrudServiceImpl<Client, ClientRequestDTO, ClientResponseDTO> implements ClientService {

    private final UserRepository userRepository;

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper , UserRepository userRepository){
        super(repository,mapper);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ClientResponseDTO save(ClientRequestDTO requestDTO) {
        return createClientWithUser(requestDTO);
    }

    public ClientResponseDTO createClientWithUser(ClientRequestDTO dto) {

        User user = User.builder()
                .username(dto.getUsername())
                .password(PasswordUtil.hash(dto.getPassword()))
                .role(UserRole.CLIENT)
                .build();

        User savedUser = userRepository.save(user);

        Client client = mapper.toEntity(dto);
        client.setUser(savedUser);

        Client savedClient = repository.save(client);

        return mapper.toDto(savedClient);
    }


}
