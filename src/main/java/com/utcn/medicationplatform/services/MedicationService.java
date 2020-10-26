package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.Medication;
import com.utcn.medicationplatform.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public UUID save(Medication medication) {
        return medicationRepository.save(medication).getId();
    }

    public Optional<Medication> findById(UUID id) {
        return medicationRepository.findById(id);
    }

    public Optional<Medication> findByName(String name){
        return medicationRepository.findByName(name);
    }

    public List<Medication> findAll() {
        return medicationRepository.findAll();
    }

    public void delete(Medication medication) {
        medicationRepository.delete(medication);
    }

    public void deleteById(UUID id) {
        medicationRepository.deleteById(id);
    }

    public void deleteByName(String name){
        medicationRepository.deleteByName(name);
    }

    public void deleteAll() {
        medicationRepository.deleteAll();
    }


}
