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
@Table(name = "[user_info]")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private ImageResponse avatar;

    private String subscription = "FREE";
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountApp account;

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
}