package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordHousekeepingDto {
    private LocalDateTime createdAt;
    private long id;
    private String ruang;
    private String ruangDetail;
    private Boolean ceklis;
}
