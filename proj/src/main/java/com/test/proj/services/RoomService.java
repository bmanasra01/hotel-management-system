package com.test.proj.services;

import com.test.proj.dto.RoomDto;

import java.util.List;
import java.util.Map;

public interface RoomService {
    RoomDto createRoom(RoomDto roomDto);
    List<RoomDto> getAllRooms();
    RoomDto getRoomById(long roomId);
    RoomDto updateRoom(RoomDto roomDto, long roomId);
    RoomDto patchRoom(Map<String, Object> updates, long roomId);
    void deleteRoomById(long roomId);
}
