package com.test.proj.repository;

import com.test.proj.compositekeys.ReservationKey;
import com.test.proj.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationKey> {

    @Query("SELECT r FROM Reservation r JOIN r.user u WHERE u.user_id = :customerId")
    List<Reservation> findByCustomerId(@Param("customerId") long customerId);

    @Query("SELECT r FROM Reservation r JOIN r.user u WHERE u.fullName LIKE %:customerName%")
    List<Reservation> findByCustomerName(@Param("customerName") String customerName);
}
