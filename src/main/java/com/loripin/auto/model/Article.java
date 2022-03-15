package com.loripin.auto.model;

import lombok.Data;
import lombok.ToString;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "articles")

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String heading;
    private String text;
    @ElementCollection
    @CollectionTable(name = "article_tags")
    private List<String> tags = new ArrayList<String>();
    private String date;
    @OneToMany(mappedBy = "article")
    @ToString.Exclude
    private List<Comment> comments;
    @OneToMany(mappedBy = "article")
    @ToString.Exclude
    private List<Reply> replies;
    @ManyToOne
    @JoinColumn(name = "modification_id")
    private Modification modification;
    private String status;

}
