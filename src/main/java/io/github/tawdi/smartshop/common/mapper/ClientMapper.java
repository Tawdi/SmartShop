package io.github.tawdi.smartshop.common.mapper;

import io.github.tawdi.smartshop.common.domain.entity.Client;
import io.github.tawdi.smartshop.common.dto.client.ClientRequestDTO;
import io.github.tawdi.smartshop.common.dto.client.ClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" ,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper extends BaseMapper<Client, ClientRequestDTO, ClientResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user" , ignore = true)
    @Mapping(target = "tier" , ignore = true)
    Client toEntity(ClientRequestDTO requestDTO);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user" , ignore = true)
    @Mapping(target = "tier" , ignore = true)
    void updateEntityFromDto(ClientRequestDTO dto, @MappingTarget Client entity);
}
