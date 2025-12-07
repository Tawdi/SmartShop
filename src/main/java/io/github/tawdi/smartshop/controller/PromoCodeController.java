package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.auth.annotation.RequireRole;
import io.github.tawdi.smartshop.auth.annotation.Role;
import io.github.tawdi.smartshop.domain.entity.PromoCode;
import io.github.tawdi.smartshop.dto.order.PromoCodeRequestDTO;
import io.github.tawdi.smartshop.dto.order.PromoCodeResponseDTO;
import io.github.tawdi.smartshop.mapper.PromoCodeMapper;
import io.github.tawdi.smartshop.service.PromoCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/promo-codes")
@Tag(name = "Codes Promo", description = "Gestion des codes promotionnels (ADMIN uniquement)")
@RequireRole(Role.ADMIN)
public class PromoCodeController extends AbstractBaseController<PromoCode,String, PromoCodeRequestDTO, PromoCodeResponseDTO> {


    public PromoCodeController(PromoCodeService service, PromoCodeMapper mapper) {
        super(service, mapper);
    }
}
