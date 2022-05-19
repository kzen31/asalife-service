package com.asaproject.asalife.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "ratingcateringmany")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingCateringMany extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int nilai1;

    @Column(nullable = false)
    private int nilai2;

    @Column(nullable = false)
    private int nilai3;

    @Column(nullable = false)
    private int nilai4;

    @Column(nullable = false)
    private int nilai5;

    @Column(nullable = false)
    private int nilai6;

    @Column(nullable = false)
    private int nilai7;

    @Column(nullable = false)
    private int nilai8;

    @Column(nullable = false)
    private String saran;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
