package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "bodyTypeNames")
@Data
public class BodyTypeName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
