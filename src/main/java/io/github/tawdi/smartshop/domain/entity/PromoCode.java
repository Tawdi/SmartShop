package io.github.tawdi.smartshop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name = "promo_code")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PromoCode extends BaseEntity<String>{

    @Id
    @Column(length = 10)
    private String code; // PROMO-A1B2

    private boolean used = false;

    private BigDecimal discountRate = new BigDecimal("0.05");

    @Override
    public String getId() {
        return code;
    }

    @Override
    public void setId(String id) {}
}