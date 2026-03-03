package com.oceanviewresort.factory;

import com.oceanviewresort.model.Room;

public class RoomFactory {

    public static Room createRoom(
            int id,
            String type,
            int number,
            int beds,
            double price,
            String paymentStatus,
            String description
    ) {

        return new Room(
                id,
                type,
                number,
                beds,
                price,
                paymentStatus,
                description
        );
    }
}