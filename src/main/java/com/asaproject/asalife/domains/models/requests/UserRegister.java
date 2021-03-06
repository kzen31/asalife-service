package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ERoleUserRegister;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegister {

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "NRP is mandatory")
    private String nrp;

    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotNull(message = "Role is mandatory")
    @ValidEnum(enumClass = ERoleUserRegister.class, groups = ERoleUserRegister.class, message = "Role is not available")
    private ERoleUserRegister role;
}
