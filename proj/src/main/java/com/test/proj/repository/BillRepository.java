package com.test.proj.repository;

import com.test.proj.compositekeys.BillKey;
import com.test.proj.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillRepository extends JpaRepository<Bill, BillKey> {

    @Query("SELECT r.price FROM Room r JOIN Reservation res ON r.room_id = res.id.room_id WHERE res.id.reservation_id = :reservationId")
    float findRoomPriceByReservationId(@Param("reservationId") long reservationId);
}
