package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByActiveTrue();

}