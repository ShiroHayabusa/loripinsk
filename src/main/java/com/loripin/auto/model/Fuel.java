package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "fuels")
@Data
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
