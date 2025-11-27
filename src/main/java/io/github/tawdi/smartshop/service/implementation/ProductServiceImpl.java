package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.service.ProductService;
import io.github.tawdi.smartshop.common.mapper.ProductMapper;
import io.github.tawdi.smartshop.common.dto.product.ProductRequestDTO;
import io.github.tawdi.smartshop.common.dto.product.ProductResponseDTO;
import io.github.tawdi.smartshop.common.domain.entity.Product;
import io.github.tawdi.smartshop.common.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends StringCrudServiceImpl<Product, ProductRequestDTO, ProductResponseDTO> implements ProductService {

    public ProductServiceImpl(ProductRepository repository , ProductMapper mapper){
        super(repository,mapper);
    }
}
