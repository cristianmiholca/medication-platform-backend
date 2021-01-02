package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.dtos.CaregiverDTO;
import com.utcn.medicationplatform.entities.Caregiver;
import com.utcn.medicationplatform.mapper.CaregiverMapper;
import com.utcn.medicationplatform.services.CaregiverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/caregivers")
@Slf4j
public class CaregiverController {

    private final CaregiverService caregiverService;

    private final CaregiverMapper caregiverMapper;

    @Autowired
    public CaregiverController(CaregiverService caregiverService, CaregiverMapper caregiverMapper) {
        this.caregiverService = caregiverService;
        this.caregiverMapper = caregiverMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<UUID> create(@RequestBody Caregiver caregiver) {
        log.info("POST request for saving caregiver: {}", caregiver);
        UUID id = caregiverService.save(caregiver);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<Caregiver> getByID(@PathVariable UUID id) {
        log.info("GET request for caregiver with id: {}", id);
        Optional<Caregiver> resultDB = caregiverService.findById(id);
        return resultDB.map(caregiver -> new ResponseEntity<>(caregiver, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getByUsername/{username}")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<CaregiverDTO> getByUsername(@PathVariable String username) {
        log.info("GET request for caregiver with username: {}", username);
        Optional<Caregiver> resultDB = caregiverService.findByUsername(username);
        return resultDB.map(caregiver -> new ResponseEntity<>(caregiverMapper.entityToDto(caregiver), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<CaregiverDTO>> getAll() {
        log.info("GET request for all caregivers");
        List<CaregiverDTO> caregivers = caregiverService.findAll()
                .stream()
                .map(caregiverMapper::entityToDto).collect(Collectors.toList());

        return new ResponseEntity<>(caregivers, HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> updateById(@PathVariable UUID id, @RequestBody CaregiverDTO caregiverDTO) {
        log.info("PUT request for caregiver with id: {}", id);
        Optional<Caregiver> resultDB = caregiverService.findById(id);
        if (resultDB.isPresent()) {
            Caregiver caregiver = caregiverMapper.dtoToEntity(caregiverDTO);
            caregiverService.save(caregiver);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateByUsername/{username}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> updateByUsername(@PathVariable String username, @RequestBody CaregiverDTO caregiverDTO) {
        log.info("PUT request for caregiver: {}", caregiverDTO);
        Optional<Caregiver> resultDB = caregiverService.findByUsername(username);
        if (resultDB.isPresent()) {
            Caregiver caregiver = caregiverMapper.dtoToEntity(caregiverDTO);
            Caregiver caregiverDB = resultDB.get();
            caregiver.setId(caregiverDB.getId());
            caregiverService.save(caregiver);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        log.info("DELETE request for caregiver with id: {}", id);
        caregiverService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteByUsername/{username}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<HttpStatus> deleteByUsername(@PathVariable String username) {
        log.info("DELETE request for caregiver with username: {}", username);
        caregiverService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
