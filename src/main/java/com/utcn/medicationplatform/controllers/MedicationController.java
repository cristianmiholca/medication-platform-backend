package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.Medication;
import com.utcn.medicationplatform.services.MedicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/medication")
@Slf4j
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody Medication medication) {
        log.info("POST request for saving medication with id: {}", medication.getId());
        UUID id = medication.getId();
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Medication> getById(@PathVariable UUID id){
        log.info("GET request for medication with id: {}", id);
        Optional<Medication> resultDB = medicationService.findById(id);
        if(resultDB.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultDB.get(), HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Medication> getByName(@PathVariable String name) {
        log.info("GET request for medication with name: {}", name);
        Optional<Medication> resultDB = medicationService.findByName(name);
        if (resultDB.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultDB.get(), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Medication>> getAll() {
        log.info("GET request for all medications");
        List<Medication> medications = medicationService.findAll();
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateById(@PathVariable UUID id, @RequestBody Medication medication){
        log.info("PUT request for medication with id: {}", id);
        Optional<Medication> resultDB = medicationService.findById(id);
        return updateMedication(medication, resultDB);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<HttpStatus> updateByName(@PathVariable String name, @RequestBody Medication medication) {
        log.info("PUT request for medication: {}", name);
        Optional<Medication> resultDB = medicationService.findByName(name);
        return updateMedication(medication, resultDB);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String name){
        log.info("DELETE request for medication: {}", name);
        medicationService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<HttpStatus> updateMedication(@RequestBody Medication medication, Optional<Medication> resultDB) {
        if(resultDB.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Medication medicationDB = resultDB.get();
        medication.setId(medicationDB.getId());
        medicationService.save(medication);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
