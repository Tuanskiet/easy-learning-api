package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.ResultItemRequest;
import com.poly.EasyLearning.entity.ResultItem;

import java.util.List;

public interface ResultItemService {

    ResultItem create(ResultItemRequest resultItemRequest);

    List<ResultItem> getAll();
}
