package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "raceResults")
@Data
public class RaceResult {
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
    @JoinColumn(name = "navigator_id")
    private Person navigator;
    @ManyToOne
    @JoinColumn(name = "modification_id")
    private Modification modification;
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "raceClass_id")
    private RaceClass raceClass;
    private String time;
    private String gap;
    private String out;
    private Integer points;
    private Integer bonus;
    private String avSpeed;
    private Integer pitStop;

}
