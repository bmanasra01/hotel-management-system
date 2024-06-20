package com.test.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {

    private long bill_id;

    private long reservation_id;

    private float amount;
    private Timestamp issue_date;
    private Timestamp due_date;
}
