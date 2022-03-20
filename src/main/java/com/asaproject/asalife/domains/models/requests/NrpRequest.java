package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NrpRequest {
    @NotEmpty(message = "NRP is mandatory")
    private String nrp;
}
