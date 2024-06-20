package com.test.proj.services.implementation;

import com.test.proj.compositekeys.Housekey;
import com.test.proj.dto.HousekeepingDto;
import com.test.proj.entities.Housekeeping;
import com.test.proj.entities.User;
import com.test.proj.exception.ResourceNotFoundException;
import com.test.proj.repository.HousekeepingRepository;
import com.test.proj.repository.UserRepository;
import com.test.proj.services.HousekeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HousekeepingImpl implements HousekeepingService {
    private final HousekeepingRepository housekeepingRepository;
    private final UserRepository userRepository;

    @Autowired
    public HousekeepingImpl(HousekeepingRepository housekeepingRepository, UserRepository userRepository) {
        this.housekeepingRepository = housekeepingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public HousekeepingDto createTask(HousekeepingDto housekeepingDto) {
        Housekeeping housekeeping = mapToEntity(housekeepingDto);
        Housekeeping newTask = housekeepingRepository.save(housekeeping);
        return mapToDto(newTask);
    }

    @Override
    public List<HousekeepingDto> getAllTasks() {
        List<Housekeeping> tasks = housekeepingRepository.findAll();
        return tasks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public HousekeepingDto getTaskById(Housekey id) {
        Housekeeping task = housekeepingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Housekeeping", "id", id));
        return mapToDto(task);
    }

    @Override
    public HousekeepingDto updateTask(HousekeepingDto housekeepingDto, Housekey id) {
        Housekeeping task = housekeepingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Housekeeping", "id", id));

        task.setTask_description(housekeepingDto.getTask_description());
        task.setTask_date(housekeepingDto.getTask_date());
        task.setStatusType(Housekeeping.StatusType.valueOf(housekeepingDto.getStatusType()));

        Housekeeping updatedTask = housekeepingRepository.save(task);
        return mapToDto(updatedTask);
    }

    @Override
    public HousekeepingDto patchTask(Map<String, Object> updates, Housekey id) {
        HousekeepingDto housekeepingDto = getTaskById(id);
        updates.forEach((key, value) -> {
            switch (key) {
                case "task_description":
                    housekeepingDto.setTask_description((String) value);
                    break;
                case "task_date":
                    housekeepingDto.setTask_date((Timestamp) value);
                    break;
                case "statusType":
                    housekeepingDto.setStatusType((String) value);
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });
        return updateTask(housekeepingDto, id);
    }

    @Override
    public void deleteTaskById(Housekey id) {
        Housekeeping task = housekeepingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Housekeeping", "id", id));
        housekeepingRepository.delete(task);
    }

    private HousekeepingDto mapToDto(Housekeeping task) {
        HousekeepingDto housekeepingDto = new HousekeepingDto();
        housekeepingDto.setTask_id(task.getId().getTask_id());
        housekeepingDto.setEmployee_id(task.getId().getEmployee_id());
        housekeepingDto.setRoom_id(task.getId().getRoom_id());
        housekeepingDto.setUser_id(task.getId().getUser_id());
        housekeepingDto.setTask_description(task.getTask_description());
        housekeepingDto.setTask_date(task.getTask_date());
        housekeepingDto.setStatusType(task.getStatusType().name());
        return housekeepingDto;
    }

    private Housekeeping mapToEntity(HousekeepingDto housekeepingDto) {
        Housekeeping task = new Housekeeping();
        Housekey housekey = new Housekey(housekeepingDto.getTask_id(), housekeepingDto.getEmployee_id(), housekeepingDto.getRoom_id(), housekeepingDto.getUser_id());
        task.setId(housekey);
        task.setTask_description(housekeepingDto.getTask_description());
        task.setTask_date(housekeepingDto.getTask_date());
        task.setStatusType(Housekeeping.StatusType.valueOf(housekeepingDto.getStatusType()));
        return task;
    }

    @Override
    public long getUserIdByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
        return user.getUser_id();
    }
}
