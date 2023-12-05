package com.poly.EasyLearning.api.admin;

import com.poly.EasyLearning.dto.request.LessonRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Lesson;
import com.poly.EasyLearning.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/admin/m-lessons")
public class ManageLessonsApi {
    private LessonService lessonService;
    @Autowired
    public ManageLessonsApi(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllLesson(@RequestParam(required = false, name = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all lesson.",
                        200,
                        lessonService.findAll(pageable)
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> doCreateLesson(
            @RequestBody LessonRequest lessonRequest,
            @AuthenticationPrincipal AccountApp accountApp){
        if(accountApp != null){
            return ResponseEntity.status(201).body(
                    new ResponseObject(
                            "Create new lesson.",
                            200,
                            lessonService.create(lessonRequest, accountApp)
                    )
            );
        } else {
            throw new BadCredentialsException("Bad credentials!");
        }
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<ResponseObject> findLessonById(@PathVariable(required = false) Integer id){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found lesson",
                        200,
                        lessonService.findById(id)
                )
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLesson(@RequestParam(name = "id") Integer lessonId) {
        lessonService.deleteById(lessonId);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Lesson has been deleted successfully :: " + lessonId,
                        204,
                        null
                )
        );
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLesson(@RequestBody Lesson lessonUpdate) {
        Lesson lessonUpdated = lessonService.updateLesson(lessonUpdate);
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Lesson has been updated successfully.",
                        200,
                        lessonUpdated
                )
        );
    }

    @PatchMapping("/upload-image")
    public ResponseEntity<?> updateAvatar(
            @RequestParam(name = "id") Integer lessonId,
            @RequestParam(name = "image") MultipartFile imageFile
    ){
        if(imageFile != null){
            Lesson lessonUpdated = lessonService.uploadImage(lessonId, imageFile);
            return ResponseEntity.status(200).body(
                    new ResponseObject(
                            "Upload lesson image successfully",
                            200,
                            lessonUpdated
                    )
            );
        }else{
            throw new IllegalArgumentException("Image file cannot be null!");
        }
    }

}

