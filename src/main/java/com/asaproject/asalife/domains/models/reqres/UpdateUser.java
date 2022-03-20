package com.asaproject.asalife.domains.models.reqres;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUser {
    @NotEmpty(message = "Name is mandatory")
    private String name;
    @NotEmpty(message = "NRP is mandatory")
    private String nrp;
    private String phoneNumber;
    private String department;
}
