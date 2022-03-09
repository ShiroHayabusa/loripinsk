package com.loripin.auto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "teams")
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private String yearOfFoundation;
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
    @Length(max = 10240)
    private String description;
    private String logo;
    private Integer points2021;
}
