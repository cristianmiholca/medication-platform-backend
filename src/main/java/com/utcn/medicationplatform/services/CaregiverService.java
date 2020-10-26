package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.Caregiver;
import com.utcn.medicationplatform.repositories.CaregiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;

    @Autowired
    public CaregiverService(CaregiverRepository caregiverRepository) {
        this.caregiverRepository = caregiverRepository;
    }

    public UUID save(Caregiver caregiver){
        return caregiverRepository.save(caregiver).getId();
    }

    public Optional<Caregiver> findById(UUID id){
        return caregiverRepository.findById(id);
    }

    public Optional<Caregiver> findByUsername(String username){
        return caregiverRepository.findByUsername(username);
    }

    public List<Caregiver> findAll(){
        return caregiverRepository.findAll();
    }

    public void delete(Caregiver caregiver){
        caregiverRepository.delete(caregiver);
    }

    public void deleteById(UUID id){
        caregiverRepository.deleteById(id);
    }

    public void deleteByUsername(String username){
        caregiverRepository.deleteByUsername(username);
    }

    public void deleteAll(){
        caregiverRepository.deleteAll();
    }

}
