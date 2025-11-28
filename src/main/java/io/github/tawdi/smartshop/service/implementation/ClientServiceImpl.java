package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.Client;
import io.github.tawdi.smartshop.domain.entity.User;
import io.github.tawdi.smartshop.domain.repository.ClientRepository;
import io.github.tawdi.smartshop.domain.repository.UserRepository;
import io.github.tawdi.smartshop.dto.client.ClientRequestDTO;
import io.github.tawdi.smartshop.dto.client.ClientResponseDTO;
import io.github.tawdi.smartshop.dto.client.ClientWithStatisticsDTO;
import io.github.tawdi.smartshop.enums.CustomerTier;
import io.github.tawdi.smartshop.enums.UserRole;
import io.github.tawdi.smartshop.exception.AuthenticationException;
import io.github.tawdi.smartshop.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.mapper.ClientMapper;
import io.github.tawdi.smartshop.service.ClientService;
import io.github.tawdi.smartshop.util.PasswordUtil;
import io.github.tawdi.smartshop.util.TierHelper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ClientServiceImpl extends StringCrudServiceImpl<Client, ClientRequestDTO, ClientResponseDTO> implements ClientService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper, UserRepository userRepository) {
        super(repository, mapper);
        this.clientRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ClientResponseDTO save(ClientRequestDTO requestDTO) {
        return createClientWithUser(requestDTO);
    }

    public ClientResponseDTO createClientWithUser(ClientRequestDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new AuthenticationException("Ce nom d'utilisateur n'est pas disponible");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(PasswordUtil.hash(dto.getPassword()))
                .role(UserRole.CLIENT)
                .build();

        User savedUser = userRepository.save(user);

        Client client = mapper.toEntity(dto);
        client.setUser(savedUser);

        Client savedClient = clientRepository.save(client);

        return mapper.toDto(savedClient);
    }

    @Override
    public ClientWithStatisticsDTO getClientWithStatistics(String clientId) {
        Client client = repository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable"));

        Object[] stats = (Object[]) clientRepository.getClientStatistics(clientId);

        Long totalOrders = 0L;
        Long confirmedOrders = 0L;
        BigDecimal totalConfirmedAmount = BigDecimal.ZERO;
        BigDecimal totalSpent = BigDecimal.ZERO;

        if (stats != null && stats.length != 0) {
            totalOrders = (Long) stats[0];
            confirmedOrders = (Long) stats[1];
            totalConfirmedAmount = (BigDecimal) stats[2];
            totalSpent = (BigDecimal) stats[3];
        }
        CustomerTier currentTier = client.getTier();

        return ClientWithStatisticsDTO.builder()
                .id(client.getId())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .name(client.getName())
                .email(client.getEmail())
                .telephone(client.getTelephone())
                .adresse(client.getAdresse())
                .tier(currentTier)
                .totalOrders(totalOrders)
                .confirmedOrders(confirmedOrders)
                .totalConfirmedAmount(totalConfirmedAmount)
                .totalSpent(totalSpent)
                .currentDiscountRate(TierHelper.discountRateForTier(currentTier))
                .nextTier(getNextTier(currentTier))
                .amountNeededForNextTier(calculateAmountForNextTier(currentTier, totalSpent))
                .build();
    }

    private String getNextTier(CustomerTier current) {
        return switch (current) {
            case BASIC -> "SILVER";
            case SILVER -> "GOLD";
            case GOLD -> "PLATINUM";
            case PLATINUM -> null;
        };
    }

    private BigDecimal calculateAmountForNextTier(CustomerTier current, BigDecimal spent) {
        return switch (current) {
            case BASIC -> TierHelper.amountForNextTier(CustomerTier.BASIC).subtract(spent.max(BigDecimal.ZERO));
            case SILVER -> TierHelper.amountForNextTier(CustomerTier.SILVER).subtract(spent);
            case GOLD -> TierHelper.amountForNextTier(CustomerTier.GOLD).subtract(spent);
            case PLATINUM -> BigDecimal.ZERO;
        };
    }

}
