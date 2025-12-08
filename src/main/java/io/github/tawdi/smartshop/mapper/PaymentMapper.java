package io.github.tawdi.smartshop.mapper;

import io.github.tawdi.smartshop.domain.entity.Payment;
import io.github.tawdi.smartshop.dto.payment.PaymentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {

    PaymentResponseDTO toDto(Payment entity);

}
