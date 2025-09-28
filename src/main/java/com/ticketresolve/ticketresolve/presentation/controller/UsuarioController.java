package com.ticketresolve.ticketresolve.presentation.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.ticketresolve.ticketresolve.configuration.security.jwt.JwtUtils;
import com.ticketresolve.ticketresolve.presentation.dto.UsuarioDTO;
import com.ticketresolve.ticketresolve.presentation.dto.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.ticketresolve.ticketresolve.persistence.entity.ERole;
import com.ticketresolve.ticketresolve.persistence.entity.Role;
import com.ticketresolve.ticketresolve.persistence.entity.Usuario;
import com.ticketresolve.ticketresolve.persistence.repository.IUsuarioRepository;
import com.ticketresolve.ticketresolve.persistence.repository.IRoleRepository; // 👈 Nuevo

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/usuarios") // 👈 agrupa endpoints
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRoleRepository roleRepository; // 👈 para no duplicar roles

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    // ---------- LOGIN ----------
    @Operation(summary = "Login de usuario",
            description = "Autentica al usuario y devuelve un JWT si las credenciales son correctas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso, retorna el token JWT"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String token = jwtUtils.generateAccessToken(userDetails.getUsername(), roles);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", userDetails.getUsername());
        response.put("roles", roles);

        return ResponseEntity.ok(response);
    }

    // ---------- CREAR USUARIO ----------
    @PostMapping("/createUsuario")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDTO createUsuarioDTO) {

        Set<Role> roles = createUsuarioDTO.getRoles().stream()
                .map(role -> roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + role)))
                .collect(Collectors.toSet());

        Usuario usuario = Usuario.builder()
                .username(createUsuarioDTO.getUsername())
                .email(createUsuarioDTO.getEmail())
                .password(passwordEncoder.encode(createUsuarioDTO.getPassword()))
                .roles(roles)
                .build();

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    // ---------- ELIMINAR USUARIO ----------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Usuario no encontrado con id " + id);
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado con id " + id);
    }
}