package io.github.tawdi.smartshop.product.controller;

import io.github.tawdi.smartshop.common.api.controller.StringBaseController;
import io.github.tawdi.smartshop.product.dto.ProductMapper;
import io.github.tawdi.smartshop.product.dto.ProductRequestDTO;
import io.github.tawdi.smartshop.product.dto.ProductResponseDTO;
import io.github.tawdi.smartshop.product.entity.Product;
import io.github.tawdi.smartshop.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products management APIs")
public class ProductController extends StringBaseController<Product, ProductRequestDTO, ProductResponseDTO> {


    public ProductController(ProductService service, ProductMapper mapper){
        super(service,mapper);
    }

}
