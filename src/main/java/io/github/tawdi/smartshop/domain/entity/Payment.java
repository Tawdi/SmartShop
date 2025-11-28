package io.github.tawdi.smartshop.domain.entity;

import io.github.tawdi.smartshop.domain.entity.id.LongEntity;
import io.github.tawdi.smartshop.enums.PaymentStatus;
import io.github.tawdi.smartshop.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends LongEntity {

    @Column(nullable = false)
    private Integer paymentNumber;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    @Min(0)
    @Max(20000)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private PaymentType type;

    @Column(nullable = false)
    @NotNull
    private LocalDate paymentDate;

    @Column
    private LocalDate dueDate;  // Pour CHEQUE/VIREMENT

    @Column(nullable = false, length = 50)
    @NotBlank
    private String reference;

    @Column(length = 100)
    private String bankName;  // Pour CHEQUE/VIREMENT

    @Column
    private LocalDate encaissementDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}