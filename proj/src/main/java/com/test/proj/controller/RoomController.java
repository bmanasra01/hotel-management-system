package com.test.proj.controller;

import com.test.proj.dto.RoomDto;
import com.test.proj.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/addroom")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto) {
        RoomDto createdRoom = roomService.createRoom(roomDto);
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping("/getall")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<RoomDto> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }


    @GetMapping("/v1/{roomId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<RoomDto> getRoomByIdV1(@PathVariable long roomId) {
        RoomDto roomDto = roomService.getRoomById(roomId);
        roomDto.setDescription("Version 1 - Basic Room Information");
        return ResponseEntity.ok(roomDto);
    }


    @GetMapping(value = "/{roomId}", params = "version=2")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<RoomDto> getRoomByIdV2(@PathVariable long roomId) {
        RoomDto roomDto = roomService.getRoomById(roomId);
        roomDto.setDescription("Version 2 - Detailed Room Information with additional details");
        return ResponseEntity.ok(roomDto);
    }


    @GetMapping("/{roomId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<RoomDto> getRoomByIdV3(@PathVariable long roomId, @RequestHeader(value = "API-Version", required = false) String apiVersion) {
        RoomDto roomDto = roomService.getRoomById(roomId);
        if ("3".equals(apiVersion)) {

            roomDto.setDescription("Version 3 - Comprehensive Room Information with full details and pricing");
        }
        return ResponseEntity.ok(roomDto);
    }

    @PutMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDto> updateRoom(@RequestBody RoomDto roomDto, @PathVariable long roomId) {
        RoomDto updatedRoom = roomService.updateRoom(roomDto, roomId);
        return ResponseEntity.ok(updatedRoom);
    }

    @PatchMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDto> patchRoom(@RequestBody Map<String, Object> updates, @PathVariable long roomId) {
        RoomDto updatedRoom = roomService.patchRoom(updates, roomId);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable long roomId) {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }
}
