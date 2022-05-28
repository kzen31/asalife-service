package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaundryDto {
    private long id;
    private String userNrp;
    private String userName;
    private String mess;
    private String no_kamar;
    private String jenis_pakaian;
    private String jenis_deviasi;
    private Date tanggal_laundry;
    private LocalDateTime tanggal_aduan;
    private String status;
}
