package com.utcn.medicationplatform.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }
}
