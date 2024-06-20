package com.test.proj.entities;


import com.test.proj.compositekeys.EmployeeKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

     @EmbeddedId
     private EmployeeKey id;

    @ManyToOne
    public User user;

    public String fullname;

    public String position;

}
