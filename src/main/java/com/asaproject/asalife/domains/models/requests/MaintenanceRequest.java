package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {
    @NotEmpty(message = "jenis Aduan Not null")
    private String jenisaduan;

    @NotEmpty(message = "lokasi Not null")
    private String lokasi;
}
