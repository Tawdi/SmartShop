package io.github.tawdi.smartshop.client.repository;

import io.github.tawdi.smartshop.client.entity.Client;
import io.github.tawdi.smartshop.common.domain.repository.StringRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends StringRepository<Client> {

    boolean existsByEmail(String email);

    @Query("""
                SELECT  COALESCE(COUNT(o), 0),
                        COALESCE(SUM(CASE WHEN o.status = 'CONFIRMED' THEN 1 ELSE 0 END), 0),
                        COALESCE(SUM(CASE WHEN o.status = 'CONFIRMED' THEN o.totalTTC ELSE 0 END), 0),
                        COALESCE(SUM(o.totalTTC - o.remainingAmount),0)
                FROM Order o
                WHERE o.client.id = :clientId
                GROUP BY o.client.id
            """)
    Object[] getClientStatistics(@Param("clientId") String clientId);

}
