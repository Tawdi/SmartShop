package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.dto.product.ProductRequestDTO;
import io.github.tawdi.smartshop.dto.product.ProductResponseDTO;
import io.github.tawdi.smartshop.domain.entity.Product;

public interface ProductService extends StringCrudService<Product, ProductRequestDTO, ProductResponseDTO> {
}
