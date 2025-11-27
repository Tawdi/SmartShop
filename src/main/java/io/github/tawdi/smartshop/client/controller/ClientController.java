package io.github.tawdi.smartshop.client.controller;

import io.github.tawdi.smartshop.client.dto.ClientMapper;
import io.github.tawdi.smartshop.client.dto.ClientRequestDTO;
import io.github.tawdi.smartshop.client.dto.ClientResponseDTO;
import io.github.tawdi.smartshop.client.entity.Client;
import io.github.tawdi.smartshop.client.service.ClientService;
import io.github.tawdi.smartshop.common.api.controller.StringBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Clients management APIs")
public class ClientController extends StringBaseController<Client, ClientRequestDTO, ClientResponseDTO> {

    public ClientController(ClientService service, ClientMapper mapper) {
        super(service, mapper);
    }


}
