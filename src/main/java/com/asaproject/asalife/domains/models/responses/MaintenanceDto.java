package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDto {
    private long id;
    private String userName;
    private String userNrp;
    private String jenisAduan;
    private String lokasi;
    private LocalDateTime tanggalAduan;
    private String priority;
    private Date duration;
    private String status;
    private String picNrp;
}
