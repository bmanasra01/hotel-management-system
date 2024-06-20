package com.test.proj.entities;


import com.test.proj.compositekeys.BillKey;
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
@Table(name = "bill")
public class Bill implements Serializable {

   @EmbeddedId
   private BillKey id;


    @OneToOne
    private Reservation reservation;

    private float amount;
    private Timestamp issue_date;
    private Timestamp due_date;
}
