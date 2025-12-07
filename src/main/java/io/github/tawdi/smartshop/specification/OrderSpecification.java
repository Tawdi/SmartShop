package io.github.tawdi.smartshop.specification;

import io.github.tawdi.smartshop.domain.entity.Order;
import io.github.tawdi.smartshop.dto.order.OrderSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> withCriteria(OrderSearchCriteria criteria, String clientIdIfClient) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (clientIdIfClient != null) {
                predicates.add(cb.equal(root.get("client").get("id"), clientIdIfClient));
            }

            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDate.class), criteria.getDateFrom()));
            }

            if (criteria.getDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDate.class), criteria.getDateTo()));
            }

            if (criteria.getMinTotal() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("totalTTC"), criteria.getMinTotal()));
            }

            if (criteria.getMaxTotal() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("totalTTC"), criteria.getMaxTotal()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}