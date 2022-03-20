package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousekeepingDto {
    private LocalDateTime createdAt;
    private long id;
    private String userNrp;
    private String userName;
    private String lokasi;
    private String deskripsi;
    private String status;
}
