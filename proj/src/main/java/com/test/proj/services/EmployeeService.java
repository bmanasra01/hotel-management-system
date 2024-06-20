package com.test.proj.services;

import com.test.proj.compositekeys.EmployeeKey;
import com.test.proj.dto.EmployeeDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(EmployeeKey id);
    EmployeeDto updateEmployee(EmployeeDto employeeDto, EmployeeKey id);
    EmployeeDto patchEmployee(Map<String, Object> updates, EmployeeKey id);
    void deleteEmployeeById(EmployeeKey id);
    long getUserIdByUsername(String username);
}
