package io.github.tawdi.smartshop.client.service;

import io.github.tawdi.smartshop.client.dto.ClientRequestDTO;
import io.github.tawdi.smartshop.client.dto.ClientResponseDTO;
import io.github.tawdi.smartshop.client.entity.Client;
import io.github.tawdi.smartshop.common.service.StringCrudService;

public interface ClientService extends StringCrudService<Client, ClientRequestDTO, ClientResponseDTO> {

    ClientResponseDTO createClientWithUser(ClientRequestDTO requestDTO);

}
