package com.poly.EasyLearning.repository;

import com.poly.EasyLearning.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByActiveTrue();

    Optional<Room> findByIdAndActiveTrue(Integer id);

}