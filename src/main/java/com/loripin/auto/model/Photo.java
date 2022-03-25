package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "photosObj")
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
