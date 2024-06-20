package com.test.proj.controller;

import com.test.proj.dto.HousekeepingDto;
import com.test.proj.services.HousekeepingService;
import com.test.proj.compositekeys.Housekey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/housekeeping")
public class HousekeepingController {

    private final HousekeepingService housekeepingService;

    @Autowired
    public HousekeepingController(HousekeepingService housekeepingService) {
        this.housekeepingService = housekeepingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HousekeepingDto> createTask(@RequestBody HousekeepingDto housekeepingDto) {
        long userId = getCurrentUserId();
        housekeepingDto.setUser_id(userId);
        HousekeepingDto createdTask = housekeepingService.createTask(housekeepingDto);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HousekeepingDto>> getAllTasks() {
        List<HousekeepingDto> tasks = housekeepingService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HousekeepingDto> getTaskById(@PathVariable long taskId) {
        long userId = getCurrentUserId();
        Housekey housekey = new Housekey(taskId, getEmployeeId(), getRoomId(), userId);
        HousekeepingDto housekeepingDto = housekeepingService.getTaskById(housekey);
        return ResponseEntity.ok(housekeepingDto);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HousekeepingDto> updateTask(@RequestBody HousekeepingDto housekeepingDto, @PathVariable long taskId) {
        long userId = getCurrentUserId();
        housekeepingDto.setUser_id(userId);
        Housekey housekey = new Housekey(taskId, housekeepingDto.getEmployee_id(), housekeepingDto.getRoom_id(), userId);
        HousekeepingDto updatedTask = housekeepingService.updateTask(housekeepingDto, housekey);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HousekeepingDto> patchTask(@RequestBody Map<String, Object> updates, @PathVariable long taskId) {
        long userId = getCurrentUserId();
        Housekey housekey = new Housekey(taskId, getEmployeeId(), getRoomId(), userId);
        HousekeepingDto updatedTask = housekeepingService.patchTask(updates, housekey);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable long taskId) {
        long userId = getCurrentUserId();
        Housekey housekey = new Housekey(taskId, getEmployeeId(), getRoomId(), userId);
        housekeepingService.deleteTaskById(housekey);
        return ResponseEntity.noContent().build();
    }

    private long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return housekeepingService.getUserIdByUsername(username);
    }

    private long getEmployeeId() {
        return 1;
    }

    private long getRoomId() {

        return 1;
    }
}
