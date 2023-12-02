package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.ResultRequest;
import com.poly.EasyLearning.entity.Result;
import com.poly.EasyLearning.entity.Room;
import com.poly.EasyLearning.exception.ResultException;
import com.poly.EasyLearning.exception.RoomException;
import com.poly.EasyLearning.repository.ResultRepository;
import com.poly.EasyLearning.service.ResultService;
import com.poly.EasyLearning.service.RoomService;
import com.poly.EasyLearning.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final RoomService roomService;

    @Override
    public List<Result> getAllActiveTrue() {
        return resultRepository.findByActiveTrue();
    }

    @Override
    public Result create(ResultRequest resultRequest) {
        Result result = new Result();
        result.setRoom(roomService.findById(resultRequest.getRoomId()));
        return resultRepository.save(result);
    }

    @Override
    public Result findById(Integer id) {
        Optional<Result> checkResult = resultRepository.findById(id);
        if (checkResult.isEmpty()) {
            throw new ResultException(MessageUtils.Result.NOT_FOUND.getValue());
        }
        return checkResult.get();
    }
}
