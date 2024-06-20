package com.test.proj.entities;

import com.test.proj.compositekeys.Housekey;
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
public class Housekeeping implements Serializable {

    @EmbeddedId
    private Housekey id;


    @ManyToOne
    public Employee employee;

    @ManyToOne
    public Room room;

    @ManyToOne
    public User user;

    private String task_description;

    private Timestamp task_date;

    @Enumerated(EnumType.STRING)
    private Housekeeping.StatusType statusType;

    public enum StatusType {
        COMPLETED,
        SCHEDULED
    }
}
