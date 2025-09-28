package com.ticketresolve.ticketresolve.service.implementation;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ticketresolve.ticketresolve.persistence.entity.Usuario;
import com.ticketresolve.ticketresolve.persistence.repository.IUsuarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Usar directamente el nombre del rol tal como está en la BD (ADMINISTRADOR, EMPLEADO)
        Collection<? extends GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // SIN prefijo ROLE_
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities
        );
    }
}