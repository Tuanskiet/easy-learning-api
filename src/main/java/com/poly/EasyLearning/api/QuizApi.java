package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.QuizRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuizApi {
    private final QuizService quizService;

    @GetMapping("/quiz/all")
    public ResponseEntity<ResponseObject> getAllQuiz(){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all quiz.",
                        200,
                        quizService.getAllActiveTrue()
                )
        );
    }

    @PostMapping("/quiz/create")
    public ResponseEntity<ResponseObject> doCreateLesson(@RequestBody QuizRequest quizRequest){
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new quiz.",
                        200,
                        quizService.create(quizRequest)
                )
        );
    }

    @GetMapping(value = {"/quiz/search/{keyword}", "/quiz/search/"})
    public ResponseEntity<ResponseObject> search(@PathVariable(required = false) String keyword){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found quiz",
                        200,
                        quizService.searchByKeyword(keyword)
                )
        );
    }

    @GetMapping(value = {"/quiz/{id}"})
    public ResponseEntity<ResponseObject> getQuiz(@PathVariable(required = false) Integer id){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found quiz",
                        200,
                        quizService.findById(id)
                )
        );
    }

}

