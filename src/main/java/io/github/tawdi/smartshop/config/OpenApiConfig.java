package io.github.tawdi.smartshop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SmartShop API",
                version = "1.0",
                description = "SmartShop est une application web de gestion commerciale destinée à MicroTech Maroc",
                contact = @Contact(name = "Support", email = "support@smartshop.com")

        )
)
public class OpenApiConfig {
}
