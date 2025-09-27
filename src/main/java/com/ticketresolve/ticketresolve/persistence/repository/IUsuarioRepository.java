package com.ticketresolve.ticketresolve.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ticketresolve.ticketresolve.persistence.entity.Usuario;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("select u from Usuario u where u.username = ?1")
    Optional<Usuario> getName(String username);
}