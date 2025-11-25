package io.github.tawdi.smartshop.client.repository;

import io.github.tawdi.smartshop.client.entity.Client;
import io.github.tawdi.smartshop.common.domain.repository.StringRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends StringRepository<Client> {

    boolean existsByEmail(String email);

}
