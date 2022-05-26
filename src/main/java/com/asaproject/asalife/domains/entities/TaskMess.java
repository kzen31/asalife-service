package com.asaproject.asalife.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "taskmess")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class TaskMess extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Boolean ruangTvKacaJendelaKusen;

    @Column(nullable = false)
    private Boolean ruangTvCermin;

    @Column(nullable = false)
    private Boolean ruangTvDispenser;

    @Column(nullable = false)
    private Boolean ruangTvAc;

    @Column(nullable = false)
    private Boolean ruangTvFurniture;

    @Column(nullable = false)
    private Boolean ruangTvRakTv;

    @Column(nullable = false)
    private Boolean ruangTvTiraiKarpet;

    @Column(nullable = false)
    private Boolean ruangTvDinding;

    @Column(nullable = false)
    private Boolean ruangTvLantai;

    @Column(nullable = false)
    private Boolean koridorTempatSampah;

    @Column(nullable = false)
    private Boolean koridorPintu;

    @Column(nullable = false)
    private Boolean koridorLantaiSudutLantai;

    @Column(nullable = false)
    private Boolean koridorKeset;

    @Column(nullable = false)
    private Boolean koridorPantry;

    @Column(nullable = false)
    private Boolean koridorWastafelChromeFixture;

    @Column(nullable = false)
    private Boolean koridorPeralatanMakanRakPiring;

    @Column(nullable = false)
    private Boolean koridorPintuDinding;

    @Column(nullable = false)
    private Boolean koridorKancaJendelaKusen;

    @Column(nullable = false)
    private Boolean toiletPintuDinding;

    @Column(nullable = false)
    private Boolean toiletTempatSampah;

    @Column(nullable = false)
    private Boolean toiletWastafelChromeFixture;

    @Column(nullable = false)
    private Boolean toiletUrinoirSelangToiletBowl;

    @Column(nullable = false)
    private Boolean toiletShowerAreaCurtain;

    @Column(nullable = false)
    private Boolean toiletLantaiSudutLantai;

    @Column(nullable = false)
    private Boolean toiletTeras;

    @Column(nullable = false)
    private String mess;
}
