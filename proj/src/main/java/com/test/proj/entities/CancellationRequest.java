package com.test.proj.entities;

import com.test.proj.compositekeys.CancelKey;
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
@Table(name = "cancellationrequest")
public class CancellationRequest implements Serializable {

    @EmbeddedId
    private CancelKey id;

    @OneToOne
    private Reservation reservation;



    private Timestamp request_date;

    @Enumerated(EnumType.STRING)
    private CancellationRequest.StatusType statusType;

    public enum StatusType {
        PENDING,
        APPROVED,
        DISAPPROVED
    }
}
