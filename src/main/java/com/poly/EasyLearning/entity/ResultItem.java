package com.poly.EasyLearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "result_item")
public class ResultItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userAns;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    private Result result;

    @ManyToOne
    @JoinColumn(name = "quiz_item_id", referencedColumnName = "id")
    private QuizItem quizItem;


}
