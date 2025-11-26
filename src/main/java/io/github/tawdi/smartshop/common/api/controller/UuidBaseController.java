package io.github.tawdi.smartshop.common.api.controller;

import io.github.tawdi.smartshop.common.domain.entity.id.UuidEntity;
import io.github.tawdi.smartshop.common.mapper.BaseMapper;
import io.github.tawdi.smartshop.common.service.UuidCrudService;

import java.util.UUID;

public abstract class UuidBaseController<T extends UuidEntity, R1, R2> extends AbstractBaseController<T, UUID, R1, R2> {

    protected UuidBaseController(UuidCrudService<T, R1, R2> service, BaseMapper<T, R1, R2> mapper) {
        super(service, mapper);
    }
}