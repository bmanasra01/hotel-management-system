package com.test.proj.services;

import com.test.proj.compositekeys.ReservationKey;
import com.test.proj.dto.ReservationDto;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    ReservationDto createReservation(ReservationDto reservationDto);
    List<ReservationDto> getAllReservations();
    ReservationDto getReservationById(ReservationKey id);
    ReservationDto updateReservation(ReservationDto reservationDto, ReservationKey id);
    ReservationDto patchReservation(Map<String, Object> updates, ReservationKey id);
    void deleteReservationById(ReservationKey id);
    long getUserIdByUsername(String username);

    List<ReservationDto> findByCustomerId(long customerId);
    List<ReservationDto> findByCustomerName(String customerName);
}
