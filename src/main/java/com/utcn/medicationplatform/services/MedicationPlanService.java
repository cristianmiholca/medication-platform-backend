package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.MedicationPlan;
import com.utcn.medicationplatform.repositories.MedicationPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicationPlanService {

    private final MedicationPlanRepository medicationPlanRepository;

    @Autowired
    public MedicationPlanService(MedicationPlanRepository medicationPlanRepository) {
        this.medicationPlanRepository = medicationPlanRepository;
    }

    public UUID save(MedicationPlan medicationPlan){
        return medicationPlanRepository.save(medicationPlan).getId();
    }

    public Optional<MedicationPlan> findById(UUID id){
        return medicationPlanRepository.findById(id);
    }

    public List<MedicationPlan> findAll(){
        return medicationPlanRepository.findAll();
    }

    public List<MedicationPlan> findByPatientId(UUID id) {
        return medicationPlanRepository.findByPatientId(id);
    }

    public void delete(MedicationPlan medicationPlan){
        medicationPlanRepository.delete(medicationPlan);
    }

    public void deleteById(UUID id){
        medicationPlanRepository.deleteById(id);
    }

    public void deleteAll(){
        medicationPlanRepository.deleteAll();
    }

}
