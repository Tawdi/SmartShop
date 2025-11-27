package io.github.tawdi.smartshop.client.controller;

import io.github.tawdi.smartshop.client.dto.ClientMapper;
import io.github.tawdi.smartshop.client.dto.ClientRequestDTO;
import io.github.tawdi.smartshop.client.dto.ClientResponseDTO;
import io.github.tawdi.smartshop.client.dto.ClientWithStatisticsDTO;
import io.github.tawdi.smartshop.client.entity.Client;
import io.github.tawdi.smartshop.client.repository.ClientRepository;
import io.github.tawdi.smartshop.client.service.ClientService;
import io.github.tawdi.smartshop.common.api.controller.StringBaseController;
import io.github.tawdi.smartshop.common.api.dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Clients management APIs")
public class ClientController extends StringBaseController<Client, ClientRequestDTO, ClientResponseDTO> {

    private final ClientService clientService;

    public ClientController(ClientService service, ClientMapper mapper) {
        super(service, mapper);
        this.clientService = service;
    }


    @GetMapping("/{id}/statistics")
    @Operation(summary = "Récupérer un client avec toutes ses statistiques de fidélité")
    public ResponseEntity<ApiResponseDTO<ClientWithStatisticsDTO>> getClientStatistics(@PathVariable String id) {
        ClientWithStatisticsDTO stats =  clientService.getClientWithStatistics(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Statistiques récupérées", stats));
    }


}
