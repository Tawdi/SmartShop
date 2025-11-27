package io.github.tawdi.smartshop.common.domain.repository;

import io.github.tawdi.smartshop.common.domain.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends StringRepository<Product> {

    boolean existsByReference(String reference);

}
