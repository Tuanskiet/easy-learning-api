package com.poly.EasyLearning.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String correctAnswer;
    private Integer lessonId;

}
