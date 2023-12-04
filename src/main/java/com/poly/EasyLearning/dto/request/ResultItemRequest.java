package com.poly.EasyLearning.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultItemRequest {
    private String userAns;
    private Integer quizItemId;
    private Integer resultId;
}
