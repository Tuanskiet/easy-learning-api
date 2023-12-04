package com.poly.EasyLearning.api;

import com.poly.EasyLearning.dto.request.ResultItemRequest;
import com.poly.EasyLearning.dto.request.ResultRequest;
import com.poly.EasyLearning.dto.response.ResponseObject;
import com.poly.EasyLearning.service.ResultItemService;
import com.poly.EasyLearning.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ResultItemApi {
    private final ResultItemService resultItemService;

    @GetMapping("/result-item/all")
    public ResponseEntity<ResponseObject> getAllQuiz() {
        return ResponseEntity.status(200).body(
                new ResponseObject(
                        "Get all results.",
                        200,
                        resultItemService.getAll()
                )
        );
    }

    @PostMapping("/result-item/create")
    public ResponseEntity<ResponseObject> doCreateResult(@RequestBody ResultItemRequest resultItemRequest) {
        return ResponseEntity.status(201).body(
                new ResponseObject(
                        "Create new result.",
                        200,
                        resultItemService.create(resultItemRequest)
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

//    @GetMapping(value = {"/result-item/get/{id}"})
//    public ResponseEntity<ResponseObject> getResult(@PathVariable(required = false) Integer id) {
//        return ResponseEntity.status(200).body(
//                new ResponseObject(
//                        "Found result",
//                        200,
//                        resultItemService.findById(id)
//                )
//        );
//    }

}

