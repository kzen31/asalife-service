package com.asaproject.asalife.domains.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingCateringDto {
    private LocalDateTime createdAt;
    private Long id_pertanyaan;
    private String isi_pertanyaan;
    private long id;
    private int nilai;
}
