package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.dto.request.QuestionRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.service.LessonService;
import com.poly.EasyLearning.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class QuestionApi {
    private QuestionService questionService;
    @Autowired
    public QuestionApi(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/all")
    public ResponseEntity<ResponseObject> getAllQuestion(){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all questions.",
                        200,
                        questionService.getAllActiveTrue()
                )
        );
    }

//    @PostMapping("/question/create")
//    public ResponseEntity<ResponseObject> doCreateQuestion(@RequestBody QuestionRequest questionRequest){
//        return ResponseEntity.status(201).body(
//                new ResponseObject(
//                        "Create new question.",
//                        200,
//                        questionService.create(questionRequest)
//                )
//        );
//    }


}

