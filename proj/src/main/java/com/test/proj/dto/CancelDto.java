package com.test.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelDto {
    private long request_id;
    private long reservation_id;
    private long room_id;
    private long customer_id;
    private long admin_id;
    private String statusType;
    private Timestamp request_date;
}
