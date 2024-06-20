package com.test.proj.services.implementation;

import com.test.proj.dto.RoomDto;
import com.test.proj.entities.Room;
import com.test.proj.exception.ResourceNotFoundException;
import com.test.proj.repository.RoomRepository;
import com.test.proj.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = mapToEntity(roomDto);
        Room newRoom = roomRepository.save(room);
        return mapToDto(newRoom);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        return mapToDto(room);
    }

    @Override
    public RoomDto updateRoom(RoomDto roomDto, long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        room.setRoom_number(roomDto.getRoom_number());
        room.setType(roomDto.getType());
        room.setPrice(roomDto.getPrice());
        room.setCapacity(roomDto.getCapacity());

        Room updatedRoom = roomRepository.save(room);
        return mapToDto(updatedRoom);
    }

    @Override
    public RoomDto patchRoom(Map<String, Object> updates, long roomId) {
        RoomDto roomDto = getRoomById(roomId);
        updates.forEach((key, value) -> {
            switch (key) {
                case "room_number":
                    roomDto.setRoom_number((Integer) value);
                    break;
                case "type":
                    roomDto.setType((String) value);
                    break;
                case "price":
                    roomDto.setPrice((Integer) value);
                    break;
                case "capacity":
                    roomDto.setCapacity((String) value);
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });
        return updateRoom(roomDto, roomId);
    }

    @Override
    public void deleteRoomById(long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        roomRepository.delete(room);
    }

    private RoomDto mapToDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoom_id(room.getRoom_id());
        roomDto.setRoom_number(room.getRoom_number());
        roomDto.setType(room.getType());
        roomDto.setPrice(room.getPrice());
        roomDto.setCapacity(room.getCapacity());
        return roomDto;
    }

    private Room mapToEntity(RoomDto roomDto) {
        Room room = new Room();
        room.setRoom_id(roomDto.getRoom_id());
        room.setRoom_number(roomDto.getRoom_number());
        room.setType(roomDto.getType());
        room.setPrice(roomDto.getPrice());
        room.setCapacity(roomDto.getCapacity());
        return room;
    }
}
