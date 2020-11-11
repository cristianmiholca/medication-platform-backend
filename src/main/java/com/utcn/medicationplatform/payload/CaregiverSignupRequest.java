package com.utcn.medicationplatform.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class CaregiverSignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String birthDate;

    @NotBlank
    private String gender;

    @NotBlank
    private String address;

}
