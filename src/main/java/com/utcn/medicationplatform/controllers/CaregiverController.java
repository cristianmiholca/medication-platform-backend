package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.Caregiver;
import com.utcn.medicationplatform.entities.User;
import com.utcn.medicationplatform.services.CaregiverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/caregivers")
@Slf4j
public class CaregiverController {

    //TODO rename all enddpoints

    private final CaregiverService caregiverService;

    @Autowired
    public CaregiverController(CaregiverService caregiverService) {
        this.caregiverService = caregiverService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody Caregiver caregiver) {
        log.info("POST request for saving caregiver with id: {}", caregiver.getId());
        UUID id = caregiverService.save(caregiver);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caregiver> getByID(@PathVariable UUID id){
        log.info("GET request for caregiver with id: {}", id);
        Optional<Caregiver> resultDB = caregiverService.findById(id);
        if (resultDB.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultDB.get(), HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<Caregiver> getByUsername(@PathVariable String username){
        log.info("GET request for caregiver with username: {}", username);
        Optional<Caregiver> resultDB = caregiverService.findByUsername(username);
        if (resultDB.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultDB.get(), HttpStatus.OK);
    }

    @GetMapping("/getALl")
    public ResponseEntity<List<Caregiver>> getAll(){
        log.info("GET request for all caregivers");
        List<Caregiver> caregivers = caregiverService.findAll();
        return new ResponseEntity<>(caregivers, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<HttpStatus> updateByUsername(@PathVariable String username, @RequestBody Caregiver caregiver) {
        log.info("PUT request for caregiver with username: {}", username);
        Optional<Caregiver> resultDB = caregiverService.findByUsername(username);
        if(resultDB.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Caregiver caregiverDB = resultDB.get();
        caregiver.setId(caregiverDB.getId());
        caregiverService.save(caregiver);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String username){
        log.info("DELETE request for caregiver with username: {}", username);
        caregiverService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
