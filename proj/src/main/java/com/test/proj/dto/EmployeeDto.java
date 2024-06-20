package com.test.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private long employee_id;
    private long user_id;

    public String fullname;

    public String position;
}
