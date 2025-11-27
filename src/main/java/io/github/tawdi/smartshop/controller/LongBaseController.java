package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.common.domain.entity.id.LongEntity;
import io.github.tawdi.smartshop.common.mapper.BaseMapper;
import io.github.tawdi.smartshop.service.LongCrudService;

public abstract class LongBaseController<T extends LongEntity, R1, R2> extends AbstractBaseController<T, Long, R1, R2> {

    protected LongBaseController(LongCrudService<T, R1, R2> service, BaseMapper<T, R1, R2> mapper) {
        super(service, mapper);
    }
}