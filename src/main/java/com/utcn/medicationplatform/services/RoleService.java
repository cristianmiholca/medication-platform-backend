package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.ERole;
import com.utcn.medicationplatform.entities.Role;
import com.utcn.medicationplatform.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Integer save(Role role) {
        return roleRepository.save(role).getId();
    }

    public Optional<Role> findByName(ERole name){
        return roleRepository.findByName(name);
    }

}
