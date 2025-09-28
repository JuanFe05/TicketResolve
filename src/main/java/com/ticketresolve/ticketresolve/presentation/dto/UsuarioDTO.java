package com.ticketresolve.ticketresolve.presentation.dto;

import java.util.Set;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    @Email
    private String email;

    @NotBlank(message = "El username no puede estar vacío")
    private String username;

    @NotBlank(message = "El password no puede estar vacío")
    private String password;

    @NotEmpty(message = "Debe tener al menos un rol asignado")
    private Set<String> roles;
}