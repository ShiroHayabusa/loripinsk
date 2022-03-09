package com.loripin.auto.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "articles")

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Поле 'Заголовок' не может быть пустым!")
    @Length(max = 255, message = "Заголовок слишком длинный")
    private String heading;

    @NotBlank(message = "Поле 'Текст' не может быть пустым!")
    @Length(max = 10240, message = "Текст слишком длинный")
    private String text;

    @ElementCollection
    @CollectionTable(name = "article_tags")
    private List<String> tags = new ArrayList<String>();

    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "modification_id")
    @NotNull(message = "Поле 'Модификация' не может быть пустым!")
    private Modification modification;

    @OneToMany(mappedBy = "article")
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "article")
    @ToString.Exclude
    private List<Reply> replies;


    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }
}
