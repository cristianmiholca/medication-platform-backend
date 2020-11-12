package com.utcn.medicationplatform.controllers;

import com.utcn.medicationplatform.entities.MedicationPlan;
import com.utcn.medicationplatform.services.MedicationPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/medication_plan")
@Slf4j
public class MedicationPlanController {

    private final MedicationPlanService medicationPlanService;

    @Autowired
    public MedicationPlanController(MedicationPlanService medicationPlanService) {
        this.medicationPlanService = medicationPlanService;
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody MedicationPlan medicationPlan){
        log.info("POST request for medical record with id: {}", medicationPlan.getId());
        UUID id = medicationPlanService.save(medicationPlan);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicationPlan>> getAll(){
        log.info("GET request for all medical records");
        List<MedicationPlan> medicationPlans = medicationPlanService.findAll();
        return new ResponseEntity<>(medicationPlans, HttpStatus.OK);
    }

    @GetMapping("/getByPatient/{id}")
    public ResponseEntity<List<MedicationPlan>> getByPatient(@PathVariable UUID id){
        log.info("GET request for medication plans for patient with id: {}", id);
        List<MedicationPlan> medicationPlans = medicationPlanService.findByPatientId(id);

        return new ResponseEntity<>(medicationPlans, HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<HttpStatus> updateById(@PathVariable UUID id, @RequestBody MedicationPlan medicationPlan){
        log.info("PUT request for medical record with id: {}", id);
        Optional<MedicationPlan> resultDB = medicationPlanService.findById(id);
        if(resultDB.isPresent()){
            MedicationPlan medicationPlanDB = resultDB.get();
            medicationPlan.setId(medicationPlanDB.getId());
            medicationPlanService.save(medicationPlan);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id){
        log.info("DELETE request for medical record with id: {}", id);
        medicationPlanService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
