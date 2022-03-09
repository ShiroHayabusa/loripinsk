package com.loripin.auto.model;

import com.loripin.auto.model.util.SpotHelper;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "spots")
@Data
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String city;
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "carmodel_id")
    private Carmodel carmodel;

    @ManyToOne
    @JoinColumn(name = "modification_id")
    private Modification modification;

    @OneToMany(mappedBy = "spot")
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "spot")
    @ToString.Exclude
    private List<Reply> replies;

    private String photo1;
    private String photo2;
    private String photo3;

    @ManyToMany
    @JoinTable(name = "spot_likes",
               joinColumns = {@JoinColumn(name = "spot_id")},
               inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    public String getUserName(){
        return SpotHelper.getUserName(user);
    }
}
