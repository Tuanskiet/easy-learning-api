package com.poly.EasyLearning.exception;

import com.poly.EasyLearning.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAppHandler {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ResponseObject> handleAccountException(AccountException ex){
        return ResponseEntity.status(500).body(new ResponseObject(
                "Account exception!",
                500,
                ex.getMessage()
        ));
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ResponseObject> handleRoleException(RoleException ex){
        return ResponseEntity.status(500).body(new ResponseObject(
                "Role exception!",
                500,
                ex.getMessage()
        ));
    }
    @ExceptionHandler(LessonException.class)
    public ResponseEntity<ResponseObject> handleLessonException(LessonException ex){
        return ResponseEntity.status(500).body(new ResponseObject(
                "Lesson exception!",
                500,
                ex.getMessage()
        ));
    }

    @ExceptionHandler(QuizException.class)
    public ResponseEntity<ResponseObject> handleLessonException(QuizException ex){
        return ResponseEntity.status(500).body(new ResponseObject(
                "Quiz exception!",
                500,
                ex.getMessage()
        ));
    }

    @ExceptionHandler(RoomException.class)
    public ResponseEntity<ResponseObject> handleLessonException(RoomException ex){
        return ResponseEntity.status(500).body(new ResponseObject(
                "Room exception!",
                500,
                ex.getMessage()
        ));
    }

    @ExceptionHandler(ResultException.class)
    public ResponseEntity<ResponseObject> handleLessonException(ResultException ex){
        return ResponseEntity.status(500).body(new ResponseObject(
                "Result exception!",
                500,
                ex.getMessage()
        ));
    }
}
