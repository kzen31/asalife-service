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

@Table(name = "taskroom")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class TaskRoom extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private Boolean lantaiKamar;

    @Column(nullable = false)
    private Boolean lantaiToilet;

    @Column(nullable = false)
    private Boolean lantaiLangitKamar;

    @Column(nullable = false)
    private Boolean lantaiLangitKamarMandi;

    @Column(nullable = false)
    private Boolean wc;

    @Column(nullable = false)
    private Boolean wastafel;

    @Column(nullable = false)
    private Boolean tempatTidur;

    @Column(nullable = false)
    private Boolean sprei;

    @Column(nullable = false)
    private Boolean selimut;

    @Column(nullable = false)
    private Boolean ac;

    @Column(nullable = false)
    private Boolean meja;

    @Column(nullable = false)
    private Boolean cermin;

    @Column(nullable = false)
    private Boolean keran;

    @Column(nullable = false)
    private Boolean shower;

    @Column(nullable = false)
    private Boolean tempatSampah;

    @Column(nullable = false)
    private Boolean jendela;

    @Column(nullable = false)
    private Boolean gorden;

    @Column(nullable = false)
    private Boolean lemari;

    @Column(nullable = false)
    private String mess;

    @Column(nullable = false)
    private String noKamar;
}
