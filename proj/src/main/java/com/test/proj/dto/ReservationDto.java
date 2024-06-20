package com.test.proj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private long reservation_id;
    private long user_id;
    private long room_id;
    private Timestamp reservation_date;
    private Timestamp check_in_date;
    private Timestamp check_out_date;
    private String statusType;
}
