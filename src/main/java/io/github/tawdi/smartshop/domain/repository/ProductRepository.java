package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends StringRepository<Product> {

    boolean existsByReference(String reference);


    Optional<Product> findByIdAndDeleted(String id,Boolean isDeleted);
}
