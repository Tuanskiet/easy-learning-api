package com.poly.EasyLearning.service;

import com.poly.EasyLearning.dto.request.RoomRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Room;

public interface RoomService {
    Room findById(Integer id);
    Room create(RoomRequest roomRequest);
    Room create(RoomRequest roomRequest, AccountApp accountApp);
    Room update(Room room);
}
