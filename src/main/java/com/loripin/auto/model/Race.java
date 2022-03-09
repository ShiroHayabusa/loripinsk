package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "races")
@Data
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer stageNumber;
    private String name;
    private String season;
    private String startDate;
    private String finishDate;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;
}
