package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordHousekeepingRequest {
    @NotNull
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String ceklis;
}
