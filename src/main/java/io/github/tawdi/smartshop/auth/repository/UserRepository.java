package io.github.tawdi.smartshop.auth.repository;

import io.github.tawdi.smartshop.auth.entity.User;
import io.github.tawdi.smartshop.common.domain.repository.StringRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends StringRepository<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
