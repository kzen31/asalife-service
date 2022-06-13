package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    @NotEmpty(message = "NRP cannot null")
    private String nrp;

    @NotEmpty(message = "new Password Cannot null")
    private String password;
}
