package com.utcn.medicationplatform.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class PatientCreateDTO {

    private String username;

    private String password;

    private String birthDate;

    private String name;

    private String gender;

    private String address;

    @JsonProperty("caregiver_id")
    private UUID caregiverId;

}
