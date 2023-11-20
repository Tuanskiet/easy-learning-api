package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.dto.request.UserRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.service.AccountService;
import com.poly.EasyLearning.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LessonApi {
    private final LessonService lessonService;

    @GetMapping("/lesson/all")
    public ResponseEntity<ResponseObject> getAllLesson(){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all lesson.",
                        200,
                        lessonService.getAllActiveTrue()
                )
        );
    }

    @PostMapping("/lesson/create")
    public ResponseEntity<ResponseObject> doCreateLesson(@RequestBody LessonRequest lessonRequest){
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new lesson.",
                        200,
                        lessonService.create(lessonRequest)
                )
        );
    }

}

