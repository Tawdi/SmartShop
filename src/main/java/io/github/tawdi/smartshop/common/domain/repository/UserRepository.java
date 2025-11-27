package io.github.tawdi.smartshop.common.domain.repository;

import io.github.tawdi.smartshop.common.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends StringRepository<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
