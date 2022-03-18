package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousekeepingDto {
    private long id;
    private String userNrp;
    private String userName;
    private String lokasi;
    private String deskripsi;
    private String status;
}
