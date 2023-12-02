package com.poly.EasyLearning.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.entity.Question;
import com.poly.EasyLearning.service.LessonService;
import com.poly.EasyLearning.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class QuestionApi {
    private final QuestionService questionService;
    private final LessonService lessonService;

    @GetMapping("/question/all")
    public ResponseEntity<ResponseObject> getAllQuestion() {
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all questions.",
                        200,
                        questionService.getAllActiveTrue()
                )
        );
    }

    @PostMapping("/question/create")
    public ResponseEntity<ResponseObject> createQuestion( String questions, Integer idLesson) {
        System.out.println("Lesson: " + idLesson);
        System.out.println("Lesson: " + questions);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Question> questionList = new ArrayList<>();
        Lesson lesson = this.lessonService.findById(idLesson);

        try {
            // Chuyển đổi từ JSON sang List
            questionList = objectMapper.readValue(questions, new TypeReference<List<Question>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi khi chuyển đổi
        }


        if (lesson != null) {
            for (Question question : questionList) {
                question.setLesson(lesson);
                this.questionService.createQuestion(question);
            }
        }

        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all questions.",
                        200,
                        questionList
                )
        );
    }

    @PutMapping("/question/update")
    public ResponseEntity<ResponseObject> updateQuestion(@RequestBody Question question) {
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Update questions success",
                        201,
                        questionService.updateQuestion(question)
                )
        );
    }

    @DeleteMapping("/question/delete/{id}")
    public ResponseEntity<ResponseObject> updateQuestion(@PathVariable Integer id) {
        this.questionService.delete(id);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Delete questions success",
                        201,
                        null

                )
        );
    }

//    @PostMapping("/lesson/create")
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

