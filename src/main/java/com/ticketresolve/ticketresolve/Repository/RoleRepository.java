package com.ticketresolve.ticketresolve.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ticketresolve.ticketresolve.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}
