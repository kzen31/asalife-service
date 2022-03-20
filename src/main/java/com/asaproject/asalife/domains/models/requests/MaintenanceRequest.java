package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {
    private String jenisaduan;
    private String lokasi;
}
