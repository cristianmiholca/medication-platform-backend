package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.Doctor;
import com.utcn.medicationplatform.services.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/doctors")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<UUID> create(@RequestBody Doctor doctor){
        log.info("POST request for saving doctor with id: {}", doctor.getId());
        UUID id = doctorService.save(doctor);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<Doctor> getById(@PathVariable UUID id){
        log.info("GET request for doctor with id: {}", id);
        Optional<Doctor> resultDB = doctorService.findById(id);
        return resultDB.map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getByUsername/{username}")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<Doctor> getByUsername(@PathVariable String username){
        log.info("GET request for doctor with username: {}", username);
        Optional<Doctor> resultDB = doctorService.findByUsername(username);
        return resultDB.map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<Doctor>> getAll(){
        log.info("GET request for all doctors");
        List<Doctor> doctors = doctorService.findAll();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> updateByUsername(@PathVariable String username, @RequestBody Doctor doctor) {
        log.info("PUT request for doctor with username: {}", username);
        Optional<Doctor> resultDB = doctorService.findByUsername(username);
        if(resultDB.isPresent()){
            Doctor doctorDB = resultDB.get();
            doctor.setId(doctorDB.getId());
            doctorService.save(doctor);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> delete(@PathVariable String username) {
        log.info("DELETE request for doctor with username: {}", username);
        doctorService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
