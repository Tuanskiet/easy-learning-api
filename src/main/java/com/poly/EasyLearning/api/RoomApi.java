package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.QuizRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.service.QuizService;
import com.poly.EasyLearning.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class RoomApi {
    private final RoomService roomService;



    /*@GetMapping("/room/all")
    public ResponseEntity<ResponseObject> getAllRoom(){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all room.",
                        200,
                        roomService.getAllActiveTrue()
                )
        );
    }

    @PostMapping("/room/create")
    public ResponseEntity<ResponseObject> doCreateRoom(@RequestBody RoomRequest roomRequest){
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new room.",
                        200,
                        roomService.create(roomRequest)
                )
        );
    }

    @GetMapping(value = {"/room/{id}"})
    public ResponseEntity<ResponseObject> getQuiz(@PathVariable(required = false) Integer id){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found room",
                        200,
                        roomService.findById(id)
                )
        );
    }*/

}

