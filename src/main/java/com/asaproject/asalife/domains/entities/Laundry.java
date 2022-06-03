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
import java.util.Date;

@Table(name = "laundry")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class Laundry extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String mess;

    @Column(nullable = false)
    private String no_kamar;

    @Column(nullable = false)
    private String jenis_pakaian;

    @Column(nullable = false)
    private String jenis_deviasi;

    @Column
    private Date tanggal_laundry;

    @Column(nullable = false)
    private String status = "SEARCHING";
}
