package com.loripin.auto.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gearBoxes")
@Data
public class GearBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String description;
    @ElementCollection
    @CollectionTable(name = "photos2", joinColumns = @JoinColumn(name = "gearBox_id"))
    private List<Photo> photos;
}
