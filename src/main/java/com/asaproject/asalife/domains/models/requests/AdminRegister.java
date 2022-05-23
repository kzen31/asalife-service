package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ERoleAdminRegister;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegister {

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "NRP is mandatory")
    private String nrp;

    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotEmpty(message = "department is mandatory")
    private String department;

    @NotNull(message = "Role is mandatory")
    @ValidEnum(enumClass = ERoleAdminRegister.class, groups = ERoleAdminRegister.class, message = "Role is not available")
    private ERoleAdminRegister role;
}
