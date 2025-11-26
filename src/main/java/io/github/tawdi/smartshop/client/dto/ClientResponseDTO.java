package io.github.tawdi.smartshop.client.dto;

import io.github.tawdi.smartshop.client.entity.CustomerTier;
import io.github.tawdi.smartshop.common.api.dto.BaseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientResponseDTO extends BaseResponseDTO<String> {

    private String name;
    private String email;
    private String telephone;
    private String adresse;

    private CustomerTier tier;
}
