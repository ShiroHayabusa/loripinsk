package com.loripin.auto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "engines")
@Data
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String volume;
    private String power;
    @ManyToOne
    @JoinColumn(name = "engineType_id")
    private EngineType engineType;
    private String torque;
    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Length(max = 10240)
    private String description;
    private String photo;

}
