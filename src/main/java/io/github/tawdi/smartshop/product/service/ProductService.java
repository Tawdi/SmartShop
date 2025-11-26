package io.github.tawdi.smartshop.product.service;

import io.github.tawdi.smartshop.common.service.StringCrudService;
import io.github.tawdi.smartshop.product.dto.ProductRequestDTO;
import io.github.tawdi.smartshop.product.dto.ProductResponseDTO;
import io.github.tawdi.smartshop.product.entity.Product;

public interface ProductService extends StringCrudService<Product, ProductRequestDTO, ProductResponseDTO> {
}
