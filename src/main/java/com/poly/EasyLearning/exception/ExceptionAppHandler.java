package com.poly.EasyLearning.exception;

import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.exception.AccountException;
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
}
