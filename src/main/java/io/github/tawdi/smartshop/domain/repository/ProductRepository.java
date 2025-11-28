package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends StringRepository<Product> {

    boolean existsByReference(String reference);

}
