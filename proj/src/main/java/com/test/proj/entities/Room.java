package com.test.proj.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long room_id;

    @OneToOne
    private Reservation reservation;

    private int room_number;
    private String type;
    private int price;
    private String capacity;
    private String description;


}
