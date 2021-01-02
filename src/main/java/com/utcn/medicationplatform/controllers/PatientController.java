package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.dtos.PatientCreateDTO;
import com.utcn.medicationplatform.dtos.PatientDTO;
import com.utcn.medicationplatform.entities.Patient;
import com.utcn.medicationplatform.mapper.PatientCreateMapper;
import com.utcn.medicationplatform.mapper.PatientMapper;
import com.utcn.medicationplatform.security.services.UserDetailsImpl;
import com.utcn.medicationplatform.services.PatientService;
import com.utcn.medicationplatform.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/patients")
@Slf4j
public class PatientController {

    private final PatientService patientService;

    private final UserService userService;

    private final PatientMapper patientMapper;


    @Autowired
    PatientCreateMapper patientCreateMapper;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    public PatientController(PatientService patientService, UserService userService, PatientMapper patientMapper) {
        this.patientService = patientService;
        this.userService = userService;
        this.patientMapper = patientMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<UUID> create(@RequestBody PatientCreateDTO patientCreateDTO) {
        log.info("POST request for patient: {}", patientCreateDTO);
        UUID id = patientService.save(patientCreateMapper.dtoToEntity(patientCreateDTO));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<Patient> getById(@PathVariable UUID id) {
        log.info("GET request for patient with id: {}", id);
        Optional<Patient> resultDB = patientService.findById(id);
        return resultDB.map(patient -> new ResponseEntity<>(patient, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getByUsername/{username}")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<Patient> getByUsername(@PathVariable String username) {
        log.info("GET request for patient with username: {}", username);
        Optional<Patient> resultDB = patientService.findByUsername(username);
        return resultDB.map(patient -> new ResponseEntity<>(patient, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<PatientDTO>> getAll(){
        log.info("GET request for all patients");
        List<PatientDTO> patients = patientService.findAll()
                .stream()
                .map(patientMapper::entityToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/getByCaregiver/{id}")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<PatientDTO>> getByCaregiver(@PathVariable UUID id) {
        log.info("GET request for patients of caregiver with id: {}", id);
        List<PatientDTO> patients = patientService.findAllByCaregiverId(id)
                .stream()
                .map(patientMapper::entityToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> updateById(@PathVariable UUID id, @RequestBody PatientDTO patientDTO){
        log.info("PUT request for patient: {}", patientDTO);
        Optional<Patient> resultDB = patientService.findById(id);
        return updatePatient(patientMapper.dtoToEntity(patientDTO), resultDB);
    }

    @PutMapping("/updateByUsername/{username}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> updateByUsername(@PathVariable String username, @RequestBody PatientDTO patientDTO) {
        log.info("PUT request for patient: {}", patientDTO);
        Optional<Patient> resultDB = patientService.findByUsername(username);
        return updatePatient(patientMapper.dtoToEntity(patientDTO), resultDB);
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        log.info("DELETE request for user with id: {}", id);
        patientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteByUsername/{username}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> deleteByUsername(@PathVariable String username) {
        log.info("DELETE request for user with username: {}", username);
        patientService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<HttpStatus> updatePatient(@RequestBody Patient patient, Optional<Patient> resultDB) {
        if(resultDB.isPresent()){
            Patient patientDB = resultDB.get();
            patient.setId(patientDB.getId());
            patientService.save(patient);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
