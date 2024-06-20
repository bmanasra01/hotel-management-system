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
public class ReservationKey implements Serializable {
    private long reservation_id;
    private long user_id;
    private long room_id;


}
