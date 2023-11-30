package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.RoomRequest;
import com.poly.EasyLearning.entity.Room;
import com.poly.EasyLearning.repository.RoomRepository;
import com.poly.EasyLearning.service.QuizService;
import com.poly.EasyLearning.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

//    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final QuizService quizService;

    @Override
    public Room findById(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room create(RoomRequest roomRequest) {
        Room room = new Room();
        room.setQuiz(quizService.findById(roomRequest.getQuizId()));
        return roomRepository.save(room);
    }


}
