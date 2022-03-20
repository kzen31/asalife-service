package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMaintenanceDto {
    private long id;
    private String userName;
    private String userNrp;
    private String jenisAset;
    private String lokasiAset;
    private String status;
    private String keterangan;
}
