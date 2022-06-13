package com.asaproject.asalife.domains.models.requests;

import com.asaproject.asalife.domains.ERoleRegister;
import com.asaproject.asalife.utils.validators.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDetailUser {
    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "NRP is mandatory")
    private String nrp;

    @NotEmpty(message = "department is mandatory")
    private String department;

    @NotNull(message = "Role is mandatory")
    @ValidEnum(enumClass = ERoleRegister.class, groups = ERoleRegister.class, message = "Role is not available")
    private ERoleRegister role;
}
