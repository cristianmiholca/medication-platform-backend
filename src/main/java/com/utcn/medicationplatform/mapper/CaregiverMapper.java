package com.utcn.medicationplatform.mapper;

import com.utcn.medicationplatform.dtos.CaregiverDTO;
import com.utcn.medicationplatform.entities.Caregiver;
import com.utcn.medicationplatform.services.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CaregiverMapper implements Mapper<Caregiver, CaregiverDTO>{

    @Autowired
    private CaregiverService caregiverService;

    @Override
    public Caregiver dtoToEntity(CaregiverDTO caregiverDTO) {
        Caregiver caregiver = Caregiver.builder()
                .name(caregiverDTO.getName())
                .gender(caregiverDTO.getGender())
                .birthDate(caregiverDTO.getBirthDate())
                .address(caregiverDTO.getAddress())
                .build();
        Caregiver caregiverFromDB = caregiverService.findById(caregiverDTO.getId())
                .orElseThrow(() -> new RuntimeException("Cannot retrieve caregiver from DB!"));
        caregiver.setId(caregiverFromDB.getId());
        caregiver.setUsername(caregiverFromDB.getUsername());
        caregiver.setPassword(caregiverFromDB.getPassword());
        caregiver.setRoles(caregiverFromDB.getRoles());

        return caregiver;
    }

    @Override
    public CaregiverDTO entityToDto(Caregiver caregiver) {
        return CaregiverDTO.builder()
                .id(caregiver.getId())
                .name(caregiver.getName())
                .gender(caregiver.getGender())
                .birthDate(caregiver.getBirthDate())
                .address(caregiver.getAddress())
                .build();
    }
}
