package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.ResultItemRequest;
import com.poly.EasyLearning.entity.ResultItem;
import com.poly.EasyLearning.repository.ResultItemRepository;
import com.poly.EasyLearning.service.QuizItemService;
import com.poly.EasyLearning.service.ResultItemService;
import com.poly.EasyLearning.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResultItemServiceImpl implements ResultItemService {
    private final ResultService resultService;
    private final ResultItemRepository resultItemRepository;
    private final QuizItemService quizItemService;


    @Override
    public ResultItem create(ResultItemRequest resultItemRequest) {
        ResultItem resultItem = new ResultItem();
        BeanUtils.copyProperties(resultItemRequest,resultItem);
        resultItem.setQuizItem(quizItemService.findById(resultItemRequest.getQuestionItemId()));
        resultItem.setResult(resultService.findById(resultItemRequest.getResultId()));
        return null;
    }
}
