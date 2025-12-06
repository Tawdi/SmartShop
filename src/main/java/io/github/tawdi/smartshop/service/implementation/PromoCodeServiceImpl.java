package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.PromoCode;
import io.github.tawdi.smartshop.domain.repository.PromoCodeRepository;
import io.github.tawdi.smartshop.dto.order.PromoCodeRequestDTO;
import io.github.tawdi.smartshop.dto.order.PromoCodeResponseDTO;
import io.github.tawdi.smartshop.exception.BusinessRuleViolationException;
import io.github.tawdi.smartshop.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.mapper.PromoCodeMapper;
import io.github.tawdi.smartshop.service.PromoCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromoCodeServiceImpl extends BaseCrudServiceImpl<PromoCode, PromoCodeRequestDTO, PromoCodeResponseDTO,String>  implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;

    public PromoCodeServiceImpl(PromoCodeRepository repository, PromoCodeMapper mapper) {
        super(repository, mapper);
        this.promoCodeRepository = repository;
        this.promoCodeMapper = mapper;
    }

    @Override
    @Transactional
    public PromoCodeResponseDTO save(PromoCodeRequestDTO dto) {
        String code = dto.code();

        if (promoCodeRepository.existsById(code)) {
            throw new BusinessRuleViolationException(
                    String.format("Le code promo '%s' existe déjà. Utilisez un autre code.", code)
            );
        }
        PromoCode entity = mapper.toEntity(dto);
        entity = promoCodeRepository.save(entity);
        return promoCodeMapper.toDto(entity);
    }

    @Override
    @Transactional
    public PromoCodeResponseDTO update(String code, PromoCodeRequestDTO dto) {
        PromoCode entity = promoCodeRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Code promo non trouvé : " + code));
        if (dto.code()!= null && !code.equals(dto.code()) && repository.existsById(dto.code())) {
            throw new BusinessRuleViolationException(
                    String.format("Le code promo '%s' est déjà utilisé par un autre code.", dto.code())
            );
        }
        promoCodeMapper.updateEntityFromDto(dto, entity);
        entity = promoCodeRepository.save(entity);
        return promoCodeMapper.toDto(entity);
    }
}
