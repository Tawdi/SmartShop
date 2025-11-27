package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.common.dto.client.ClientRequestDTO;
import io.github.tawdi.smartshop.common.dto.client.ClientResponseDTO;
import io.github.tawdi.smartshop.common.dto.client.ClientWithStatisticsDTO;
import io.github.tawdi.smartshop.common.domain.entity.Client;

public interface ClientService extends StringCrudService<Client, ClientRequestDTO, ClientResponseDTO> {

    ClientResponseDTO createClientWithUser(ClientRequestDTO requestDTO);

    ClientWithStatisticsDTO getClientWithStatistics(String clientId) ;
}
