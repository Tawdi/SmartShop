package io.github.tawdi.smartshop.client.dto;

import io.github.tawdi.smartshop.common.api.dto.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {

    @NotBlank(groups = ValidationGroups.Create.class, message = "Le nom est obligatoire")
    @Size(groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class} ,max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String name;

    @NotBlank(groups = ValidationGroups.Create.class, message = "L'email est obligatoire")
    @Email(message = "Email invalide",groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
    @Size(max = 100,groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
    private String email;

    @Size(max = 20, message = "Téléphone trop long",groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
    private String telephone;

    @Size(max = 200, message = "Adresse trop longue" , groups = {ValidationGroups.Create.class ,ValidationGroups.Update.class})
    private String adresse;

    @NotBlank(groups = ValidationGroups.Create.class, message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 100,message = "Le mot de passe doit faire au moins 6 caractères",groups = {ValidationGroups.Create.class })
    private String password;

    @NotBlank(groups = ValidationGroups.Create.class, message = "Le username est obligatoire")
    @Size(min = 6, max = 100,message = "Le username doit faire au moins 6 caractères",groups = {ValidationGroups.Create.class})
    private String username;
}