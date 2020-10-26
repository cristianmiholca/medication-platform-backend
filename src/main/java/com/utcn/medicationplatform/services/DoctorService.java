package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.Doctor;
import com.utcn.medicationplatform.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public UUID save(Doctor doctor) {
        return doctorRepository.save(doctor).getId();
    }

    public Optional<Doctor> findByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }

    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

    public void deleteByUsername(String username){
        doctorRepository.deleteByUsername(username);
    }

    public void deleteAll(){
        doctorRepository.deleteAll();
    }

}
