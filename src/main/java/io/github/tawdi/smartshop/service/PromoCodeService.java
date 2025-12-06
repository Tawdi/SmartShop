package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.domain.entity.PromoCode;
import io.github.tawdi.smartshop.dto.order.PromoCodeRequestDTO;
import io.github.tawdi.smartshop.dto.order.PromoCodeResponseDTO;

public interface PromoCodeService extends BaseCrudService<PromoCode, PromoCodeRequestDTO, PromoCodeResponseDTO,String> {
}
