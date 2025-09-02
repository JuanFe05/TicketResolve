package com.ticketresolve.ticketresolve.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.ticketresolve.ticketresolve.model.ERole;
import com.ticketresolve.ticketresolve.model.Role;
import com.ticketresolve.ticketresolve.model.Usuario;
import com.ticketresolve.ticketresolve.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
public class UsuarioController {

     @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "Login de usuario",
               description = "Autentica al usuario y devuelve un JWT si las credenciales son correctas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso, retorna el token JWT"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        // aquí invocas el AuthenticationManager de Spring Security
        // generas el JWT
        Map<String, String> response = new HashMap<>();
        response.put("token", "aqui_va_el_jwt");
        return ResponseEntity.ok(response);
    }


     @GetMapping("/hello")
     public String hello() {
         return new String();
     }
     
     @GetMapping("/helloSecured")
     public String helloSecured() {
         return new String();
     }

     @PostMapping("/createUsuario")
     public ResponseEntity<?> createUsuario(@Valid @RequestBody CreateUsuarioDTO createUsuarioDTO){
        
        Set<Role> roles = createUsuarioDTO.getRoles().stream()
                .map(role -> Role.builder()
                        .name(ERole.valueOf(role))
                        .build())
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

     @DeleteMapping("/deleteUsuario")
     public String deleteUsuario(@RequestParam String id){
        usuarioRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el Usuario con id".concat(id);
     }

}
