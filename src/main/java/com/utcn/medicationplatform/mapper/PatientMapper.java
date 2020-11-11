package com.utcn.medicationplatform.mapper;

import com.utcn.medicationplatform.dtos.PatientDTO;
import com.utcn.medicationplatform.entities.Patient;
import com.utcn.medicationplatform.services.CaregiverService;
import com.utcn.medicationplatform.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper implements Mapper<Patient, PatientDTO> {

    @Autowired
    private PatientService patientService;

    @Autowired
    private CaregiverService caregiverService;

    @Override
    public Patient dtoToEntity(PatientDTO patientDTO) {
        Patient patientFromDB = patientService.findById(patientDTO.getId())
                .orElseThrow(() -> new RuntimeException("Error: Cannot retrieve object from DB!"));

        Patient patient = Patient.builder()
                .name(patientDTO.getName())
                .gender(patientDTO.getGender())
                .address(patientDTO.getAddress())
                .caregiver(caregiverService
                        .findById(patientDTO.getCaregiverId())
                        .orElseThrow(IllegalAccessError::new))
                .build();
        patient.setUsername(patientFromDB.getUsername());
        patient.setPassword(patientFromDB.getPassword());
        patient.setBirthDate(patientFromDB.getBirthDate());
        patient.setRoles(patientFromDB.getRoles());

        return patient;
    }

    @Override
    public PatientDTO entityToDto(Patient patient) {
        PatientDTO patientDTO = PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .birthDate(patient.getBirthDate())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .build();
        if (patient.getCaregiver() != null) {
            patientDTO.setCaregiverId(patient.getCaregiver().getId());
        }

        return patientDTO;
    }

}
