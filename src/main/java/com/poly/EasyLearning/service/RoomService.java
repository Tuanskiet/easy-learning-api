package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.RoomRequest;
import com.poly.EasyLearning.entity.Room;

public interface RoomService {
    Room findById(Integer id);

    Room create(RoomRequest roomRequest);
}
