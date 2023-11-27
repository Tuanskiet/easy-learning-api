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
@Table(name = "lesson")
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active = false;

    @Column(length = 1024)
    private String  title;

    @Column(columnDefinition = "TEXT")
    private String  description;

    @Column(length = 1024)
    private String  image;

    @OneToMany(mappedBy = "lesson")
    private List<Question> questions = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userInfoId", referencedColumnName = "id")
    private UserInfo userInfo;

    // Should add account id attribute
}
