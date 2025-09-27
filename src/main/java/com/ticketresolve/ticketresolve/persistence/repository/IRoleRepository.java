package com.ticketresolve.ticketresolve.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ticketresolve.ticketresolve.persistence.entity.Role;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Long>{

}
