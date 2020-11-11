package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.Patient;
import com.utcn.medicationplatform.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public UUID save(Patient patient){
        return patientRepository.save(patient).getId();
    }

    public Optional<Patient> findById(UUID id){
        return patientRepository.findById(id);
    }

    public Optional<Patient> findByUsername(String username) {
        return patientRepository.findByUsername(username);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    public List<Patient> findAllByCaregiverId(UUID caregiverId) {
        return patientRepository.findPatientsByCaregiverId(caregiverId);
    }

    public void delete(Patient patient){
        patientRepository.delete(patient);
    }

    public void deleteById(UUID id){
        patientRepository.deleteById(id);
    }

    public void deleteByUsername(String username) {
        patientRepository.deleteByUsername(username);
    }

    public void deleteAll(){
        patientRepository.deleteAll();
    }
}
