package io.github.tawdi.smartshop.common.domain.repository;

import io.github.tawdi.smartshop.common.domain.entity.id.StringEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface StringRepository<T extends StringEntity>
        extends GenericRepository<T, String> {
}