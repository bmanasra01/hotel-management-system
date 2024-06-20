package com.test.proj.compositekeys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Housekey implements Serializable {
    private long task_id;
    private long employee_id;
    private long room_id;
    private long user_id;
}
