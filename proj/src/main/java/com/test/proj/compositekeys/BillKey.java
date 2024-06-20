package com.test.proj.compositekeys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BillKey implements Serializable {
    private long bill_id;

    private long reservation_id;
}
