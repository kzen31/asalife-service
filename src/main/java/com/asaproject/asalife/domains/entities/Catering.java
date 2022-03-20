package com.asaproject.asalife.domains.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "catering")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Catering extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private String lokasi;

    @Column(nullable = false)
    private String deskripsi;

    private String kritik_saran;

    @Column(nullable = false)
    private String status = "INQUIRY";
}
