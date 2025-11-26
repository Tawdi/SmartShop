package io.github.tawdi.smartshop.common.service.implementation;


import io.github.tawdi.smartshop.common.domain.entity.id.StringEntity;
import io.github.tawdi.smartshop.common.domain.repository.StringRepository;
import io.github.tawdi.smartshop.common.mapper.BaseMapper;
import io.github.tawdi.smartshop.common.service.StringCrudService;

public abstract class StringCrudServiceImpl<T extends StringEntity, R1, R2>
        extends BaseCrudServiceImpl<T, R1, R2, String>
        implements StringCrudService<T, R1, R2> {

    protected StringCrudServiceImpl(StringRepository<T> repository, BaseMapper<T, R1, R2> mapper) {
        super(repository, mapper);
    }

}