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
@RequestMapping(value = "/medications")
@Slf4j
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody Medication medication) {
        log.info("POST request for saving medication: {}", medication);
        UUID id = medicationService.save(medication);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Medication> getById(@PathVariable UUID id){
        log.info("GET request for medication with id: {}", id);
        Optional<Medication> resultDB = medicationService.findById(id);
        return resultDB.map(medication -> new ResponseEntity<>(medication, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Medication>> getAll() {
        log.info("GET request for all medications");
        List<Medication> medications = medicationService.findAll();
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<HttpStatus> updateById(@PathVariable UUID id, @RequestBody Medication medication){
        log.info("PUT request for medication with id: {}", id);
        Optional<Medication> resultDB = medicationService.findById(id);
        return updateMedication(medication, resultDB);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id){
        log.info("DELETE request for medication with id: {}", id);
        medicationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<HttpStatus> updateMedication(@RequestBody Medication medication, Optional<Medication> resultDB) {
        if(resultDB.isPresent()){
            Medication medicationDB = resultDB.get();
            medication.setId(medicationDB.getId());
            medicationService.save(medication);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
