package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.auth.annotation.RequireRole;
import io.github.tawdi.smartshop.auth.annotation.Role;
import io.github.tawdi.smartshop.domain.entity.Product;
import io.github.tawdi.smartshop.dto.ApiResponseDTO;
import io.github.tawdi.smartshop.dto.product.ProductRequestDTO;
import io.github.tawdi.smartshop.dto.product.ProductResponseDTO;
import io.github.tawdi.smartshop.dto.product.ProductSearchCriteria;
import io.github.tawdi.smartshop.mapper.ProductMapper;
import io.github.tawdi.smartshop.service.ProductService;
import io.github.tawdi.smartshop.specification.ProductSpecification;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products management APIs")
public class ProductController extends StringBaseController<Product, ProductRequestDTO, ProductResponseDTO> {


    public ProductController(ProductService service, ProductMapper mapper) {
        super(service, mapper);
    }

    @GetMapping("/filters")
    @RequireRole(Role.ADMIN)
    public ResponseEntity<ApiResponseDTO<Page<ProductResponseDTO>>> getAllWithFilters(ProductSearchCriteria criteria, @ParameterObject Pageable pageable) {

        Specification<Product> spec = ProductSpecification.withCriteria(criteria);
        Page<ProductResponseDTO> page = service.findAll(pageable, spec);

        return ResponseEntity.ok(ApiResponseDTO.success("Liste des produits filtrée", page));
    }
}
