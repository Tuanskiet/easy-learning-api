package com.poly.EasyLearning.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequest {
    private String  title;
    private String  description;
    private String  image;
}
