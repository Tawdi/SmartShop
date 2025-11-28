package io.github.tawdi.smartshop.mapper;

import io.github.tawdi.smartshop.domain.entity.OrderItem;
import io.github.tawdi.smartshop.dto.order.OrderItemRequestDTO;
import io.github.tawdi.smartshop.dto.order.OrderItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" ,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderItemMapper extends BaseMapper<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO> {

    @Override
    @Mapping(target = "productId" , source = "product.id")
    @Mapping(target = "productName",source = "product.name")
    OrderItemResponseDTO toDto(OrderItem entity);
}
