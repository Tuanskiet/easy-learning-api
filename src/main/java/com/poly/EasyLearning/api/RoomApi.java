package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.RoomRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Room;
import com.poly.EasyLearning.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class RoomApi {
    private final RoomService roomService;



//    @GetMapping("/room/all")
//    public ResponseEntity<ResponseObject> getAllRoom(){
//        return ResponseEntity.status(200).body(
//                new ResponseObject(
//                        "Get all room.",
//                        200,
//                        roomService.getAllActiveTrue()
//                )
//        );
//    }

    @PostMapping("/room/create")
    public ResponseEntity<ResponseObject> doCreateRoom(@RequestBody RoomRequest roomRequest, @AuthenticationPrincipal AccountApp accountApp){
        System.out.println(roomRequest.getQuizId());
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new room.",
                        200,
                        roomService.create(roomRequest, accountApp)
                )
        );
    }

    @GetMapping(value = {"/room/get/{id}"})
    public ResponseEntity<ResponseObject> getQuiz(@PathVariable(required = false) Integer id){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found room",
                        200,
                        roomService.findById(id)
                )
        );
    }

    @PostMapping("/room/update")
    public ResponseEntity<?> updateLesson(@RequestBody Room roomUpdate
    ){
        Room roomUpdated = roomService.update(roomUpdate);
        System.out.println(roomUpdate.toString());
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Room has been updated successfully.",
                        200,
                        roomUpdated
                )
        );
    }

}

