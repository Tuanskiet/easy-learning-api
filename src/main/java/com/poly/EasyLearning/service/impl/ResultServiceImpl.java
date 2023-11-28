package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.ResultRequest;
import com.poly.EasyLearning.entity.Result;
import com.poly.EasyLearning.repository.ResultRepository;
import com.poly.EasyLearning.service.ResultService;
import com.poly.EasyLearning.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    @Override
    public List<Result> getAllActiveTrue() {
        return resultRepository.findByActiveTrue();
    }

    @Override
    public Result create(ResultRequest resultRequest) {
        Result result = new Result();
        BeanUtils.copyProperties(resultRequest, result);
        return resultRepository.save(result);
    }
}
