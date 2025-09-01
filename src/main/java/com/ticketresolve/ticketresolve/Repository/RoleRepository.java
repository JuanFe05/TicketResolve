package com.ticketresolve.ticketresolve.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.accesoSeguroService.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}
