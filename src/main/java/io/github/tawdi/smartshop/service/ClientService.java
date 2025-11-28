package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.dto.client.ClientRequestDTO;
import io.github.tawdi.smartshop.dto.client.ClientResponseDTO;
import io.github.tawdi.smartshop.dto.client.ClientWithStatisticsDTO;
import io.github.tawdi.smartshop.domain.entity.Client;

public interface ClientService extends StringCrudService<Client, ClientRequestDTO, ClientResponseDTO> {

    ClientResponseDTO createClientWithUser(ClientRequestDTO requestDTO);

    ClientWithStatisticsDTO getClientWithStatistics(String clientId) ;
}
