package io.github.tawdi.smartshop.client.dto;

import io.github.tawdi.smartshop.client.entity.CustomerTier;
import io.github.tawdi.smartshop.common.api.dto.BaseResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClientResponseDTO extends BaseResponseDTO<String> {

    private String name;
    private String email;
    private String telephone;
    private String adresse;

    private CustomerTier tier;
}
