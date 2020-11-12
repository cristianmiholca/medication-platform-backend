package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.Role;
import com.utcn.medicationplatform.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roles")
@Slf4j
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> create(@RequestBody Role role) {
        log.info("POST request for saving role: {}", role);
        Integer id = roleService.save(role);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

}
