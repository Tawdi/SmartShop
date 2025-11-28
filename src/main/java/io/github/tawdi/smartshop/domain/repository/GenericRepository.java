package io.github.tawdi.smartshop.domain.repository;

import io.github.tawdi.smartshop.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity<I>, I>
        extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {


}

