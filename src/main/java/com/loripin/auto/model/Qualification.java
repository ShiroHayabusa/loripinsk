package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "qualifications")
@Data
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;
    private Integer position;
    @ManyToOne
    @JoinColumn(name = "pilot_id")
    private Person pilot;
    @ManyToOne
    @JoinColumn(name = "modification_id")
    private Modification modification;
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    private String time;
    private String gap;
    private Integer laps;
}
