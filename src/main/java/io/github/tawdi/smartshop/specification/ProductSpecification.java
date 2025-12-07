package io.github.tawdi.smartshop.specification;

import io.github.tawdi.smartshop.domain.entity.Product;
import io.github.tawdi.smartshop.dto.product.ProductSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withCriteria(ProductSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getIncludeDeleted() == null || !criteria.getIncludeDeleted()) {
                predicates.add(cb.equal(root.get("deleted"), false));
            }

            if (criteria.getReference() != null && !criteria.getReference().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("reference")), "%" + criteria.getReference().toLowerCase() + "%"));
            }

            if (criteria.getName() != null && !criteria.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("priceHT"), criteria.getMinPrice()));
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("priceHT"), criteria.getMaxPrice()));
            }

            if (criteria.getMinStock() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("stock"), criteria.getMinStock()));
            }

            if (criteria.getMaxStock() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("stock"), criteria.getMaxStock()));
            }

            if (Boolean.TRUE.equals(criteria.getAvailableOnly())) {
                predicates.add(cb.greaterThan(root.get("stock"), 0));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}