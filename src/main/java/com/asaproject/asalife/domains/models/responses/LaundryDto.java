package com.asaproject.asalife.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaundryDto {
    private long id;
    private String userNrp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mess;
    private String no_kamar;
    private String jenis_pakaian;
    private String jenis_deviasi;
    private Date tanggal_laundry;
    private String status;
}
