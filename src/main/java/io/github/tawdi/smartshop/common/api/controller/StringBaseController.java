package io.github.tawdi.smartshop.common.api.controller;

import io.github.tawdi.smartshop.common.domain.entity.id.StringEntity;
import io.github.tawdi.smartshop.common.mapper.BaseMapper;
import io.github.tawdi.smartshop.common.service.StringCrudService;

public abstract class StringBaseController<T extends StringEntity, R1, R2> extends AbstractBaseController<T, String, R1, R2> {

    protected StringBaseController(StringCrudService<T, R1, R2> service, BaseMapper<T, R1, R2> mapper) {
        super(service, mapper);
    }
}