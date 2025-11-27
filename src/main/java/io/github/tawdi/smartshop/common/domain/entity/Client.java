package io.github.tawdi.smartshop.common.domain.entity;

import io.github.tawdi.smartshop.common.enums.CustomerTier;
import io.github.tawdi.smartshop.common.domain.entity.id.StringEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends StringEntity {

    @Column(nullable = false, length = 100)
    @NotBlank
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    @Email
    private String email;

    @Column(length = 20)
    private String telephone;

    @Column(length = 200)
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerTier tier = CustomerTier.BASIC;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
