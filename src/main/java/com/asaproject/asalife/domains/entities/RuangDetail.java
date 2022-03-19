package com.asaproject.asalife.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "ruangdetail")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuangDetail extends Auditable implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "ruang_id", referencedColumnName = "id")
        private Ruang ruang;

        @Column(nullable = false)
        private String detail;

        @JsonIgnore
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "ruangDetail")
        private List<RecordHousekeeping> recordHousekeepings;
}
