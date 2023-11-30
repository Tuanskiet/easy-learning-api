package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.ResultRequest;
import com.poly.EasyLearning.entity.Result;

import java.util.List;

public interface ResultService {
    List<Result> getAllActiveTrue();

    Result create(ResultRequest resultRequest);
}
