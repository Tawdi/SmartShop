package io.github.tawdi.smartshop.common.domain.repository;

import io.github.tawdi.smartshop.common.domain.entity.id.UuidEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface UuidRepository<T extends UuidEntity>
        extends GenericRepository<T, UUID> {
}
