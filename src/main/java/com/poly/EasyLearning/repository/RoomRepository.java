package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}