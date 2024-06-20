package com.test.proj.controller;

import com.test.proj.dto.EmployeeDto;
import com.test.proj.services.EmployeeService;
import com.test.proj.compositekeys.EmployeeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        long userId = getCurrentUserId();
        employeeDto.setUser_id(userId);
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long employeeId) {
        long userId = getCurrentUserId();
        EmployeeKey employeeKey = new EmployeeKey(employeeId, userId);
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeKey);
        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable long employeeId) {
        long userId = getCurrentUserId();
        EmployeeKey employeeKey = new EmployeeKey(employeeId, userId);
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto, employeeKey);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDto> patchEmployee(@RequestBody Map<String, Object> updates, @PathVariable long employeeId) {
        long userId = getCurrentUserId();
        EmployeeKey employeeKey = new EmployeeKey(employeeId, userId);
        EmployeeDto updatedEmployee = employeeService.patchEmployee(updates, employeeKey);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long employeeId) {
        long userId = getCurrentUserId();
        EmployeeKey employeeKey = new EmployeeKey(employeeId, userId);
        employeeService.deleteEmployeeById(employeeKey);
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
        return employeeService.getUserIdByUsername(username);
    }
}
