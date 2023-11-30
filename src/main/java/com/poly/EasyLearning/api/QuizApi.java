package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.QuizRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<ResponseObject> doCreateQuiz(@RequestBody QuizRequest quizRequest, @AuthenticationPrincipal AccountApp accountApp){
        if(accountApp != null){
            return ResponseEntity.status(201).body(
                    new ResponseObject(
                            "Create new quiz.",
                            200,
                            quizService.create(quizRequest, accountApp)
                    )
            );
        }else{
            throw new BadCredentialsException("Bad credentials!");
        }
    }

    @GetMapping(value = {"/quiz/search"})
    public ResponseEntity<ResponseObject> search(@RequestParam(required = false) String keyword){
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

//    @GetMapping(value = {"/quiz/ofLesson"})
//    public ResponseEntity<ResponseObject> getQuizOfLesson(@RequestParam(required = false) Integer id){
//        return ResponseEntity.status(200).body(
//                new ResponseObject(
//                        "Found quiz",
//                        200,
//                        quizService.findById(id)
//                )
//        );
//    }

}

