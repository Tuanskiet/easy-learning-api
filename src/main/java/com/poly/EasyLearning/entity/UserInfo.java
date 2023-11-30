package com.poly.EasyLearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;
    private String email;
    private String avatar  = "";

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountApp account;

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
}