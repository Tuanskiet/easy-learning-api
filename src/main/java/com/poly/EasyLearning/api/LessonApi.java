package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.entity.ImageResponse;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.service.ImageService;
import com.poly.EasyLearning.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class LessonApi {
    private final LessonService lessonService;
    private final ImageService imageService;


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


    @GetMapping(value = {"/lesson/search"})
    public ResponseEntity<ResponseObject> search(@RequestParam(required = false) String keyword){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found lesson",
                        200,
                        lessonService.searchByKeyword(keyword)
                )
        );
    }

    @GetMapping(value = {"/lesson/{id}"})
    public ResponseEntity<ResponseObject> findLessonById(@PathVariable(required = false) int id){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found lesson",
                        200,
                        lessonService.searchById(id)
                )
        );
    }

    @DeleteMapping(value = {"/lesson/delete/{id}"})
    public ResponseEntity<Void> delete(@PathVariable(required = false) int id){
        Lesson lesson = lessonService.searchById(id);
        this.lessonService.delete(id);
        this.imageService.delete(lesson.getImage().getPublicId());
        return ResponseEntity.ok().build();
    }

}

