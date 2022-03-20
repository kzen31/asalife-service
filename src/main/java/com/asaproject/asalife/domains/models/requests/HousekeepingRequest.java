package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HousekeepingRequest {
    @NotEmpty(message = "Lokasi is mandatory")
    private String lokasi;

    @NotEmpty(message = "Deskripsi is mandatory")
    private String deskripsi;
}
