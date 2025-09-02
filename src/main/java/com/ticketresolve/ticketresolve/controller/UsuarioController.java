package com.ticketresolve.ticketresolve.controller;

import java.util.Set;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ticketresolve.ticketresolve.model.ERole;
import com.ticketresolve.ticketresolve.model.Role;
import com.ticketresolve.ticketresolve.model.Usuario;
import com.ticketresolve.ticketresolve.repository.UsuarioRepository;


@RestController
public class UsuarioController {

     @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;


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
