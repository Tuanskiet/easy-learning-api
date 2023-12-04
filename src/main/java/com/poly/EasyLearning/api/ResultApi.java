package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.ResultRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ResultApi {
    private final ResultService resultService;

    @GetMapping("/result/all")
    public ResponseEntity<ResponseObject> getAllQuiz() {
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all results.",
                        200,
                        resultService.getAllActiveTrue()
                )
        );
    }

    @PostMapping("/result/create")
    public ResponseEntity<ResponseObject> doCreateResult(@RequestBody ResultRequest resultRequest) {
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new result.",
                        200,
                        resultService.create(resultRequest)
                )
        );
    }

    /*@GetMapping(value = {"/result/search/{keyword}", "/result/search/"})
    public ResponseEntity<ResponseObject> search(@PathVariable(required = false) String keyword){
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found result",
                        200,
                        resultService.searchByKeyword(keyword)
                )
        );
    }*/

    @GetMapping(value = {"/result/get/{id}"})
    public ResponseEntity<ResponseObject> getResult(@PathVariable(required = false) Integer id) {
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found result",
                        200,
                        resultService.findById(id)
                )
        );
    }

    @GetMapping(value = {"/result/get"})
    public ResponseEntity<ResponseObject> getResultByRoomId(@PathVariable(required = false) Integer roomId) {
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Found result",
                        200,
                       resultService.findByRoomId(roomId)

                )
        );
    }
}

