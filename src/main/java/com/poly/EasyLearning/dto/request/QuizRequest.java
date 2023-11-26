package com.poly.EasyLearning.dto.request;

import com.poly.EasyLearning.entity.QuizItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {
    private String title;
    private List<QuizItem> quizItems;
}
