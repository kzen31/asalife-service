package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateringDto {
    private long id;
    private String userName;
    private String userNrp;
    private String lokasi;
    private String deskripsi;
    private String kritik_saran;
    private String status;
    private LocalDateTime tanggalAduan;
}
