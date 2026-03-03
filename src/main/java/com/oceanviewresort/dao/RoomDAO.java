package com.oceanviewresort.dao;

import com.oceanviewresort.model.Room;
import java.util.List;

public interface RoomDAO {

    boolean addRoom(Room room);

    Room getRoomById(int id);

    Room getRoomByNumber(int roomNumber);

    List<Room> getAllRooms();

    boolean updateRoom(Room room);

    boolean deleteRoom(int id);
}
