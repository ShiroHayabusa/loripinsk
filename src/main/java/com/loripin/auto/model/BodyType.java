package com.loripin.auto.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "bodyTypes")
@Data
public class BodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "generation_id")
    private Generation generation;
    @ManyToOne
    @JoinColumn(name = "restyle_name")
    private Restyle restyle;
    private String photo;
    private String years;
    @Length(max = 10240)
    private String description;
    @ManyToOne
    @JoinColumn(name = "bodyTypeName_id")
    private BodyTypeName bodyTypeName;
    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Spec spec;
}
