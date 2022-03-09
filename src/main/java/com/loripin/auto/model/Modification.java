package com.loripin.auto.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "modifications")
@Data
public class Modification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Length(max = 10240)
    private String description;

    @ManyToOne
    @JoinColumn(name = "bodyType_id")
    private BodyType bodyType;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "gearBox_id")
    private GearBox gearBox;

    @ManyToOne
    @JoinColumn(name = "body_id")
    private Body body;

    @ManyToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;

    private String years;

    @ElementCollection
    @CollectionTable(name = "photos")
    private List<String> photos;

    @ManyToOne
    @JoinColumn(name = "tuner_id")
    private Tuner tuner;

    private String amount;

    private String maxSpeed;

    private String acceleration;

    private Boolean uniq;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    private String length;

    private String height;

    private String width;

    private String base;

    private String weight;

    private String power;

    private String torque;

    private String engineVolume;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @ManyToOne
    @JoinColumn(name = "engineType_id")
    private EngineType engineType;

    @ManyToOne
    @JoinColumn(name = "gearBoxType_id")
    private GearBoxType gearBoxType;

    private Boolean electric;

    private Boolean hybrid;

    private String battery;

    private String range;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Spec spec;

    @OneToMany(mappedBy = "modification")
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "modification")
    @ToString.Exclude
    private List<Reply> replies;
}
