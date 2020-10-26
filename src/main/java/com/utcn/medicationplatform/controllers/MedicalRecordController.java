package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.MedicalRecord;
import com.utcn.medicationplatform.services.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/medical_record")
@Slf4j
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody MedicalRecord medicalRecord){
        log.info("POST request for medical record with id: {}", medicalRecord.getId());
        UUID id = medicalRecordService.save(medicalRecord);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicalRecord>> getAll(){
        log.info("GET request for all medical records");
        List<MedicalRecord> medicalRecords = medicalRecordService.findAll();
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateById(@PathVariable UUID id, @RequestBody MedicalRecord medicalRecord){
        log.info("PUT request for medical record with id: {}", id);
        Optional<MedicalRecord> resultDB = medicalRecordService.findById(id);
        if(resultDB.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MedicalRecord medicalRecordDB = resultDB.get();
        medicalRecord.setId(medicalRecordDB.getId());
        medicalRecordService.save(medicalRecord);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id){
        log.info("DELETE request for medical record with id: {}", id);
        medicalRecordService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
