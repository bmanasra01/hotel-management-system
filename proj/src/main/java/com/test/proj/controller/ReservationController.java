package com.test.proj.controller;

import com.test.proj.compositekeys.ReservationKey;
import com.test.proj.dto.ReservationDto;
import com.test.proj.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        long userId = getCurrentUserId();
        reservationDto.setUser_id(userId);
        ReservationDto createdReservation = reservationService.createReservation(reservationDto);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{reservationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable long reservationId) {
        long userId = getCurrentUserId();
        ReservationKey reservationKey = new ReservationKey(reservationId, userId, 0);
        ReservationDto reservationDto = reservationService.getReservationById(reservationKey);
        return ResponseEntity.ok(reservationDto);
    }

    @PutMapping("/{reservationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservationDto, @PathVariable long reservationId) {
        long userId = getCurrentUserId();
        ReservationKey reservationKey = new ReservationKey(reservationId, userId, reservationDto.getRoom_id());
        ReservationDto updatedReservation = reservationService.updateReservation(reservationDto, reservationKey);
        return ResponseEntity.ok(updatedReservation);
    }

    @PatchMapping("/{reservationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationDto> patchReservation(@RequestBody Map<String, Object> updates, @PathVariable long reservationId) {
        long userId = getCurrentUserId();
        ReservationKey reservationKey = new ReservationKey(reservationId, userId, 0);
        ReservationDto updatedReservation = reservationService.patchReservation(updates, reservationKey);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReservation(@PathVariable long reservationId) {
        long userId = getCurrentUserId();
        ReservationKey reservationKey = new ReservationKey(reservationId, userId, 0);
        reservationService.deleteReservationById(reservationKey);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchByCustomerId")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDto>> searchByCustomerId(@RequestParam long customerId) {
        List<ReservationDto> reservations = reservationService.findByCustomerId(customerId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/searchByCustomerName")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDto>> searchByCustomerName(@RequestParam String customerName) {
        List<ReservationDto> reservations = reservationService.findByCustomerName(customerName);
        return ResponseEntity.ok(reservations);
    }

    private long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return reservationService.getUserIdByUsername(username);
    }
}
