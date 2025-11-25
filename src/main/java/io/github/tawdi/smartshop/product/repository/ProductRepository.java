package io.github.tawdi.smartshop.product.repository;

import io.github.tawdi.smartshop.common.domain.repository.StringRepository;
import io.github.tawdi.smartshop.product.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends StringRepository<Product> {

    boolean existsByReference(String reference);

}
