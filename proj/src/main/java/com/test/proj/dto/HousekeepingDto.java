package com.test.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HousekeepingDto {

    private long task_id;
    private long employee_id;
    private long room_id;
    private long user_id;

    private String task_description;

    private Timestamp task_date;

    private String statusType;
}
