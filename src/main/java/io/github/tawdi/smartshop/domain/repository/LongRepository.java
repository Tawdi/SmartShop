package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.id.LongEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LongRepository<T extends LongEntity>
        extends GenericRepository<T, Long> {
}