package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    @NotNull(message = "pertanyaan_id is mandatory")
    private Long pertanyaan_id;

    @NotNull(message = "Nilai Bobot is mandatory")
    private Integer nilai;
}
