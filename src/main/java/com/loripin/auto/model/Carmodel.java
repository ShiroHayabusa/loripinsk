package com.loripin.auto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carmodels")
@Data
public class Carmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @Length(max = 10240)
    private String description;

    private Boolean uniq;
    private String years;
    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @ManyToOne
    @JoinColumn(name = "gearBox_id")
    private GearBox gearBox;
    @ManyToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;

}

