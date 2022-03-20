package com.asaproject.asalife.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "bobot")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class Bobot extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pertanyaan_id", referencedColumnName = "id")
    private Pertanyaan pertanyaan;

    @Column(nullable = false)
    private String pilihan;

    @Column(nullable = false)
    private Integer nilai;
}
