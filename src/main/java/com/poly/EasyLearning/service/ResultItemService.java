package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.ResultItemRequest;
import com.poly.EasyLearning.entity.ResultItem;

public interface ResultItemService {

    ResultItem create(ResultItemRequest resultItemRequest);
}
