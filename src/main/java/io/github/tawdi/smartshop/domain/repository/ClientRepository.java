package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.Client;
import io.github.tawdi.smartshop.dto.client.ClientStats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends StringRepository<Client> {

    boolean existsByEmail(String email);

    Optional<Client> findByUserId(String userId);

    @Query("""
                SELECT  COALESCE(COUNT(o), 0) AS totalOrders,
                        COALESCE(SUM(CASE WHEN o.status = 'CONFIRMED' THEN 1 ELSE 0 END), 0) AS confirmedOrdersCount,
                        COALESCE(SUM(CASE WHEN o.status = 'CONFIRMED' THEN o.totalTTC ELSE 0 END), 0) AS totalConfirmedAmount,
                        COALESCE(SUM(o.totalTTC - o.remainingAmount),0)  AS totalPaidAmount
                FROM Order o
                WHERE o.client.id = :clientId
                GROUP BY o.client.id
            """)
    ClientStats getClientStatistics(@Param("clientId") String clientId);

}
