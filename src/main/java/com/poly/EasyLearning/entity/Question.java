package com.poly.EasyLearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1024)
    private String  title;

    private String  answerA;
    private String  answerB;
    private String  answerC;
    private String  correctAnswer;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;
}
