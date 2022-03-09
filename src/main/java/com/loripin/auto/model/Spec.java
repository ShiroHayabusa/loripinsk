package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "specs")
@Data
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
