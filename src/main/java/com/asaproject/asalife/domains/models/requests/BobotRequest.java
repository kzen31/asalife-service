package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BobotRequest {
    @NotEmpty(message = "Nilai is mandatory")
    private Integer nilai;

    @NotEmpty(message = "Pilihan is mandatory")
    private String pilihan;

    @NotEmpty(message = "id_pertanyaan is mandatory")
    private Long id_pertanyaan;
}
