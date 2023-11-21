package com.poly.EasyLearning.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lesson")
public class Lesson {
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
}
