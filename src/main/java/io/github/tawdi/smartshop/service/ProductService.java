package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.common.dto.product.ProductRequestDTO;
import io.github.tawdi.smartshop.common.dto.product.ProductResponseDTO;
import io.github.tawdi.smartshop.common.domain.entity.Product;

public interface ProductService extends StringCrudService<Product, ProductRequestDTO, ProductResponseDTO> {
}
