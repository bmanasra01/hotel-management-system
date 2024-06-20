package com.test.proj.services;

import com.test.proj.compositekeys.Housekey;
import com.test.proj.dto.HousekeepingDto;

import java.util.List;
import java.util.Map;

public interface HousekeepingService {
    HousekeepingDto createTask(HousekeepingDto housekeepingDto);
    List<HousekeepingDto> getAllTasks();
    HousekeepingDto getTaskById(Housekey id);
    HousekeepingDto updateTask(HousekeepingDto housekeepingDto, Housekey id);
    HousekeepingDto patchTask(Map<String, Object> updates, Housekey id);
    void deleteTaskById(Housekey id);

    long getUserIdByUsername(String username);
}
