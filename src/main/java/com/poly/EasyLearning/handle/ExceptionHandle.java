package com.poly.EasyLearning.handle;

import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.exception.AccountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ResponseObject> handleAccountException(AccountException ex){
        return ResponseEntity.status(500).body(new ResponseObject(
                "Account exception!",
                500,
                ex.getMessage()
        ));
    }
}
