package com.poly.EasyLearning.dto.request;

import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Question;
import com.poly.EasyLearning.entity.QuizItem;
import com.poly.EasyLearning.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {
    private String title;
    private List<Integer> questionIdList;
}
