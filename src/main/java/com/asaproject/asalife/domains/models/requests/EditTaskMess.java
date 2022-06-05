package com.asaproject.asalife.domains.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditTaskMess {
    @NotNull(message = "ruangtvkacajendelakusen is mandatory")
    private Boolean ruangtvkacajendelakusen;

    @NotNull(message = "ruangtvcermin is mandatory")
    private Boolean ruangtvcermin;

    @NotNull(message = "ruangtvdispenser is mandatory")
    private Boolean ruangtvdispenser;

    @NotNull(message = "ruangtvac is mandatory")
    private Boolean ruangtvac;

    @NotNull(message = "ruangtvfurniture is mandatory")
    private Boolean ruangtvfurniture;

    @NotNull(message = "ruangtvraktv is mandatory")
    private Boolean ruangtvraktv;

    @NotNull(message = "ruangtvtiraikarpet is mandatory")
    private Boolean ruangtvtiraikarpet;

    @NotNull(message = "ruangtvdinding is mandatory")
    private Boolean ruangtvdinding;

    @NotNull(message = "ruangtvlantai is mandatory")
    private Boolean ruangtvlantai;

    @NotNull(message = "koridortempatsampah is mandatory")
    private Boolean koridortempatsampah;

    @NotNull(message = "koridorpintu is mandatory")
    private Boolean koridorpintu;

    @NotNull(message = "koridorlantaisudutlantai is mandatory")
    private Boolean koridorlantaisudutlantai;

    @NotNull(message = "koridorkeset is mandatory")
    private Boolean koridorkeset;

    @NotNull(message = "koridorpantry is mandatory")
    private Boolean koridorpantry;

    @NotNull(message = "koridorwastafelchromefixture is mandatory")
    private Boolean koridorwastafelchromefixture;

    @NotNull(message = "koridorperalatanmakanrakpiring is mandatory")
    private Boolean koridorperalatanmakanrakpiring;

    @NotNull(message = "koridorpintudinding is mandatory")
    private Boolean koridorpintudinding;

    @NotNull(message = "koridorkancajendelakusen is mandatory")
    private Boolean koridorkancajendelakusen;

    @NotNull(message = "toiletpintudinding is mandatory")
    private Boolean toiletpintudinding;

    @NotNull(message = "toilettempatsampah is mandatory")
    private Boolean toilettempatsampah;

    @NotNull(message = "toiletwastafelchromefixture is mandatory")
    private Boolean toiletwastafelchromefixture;

    @NotNull(message = "toileturinoirselangtoiletbowl is mandatory")
    private Boolean toileturinoirselangtoiletbowl;

    @NotNull(message = "toiletshowerareacurtain is mandatory")
    private Boolean toiletshowerareacurtain;

    @NotNull(message = "toiletlantaisudutlantai is mandatory")
    private Boolean toiletlantaisudutlantai;

    @NotNull(message = "toiletteras is mandatory")
    private Boolean toiletteras;
}
