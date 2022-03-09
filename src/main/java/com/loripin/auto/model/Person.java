package com.loripin.auto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private String dateOfBirth;
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
    @Length(max = 10240)
    private String description;
    private String photo;
    private Integer points2021;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    private Boolean navigator;
}
