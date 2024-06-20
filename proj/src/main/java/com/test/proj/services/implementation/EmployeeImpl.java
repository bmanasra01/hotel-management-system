package com.test.proj.services.implementation;

import com.test.proj.compositekeys.EmployeeKey;
import com.test.proj.dto.EmployeeDto;
import com.test.proj.entities.Employee;
import com.test.proj.entities.User;
import com.test.proj.exception.ResourceNotFoundException;
import com.test.proj.repository.EmployeeRepository;
import com.test.proj.repository.UserRepository;
import com.test.proj.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Autowired
    public EmployeeImpl(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = mapToEntity(employeeDto);
        Employee newEmployee = employeeRepository.save(employee);
        return mapToDto(newEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(EmployeeKey id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return mapToDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, EmployeeKey id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        employee.setFullname(employeeDto.getFullname());
        employee.setPosition(employeeDto.getPosition());

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToDto(updatedEmployee);
    }

    @Override
    public EmployeeDto patchEmployee(Map<String, Object> updates, EmployeeKey id) {
        EmployeeDto employeeDto = getEmployeeById(id);
        updates.forEach((key, value) -> {
            switch (key) {
                case "fullname":
                    employeeDto.setFullname((String) value);
                    break;
                case "position":
                    employeeDto.setPosition((String) value);
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });
        return updateEmployee(employeeDto, id);
    }

    @Override
    public void deleteEmployeeById(EmployeeKey id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employeeRepository.delete(employee);
    }

    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployee_id(employee.getId().getEmployee_id());
        employeeDto.setUser_id(employee.getId().getUser_id());
        employeeDto.setFullname(employee.getFullname());
        employeeDto.setPosition(employee.getPosition());
        return employeeDto;
    }

    private Employee mapToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        EmployeeKey employeeKey = new EmployeeKey(employeeDto.getEmployee_id(), employeeDto.getUser_id());
        employee.setId(employeeKey);
        employee.setFullname(employeeDto.getFullname());
        employee.setPosition(employeeDto.getPosition());
        return employee;
    }

    @Override
    public long getUserIdByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
        return user.getUser_id();
    }
}
