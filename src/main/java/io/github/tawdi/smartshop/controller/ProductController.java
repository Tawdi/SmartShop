package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.common.mapper.ProductMapper;
import io.github.tawdi.smartshop.common.dto.product.ProductRequestDTO;
import io.github.tawdi.smartshop.common.dto.product.ProductResponseDTO;
import io.github.tawdi.smartshop.common.domain.entity.Product;
import io.github.tawdi.smartshop.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products management APIs")
public class ProductController extends StringBaseController<Product, ProductRequestDTO, ProductResponseDTO> {


    public ProductController(ProductService service, ProductMapper mapper) {
        super(service, mapper);
    }

}
