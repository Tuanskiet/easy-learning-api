package com.poly.EasyLearning.service.impl;

import com.poly.EasyLearning.dto.request.RoomRequest;
import com.poly.EasyLearning.entity.AccountApp;
import com.poly.EasyLearning.entity.Room;
import com.poly.EasyLearning.exception.RoomException;
import com.poly.EasyLearning.repository.RoomRepository;
import com.poly.EasyLearning.service.QuizService;
import com.poly.EasyLearning.service.RoomService;
import com.poly.EasyLearning.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final QuizService quizService;

    @Override
    public Room findById(Integer id) {
        Optional<Room> checkRoom = roomRepository.findById(id);
        if (checkRoom.isEmpty()) {
            throw new RoomException(MessageUtils.Room.NOT_FOUND.getValue());
        }
        return checkRoom.get();
    }

    @Override
    public Room create(RoomRequest roomRequest) {
        Room room = new Room();
        room.setQuiz(quizService.findById(roomRequest.getQuizId()));

        return roomRepository.save(room);
    }

    @Override
    public Room create(RoomRequest roomRequest, AccountApp accountApp) {
        Room room = new Room();
        room.setQuiz(quizService.findById(roomRequest.getQuizId()));
        System.out.println("hello" + room.getQuiz());
        room.setUserInfo(accountApp.getUserApp());
        return roomRepository.save(room);
    }

    @Override
    public Room update(Room room) {
        Room currentRoom = roomRepository.findById(room.getId()).get();
        if(currentRoom != null){
            currentRoom.setActive(room.getActive());
        }
        System.out.println(currentRoom.toString());
        return roomRepository.save(currentRoom);
    }
}
