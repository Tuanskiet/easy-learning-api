package com.poly.EasyLearning.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lesson")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active = true;

    @Column(length = 1024)
    private String  title;

    @Column(columnDefinition = "TEXT")
    private String  description;

    @Column(length = 1024)
    private String  image;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "lesson")
    private List<Quiz> quizs = new ArrayList<>();


    @JsonIgnore
    @JsonProperty("userInfo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userInfoId", referencedColumnName = "id")
    private UserInfo userInfo;

}
