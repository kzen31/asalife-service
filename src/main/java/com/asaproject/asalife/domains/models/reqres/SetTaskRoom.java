package com.asaproject.asalife.domains.models.reqres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetTaskRoom {
    private Boolean lantaiKamar;

    private Boolean lantaiToilet;

    private Boolean lantaiLangitKamar;

    private Boolean lantaiLangitKamarMandi;

    private Boolean wc;

    private Boolean wastafel;

    private Boolean tempatTidur;

    private Boolean sprei;

    private Boolean selimut;

    private Boolean ac;

    private Boolean meja;

    private Boolean cermin;

    private Boolean keran;

    private Boolean shower;

    private Boolean tempatSampah;

    private Boolean jendela;

    private Boolean gorden;

    private Boolean lemari;
}
