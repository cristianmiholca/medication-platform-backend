package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.MedicationPlan;
import com.utcn.medicationplatform.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicationPlanService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicationPlanService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public UUID save(MedicationPlan medicationPlan){
        return medicalRecordRepository.save(medicationPlan).getId();
    }

    public Optional<MedicationPlan> findById(UUID id){
        return medicalRecordRepository.findById(id);
    }

    public List<MedicationPlan> findAll(){
        return medicalRecordRepository.findAll();
    }

    public void delete(MedicationPlan medicationPlan){
        medicalRecordRepository.delete(medicationPlan);
    }

    public void deleteById(UUID id){
        medicalRecordRepository.deleteById(id);
    }

    public void deleteAll(){
        medicalRecordRepository.deleteAll();
    }

}
