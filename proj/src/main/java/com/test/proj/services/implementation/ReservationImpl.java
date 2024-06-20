package com.test.proj.services.implementation;

import com.test.proj.compositekeys.ReservationKey;
import com.test.proj.dto.ReservationDto;
import com.test.proj.entities.Reservation;
import com.test.proj.entities.User;
import com.test.proj.exception.ResourceNotFoundException;
import com.test.proj.repository.ReservationRepository;
import com.test.proj.repository.UserRepository;
import com.test.proj.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReservationImpl(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = mapToEntity(reservationDto);
        Reservation newReservation = reservationRepository.save(reservation);
        return mapToDto(newReservation);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDto getReservationById(ReservationKey id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        return mapToDto(reservation);
    }

    @Override
    @Transactional
    public ReservationDto updateReservation(ReservationDto reservationDto, ReservationKey id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));

        reservation.setReservation_date(reservationDto.getReservation_date());
        reservation.setCheck_in_date(reservationDto.getCheck_in_date());
        reservation.setCheck_out_date(reservationDto.getCheck_out_date());
        reservation.setStatusType(Reservation.StatusType.valueOf(reservationDto.getStatusType()));

        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapToDto(updatedReservation);
    }

    @Override
    @Transactional
    public ReservationDto patchReservation(Map<String, Object> updates, ReservationKey id) {
        ReservationDto reservationDto = getReservationById(id);
        updates.forEach((key, value) -> {
            switch (key) {
                case "reservation_date":
                    reservationDto.setReservation_date((Timestamp) value);
                    break;
                case "check_in_date":
                    reservationDto.setCheck_in_date((Timestamp) value);
                    break;
                case "check_out_date":
                    reservationDto.setCheck_out_date((Timestamp) value);
                    break;
                case "statusType":
                    reservationDto.setStatusType((String) value);
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });
        return updateReservation(reservationDto, id);
    }

    @Override
    @Transactional
    public void deleteReservationById(ReservationKey id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        reservationRepository.delete(reservation);
    }

    @Override
    public long getUserIdByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
        return user.getUser_id();
    }

    @Override
    public List<ReservationDto> findByCustomerId(long customerId) {
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        return reservations.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> findByCustomerName(String customerName) {
        List<Reservation> reservations = reservationRepository.findByCustomerName(customerName);
        return reservations.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ReservationDto mapToDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservation_id(reservation.getId().getReservation_id());
        reservationDto.setUser_id(reservation.getId().getUser_id());
        reservationDto.setRoom_id(reservation.getId().getRoom_id());
        reservationDto.setReservation_date(reservation.getReservation_date());
        reservationDto.setCheck_in_date(reservation.getCheck_in_date());
        reservationDto.setCheck_out_date(reservation.getCheck_out_date());
        reservationDto.setStatusType(reservation.getStatusType().name());
        return reservationDto;
    }

    private Reservation mapToEntity(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        ReservationKey reservationKey = new ReservationKey(reservationDto.getReservation_id(), reservationDto.getUser_id(), reservationDto.getRoom_id());
        reservation.setId(reservationKey);
        reservation.setReservation_date(reservationDto.getReservation_date());
        reservation.setCheck_in_date(reservationDto.getCheck_in_date());
        reservation.setCheck_out_date(reservationDto.getCheck_out_date());
        reservation.setStatusType(Reservation.StatusType.valueOf(reservationDto.getStatusType()));
        return reservation;
    }
}
