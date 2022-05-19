package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingManyRequest {
    @NotNull(message = "Nilai1 Bobot is mandatory")
    private int nilai1;

    @NotNull(message = "Nilai2 Bobot is mandatory")
    private int nilai2;

    @NotNull(message = "Nilai3 Bobot is mandatory")
    private int nilai3;

    @NotNull(message = "Nilai4 Bobot is mandatory")
    private int nilai4;

    @NotNull(message = "Nilai5 Bobot is mandatory")
    private int nilai5;

    @NotNull(message = "Nilai6 Bobot is mandatory")
    private int nilai6;

    @NotNull(message = "Nilai7 Bobot is mandatory")
    private int nilai7;

    @NotNull(message = "Nilai8 Bobot is mandatory")
    private int nilai8;

    @NotNull(message = "saran Bobot is mandatory")
    private String saran;
}
