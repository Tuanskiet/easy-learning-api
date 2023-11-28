package com.poly.EasyLearning.dto.request;

import com.poly.EasyLearning.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequest {
    private String  title;
    private String  description;
    private String  image;
    private List<Question> questions = new ArrayList<>();
}
