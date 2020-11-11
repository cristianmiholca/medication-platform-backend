package com.utcn.medicationplatform.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class CaregiverDTO {

    private UUID id;

    private String name;

    private String gender;

    private String address;

    private Date birthDate;

}
