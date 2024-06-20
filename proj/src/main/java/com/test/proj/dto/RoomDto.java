package com.test.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private long room_id;
    private int room_number;
    private String type;
    private int price;
    private String capacity;
    private String description;
}
