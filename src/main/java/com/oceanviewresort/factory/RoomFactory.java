package com.oceanviewresort.factory;

import com.oceanviewresort.model.Room;

public class RoomFactory {

    // Full constructor factory (already exists)
    public static Room createRoom(int roomID,
                                  String roomType,
                                  int roomNumber,
                                  int bedCount,
                                  double pricePerNight,
                                  String paymentStatus,
                                  String description) {

        return new Room(roomID, roomType, roomNumber,
                bedCount, pricePerNight,
                paymentStatus, description);
    }

    public static Room createRoom(String roomType) {

        switch (roomType.toUpperCase()) {
            case "DELUXE":
                return new Room(0, "DELUXE", 0, 2, 15000.0, "UNPAID", "Default deluxe room");

            case "STANDARD":
                return new Room(0, "STANDARD", 0, 1, 8000.0, "UNPAID", "Default standard room");

            default:
                throw new IllegalArgumentException("Invalid room type");
        }
    }
}