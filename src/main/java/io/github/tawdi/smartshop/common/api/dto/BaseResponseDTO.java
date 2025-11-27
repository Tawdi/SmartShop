package io.github.tawdi.smartshop.common.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@ToString
public abstract class BaseResponseDTO<I> {

    private I id;
    private Instant createdAt;
    private Instant updatedAt;
}
