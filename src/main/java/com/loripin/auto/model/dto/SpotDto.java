package com.loripin.auto.model.dto;

import com.loripin.auto.model.*;
import com.loripin.auto.model.util.SpotHelper;
import lombok.Data;

import java.util.List;

@Data
public class SpotDto {
    private Long id;
    private String description;
    private Country country;
    private String city;
    private String date;
    private User user;
    private Manufacturer manufacturer;
    private Carmodel carmodel;
    private Modification modification;
    private List<Comment> comments;
    private List<Reply> replies;
    private String photo1;
    private String photo2;
    private String photo3;
    private Long likes;
    private Boolean meLiked;

    public SpotDto(Spot spot, Long likes, Boolean meLiked) {
        this.id = spot.getId();
        this.description = spot.getDescription();
        this.country = spot.getCountry();
        this.city = spot.getCity();
        this.date = spot.getDate();
        this.user = spot.getUser();
        this.manufacturer = spot.getManufacturer();
        this.carmodel = spot.getCarmodel();
        this.modification = spot.getModification();
        this.comments = spot.getComments();
        this.replies = spot.getReplies();
        this.photo1 = spot.getPhoto1();
        this.photo2 = spot.getPhoto2();
        this.photo3 = spot.getPhoto3();
        this.likes = likes;
        this.meLiked = meLiked;
    }
    public String getUserName(){
        return SpotHelper.getUserName(user);
    }
}
