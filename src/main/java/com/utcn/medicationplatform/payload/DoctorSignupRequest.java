package com.utcn.medicationplatform.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DoctorSignupRequest {

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
    private String address;

}
