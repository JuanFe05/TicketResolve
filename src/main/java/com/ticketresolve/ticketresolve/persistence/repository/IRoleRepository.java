package com.ticketresolve.ticketresolve.persistence.repository;

import com.ticketresolve.ticketresolve.persistence.entity.ERole;
import com.ticketresolve.ticketresolve.persistence.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}