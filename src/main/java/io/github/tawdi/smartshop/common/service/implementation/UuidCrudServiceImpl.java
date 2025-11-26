package io.github.tawdi.smartshop.common.service.implementation;


import io.github.tawdi.smartshop.common.domain.entity.id.UuidEntity;
import io.github.tawdi.smartshop.common.domain.repository.UuidRepository;
import io.github.tawdi.smartshop.common.mapper.BaseMapper;
import io.github.tawdi.smartshop.common.service.UuidCrudService;

import java.util.UUID;

public class UuidCrudServiceImpl<T extends UuidEntity, R1, R2>
        extends BaseCrudServiceImpl<T, R1, R2, UUID>
        implements UuidCrudService<T, R1, R2> {

    protected UuidCrudServiceImpl(UuidRepository<T> repository, BaseMapper<T, R1, R2> mapper) {
        super(repository, mapper);
    }

}