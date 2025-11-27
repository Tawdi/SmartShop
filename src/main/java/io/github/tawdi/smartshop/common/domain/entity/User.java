package io.github.tawdi.smartshop.common.domain.entity;

import io.github.tawdi.smartshop.common.enums.UserRole;
import io.github.tawdi.smartshop.common.domain.entity.id.StringEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends StringEntity {

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank
    private String username;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserRole role;
}
