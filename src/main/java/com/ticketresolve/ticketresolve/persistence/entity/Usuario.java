package com.ticketresolve.ticketresolve.persistence.entity;

import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    
}