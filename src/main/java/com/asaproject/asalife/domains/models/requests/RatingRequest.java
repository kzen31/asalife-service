package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    @NotEmpty(message = "pertanyaan_id is mandatory")
    private Long pertanyaan_id;

    @NotEmpty(message = "Nilai Bobot is mandatory")
    private Integer nilai;
}
