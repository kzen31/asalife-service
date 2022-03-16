package com.asaproject.asalife.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "pertanyaan")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class Pertanyaan extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pertanyaan")
    private List<Bobot> bobots;

    @Column(nullable = false)
    private String isi;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pertanyaan")
    private List<RatingCatering> ratingCaterings;
}
