package io.github.tawdi.smartshop.mapper;

import io.github.tawdi.smartshop.domain.entity.PromoCode;
import io.github.tawdi.smartshop.dto.order.PromoCodeRequestDTO;
import io.github.tawdi.smartshop.dto.order.PromoCodeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PromoCodeMapper extends BaseMapper<PromoCode, PromoCodeRequestDTO, PromoCodeResponseDTO> {
}
