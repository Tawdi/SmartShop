package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.id.LongEntity;
import io.github.tawdi.smartshop.domain.repository.LongRepository;
import io.github.tawdi.smartshop.mapper.BaseMapper;
import io.github.tawdi.smartshop.service.LongCrudService;

public class LongCrudServiceImpl<T extends LongEntity, R1, R2>
        extends BaseCrudServiceImpl<T, R1, R2, Long>
        implements LongCrudService<T, R1, R2> {

    protected LongCrudServiceImpl(LongRepository<T> repository, BaseMapper<T, R1, R2> mapper) {
        super(repository, mapper);
    }

}
