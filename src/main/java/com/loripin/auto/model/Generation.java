package com.loripin.auto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "generations")
@Data
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @ManyToOne
    @JoinColumn(name = "carmodel_id")
    private Carmodel carmodel;
    private String years;
    @ManyToOne
    @JoinColumn(name = "body_id")
    private Body body;
    private String photo;
    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;
    @Length(max = 10240)
    private String description;
}
