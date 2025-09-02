package com.ticketresolve.ticketresolve.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesController {
    
    @GetMapping("/acces")
    public String acces(Authentication authentication) {
        // Obtiene el nombre del usuario
        String username = authentication.getName();

        // Obtiene el rol (puede haber más de uno, aquí tomo el primero)
        String role = authentication.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .findFirst()
                                    .orElse("SIN_ROL");

        switch (role) {
            case "ROLE_ADMIN":
                return "Hola " + username + ", has ingresado con el rol de ADMIN";

            case "ROLE_EMPLEADO":
                return "Hola " + username + ", has ingresado con el rol de EMPLEADO";

            case "ROLE_CLIENTE":
                return "Hola " + username + ", has ingresado con el rol de CLIENTE";

            default:
                return "Hola " + username + ", no tienes un rol válido para acceder";
        }
    }
}
