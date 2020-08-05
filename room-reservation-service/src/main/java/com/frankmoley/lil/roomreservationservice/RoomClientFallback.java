package com.frankmoley.lil.roomreservationservice;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

@Component
public class RoomClientFallback implements RoomClient {
    @Override
    public List<Room> getAllRooms() {
        return Collections.emptyList();
    }

    @Override
    public Room getRoom(long id) {
        return new Room();
    }
}
