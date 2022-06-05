package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditTaskRoom {
    @NotNull(message = "lantaikamar is mandatory")
    private Boolean lantaikamar;

    @NotNull(message = "lantaitoilet is mandatory")
    private Boolean lantaitoilet;

    @NotNull(message = "lantailangitkamar is mandatory")
    private Boolean lantailangitkamar;

    @NotNull(message = "lantailangitkamarmandi is mandatory")
    private Boolean lantailangitkamarmandi;

    @NotNull(message = "wc is mandatory")
    private Boolean wc;

    @NotNull(message = "wastafel is mandatory")
    private Boolean wastafel;

    @NotNull(message = "tempattidur is mandatory")
    private Boolean tempattidur;

    @NotNull(message = "sprei is mandatory")
    private Boolean sprei;

    @NotNull(message = "selimut is mandatory")
    private Boolean selimut;

    @NotNull(message = "ac is mandatory")
    private Boolean ac;

    @NotNull(message = "meja is mandatory")
    private Boolean meja;

    @NotNull(message = "cermin is mandatory")
    private Boolean cermin;

    @NotNull(message = "keran is mandatory")
    private Boolean keran;

    @NotNull(message = "shower is mandatory")
    private Boolean shower;

    @NotNull(message = "tempatsampah is mandatory")
    private Boolean tempatsampah;

    @NotNull(message = "jendela is mandatory")
    private Boolean jendela;

    @NotNull(message = "gorden is mandatory")
    private Boolean gorden;

    @NotNull(message = "lemari is mandatory")
    private Boolean lemari;
}
