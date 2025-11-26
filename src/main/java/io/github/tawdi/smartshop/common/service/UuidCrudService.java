package io.github.tawdi.smartshop.common.service;

import io.github.tawdi.smartshop.common.domain.entity.id.UuidEntity;

import java.util.UUID;

public interface UuidCrudService<T extends UuidEntity, R1, R2> extends BaseCrudService<T, R1, R2, UUID> {
}
