package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.MedicalRecord;
import com.utcn.medicationplatform.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public UUID save(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord).getId();
    }

    public Optional<MedicalRecord> findById(UUID id){
        return medicalRecordRepository.findById(id);
    }

    public List<MedicalRecord> findAll(){
        return medicalRecordRepository.findAll();
    }

    public void delete(MedicalRecord medicalRecord){
        medicalRecordRepository.delete(medicalRecord);
    }

    public void deleteById(UUID id){
        medicalRecordRepository.deleteById(id);
    }

    public void deleteAll(){
        medicalRecordRepository.deleteAll();
    }

}
