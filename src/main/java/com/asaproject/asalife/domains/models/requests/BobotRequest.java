package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BobotRequest {
    @NotNull(message = "Nilai is mandatory")
    private Integer nilai;

    @NotEmpty(message = "Pilihan is mandatory")
    private String pilihan;

    @NotNull(message = "id_pertanyaan is mandatory")
    private Long id_pertanyaan;
}
