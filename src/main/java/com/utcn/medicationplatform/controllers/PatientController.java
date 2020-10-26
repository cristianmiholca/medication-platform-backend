package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.Patient;
import com.utcn.medicationplatform.services.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/patient")
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody Patient patient) {
        log.info("POST request for patient with id: {}", patient.getId());
        UUID id = patientService.save(patient);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Patient> getById(@PathVariable UUID id) {
        log.info("GET request for patient with id: {}", id);
        Optional<Patient> resultDB = patientService.findById(id);
        if(resultDB.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultDB.get(), HttpStatus.OK);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<Patient> getByUsername(@PathVariable String username) {
        log.info("GET request for patient with username: {}", username);
        Optional<Patient> resultDB = patientService.findByUsername(username);
        if(resultDB.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultDB.get(), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Patient>> getAll(){
        log.info("GET request for all patients");
        List<Patient> patients = patientService.findAll();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateById(@PathVariable UUID id, @RequestBody Patient patient){
        log.info("PUT request for patient with id: {}", id);
        Optional<Patient> resultDB = patientService.findById(id);
        return updatePatient(patient, resultDB);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<HttpStatus> updateByUsername(@PathVariable String username, @RequestBody Patient patient) {
        log.info("PUT request for patient with username: {}", username);
        Optional<Patient> resultDB = patientService.findByUsername(username);
        return updatePatient(patient, resultDB);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String username) {
        log.info("DELETE request for user with username: {}", username);
        patientService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<HttpStatus> updatePatient(@RequestBody Patient patient, Optional<Patient> resultDB) {
        if(resultDB.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Patient patientDB = resultDB.get();
        patient.setId(patientDB.getId());
        patientService.save(patient);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
