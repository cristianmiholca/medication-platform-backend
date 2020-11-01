package com.utcn.medicationplatform.repositories;

import com.utcn.medicationplatform.entities.Role;
import com.utcn.medicationplatform.entities.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);

}
