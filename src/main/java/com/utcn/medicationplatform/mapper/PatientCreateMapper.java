package com.utcn.medicationplatform.mapper;

import com.utcn.medicationplatform.dtos.PatientCreateDTO;
import com.utcn.medicationplatform.entities.Caregiver;
import com.utcn.medicationplatform.entities.Patient;
import com.utcn.medicationplatform.services.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Component
public class PatientCreateMapper implements Mapper<Patient, PatientCreateDTO>{

    @Autowired
    private CaregiverService caregiverService;

    @Override
    public Patient dtoToEntity(PatientCreateDTO patientCreateDTO) {
        Optional<Caregiver> optionalCaregiver = caregiverService
                .findById(patientCreateDTO.getCaregiverId());

        if(optionalCaregiver.isPresent()){
            Patient patient = Patient.builder()
                    .name(patientCreateDTO.getName())
                    .gender(patientCreateDTO.getGender())
                    .address(patientCreateDTO.getAddress())
                    .birthDate(stringToDate(patientCreateDTO.getBirthDate()))
                    .caregiver(optionalCaregiver.get())
                    .build();
            patient.setUsername(patientCreateDTO.getUsername());
            patient.setPassword(patientCreateDTO.getPassword());

            return patient;
        }

        throw new RuntimeException("Caregiver not found!");
    }

    @Override
    public PatientCreateDTO entityToDto(Patient patient) {
        return null;
    }

    private Date stringToDate(String date){
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
