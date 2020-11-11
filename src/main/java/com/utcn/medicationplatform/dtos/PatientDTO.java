package com.utcn.medicationplatform.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PatientDTO {

    private UUID id;

    private String name;

    private String gender;

    private String address;

    private Date birthDate;

    @JsonProperty("caregiver_id")
    private UUID caregiverId;

}
