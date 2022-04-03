package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaundryRequest {
    private String mess;

    @NotEmpty(message = "no_kamar is mandatory")
    private String no_kamar;

    @NotEmpty(message = "jenis_pakaian is mandatory")
    private String jenis_pakaian;

    @NotEmpty(message = "jenis_deviasi is mandatory")
    private String jenis_deviasi;

    @NotNull(message = "tanggal_loundry is mandatory")
    private Date tanggal_loundry;
}
