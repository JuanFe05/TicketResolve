package com.ticketresolve.ticketresolve.configuration.swagger;

import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "SISTEMA DE TICKETS",
                description = "Sistema de soporte para la gestión de tickets",
                termsOfService = "https://www.grupoQodez.com/terminos-y-condiciones",
                version = "1.0.0",
                contact = @Contact(
                        name = "Equipo de Desarrollo - Grupo Qodez",
                        url = "https://www.grupoQodez.com/contact",
                        email = "soporte@grupoQodez.com"
                ),
                license = @License(
                        name = "Licencia MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
                servers = {@Server(
                        description = "Servidor de Desarrollo",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de Producción",
                        url = "https://api.grupoqodez.com"
                )
                 },
                 security = @SecurityRequirement(
                        name = "Security Token"
                 )
)
@SecurityScheme(
        name = "Security Token",
        description = "Access Token For Our API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)

public class SwaggerConfig {
}