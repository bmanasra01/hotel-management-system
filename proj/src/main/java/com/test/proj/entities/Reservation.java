package com.test.proj.entities;

import com.test.proj.compositekeys.ReservationKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Reservation implements Serializable {

    @EmbeddedId
    private ReservationKey id;

    @ManyToOne
    private User user;


    private Timestamp reservation_date;
    private Timestamp check_in_date;
    private Timestamp check_out_date;

    @Enumerated(EnumType.STRING)
    private Reservation.StatusType statusType;

    public enum StatusType {
        BOOKED,
        CANCELLED,
        CHECKED_IN,
        CHECKED_OUT
    }
}
