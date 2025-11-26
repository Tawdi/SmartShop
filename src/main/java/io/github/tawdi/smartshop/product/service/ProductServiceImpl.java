package io.github.tawdi.smartshop.product.service;

import io.github.tawdi.smartshop.common.service.implementation.StringCrudServiceImpl;
import io.github.tawdi.smartshop.product.dto.ProductMapper;
import io.github.tawdi.smartshop.product.dto.ProductRequestDTO;
import io.github.tawdi.smartshop.product.dto.ProductResponseDTO;
import io.github.tawdi.smartshop.product.entity.Product;
import io.github.tawdi.smartshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends StringCrudServiceImpl<Product, ProductRequestDTO, ProductResponseDTO> implements ProductService {

    public ProductServiceImpl(ProductRepository repository , ProductMapper mapper){
        super(repository,mapper);
    }
}
