package com.frankmoley.lil.roomreservationservice;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

@Component
public class ReservationClientFallback implements ReservationClient {
    @Override
    public List<Reservation> getAllReservations(Date date) {
        return Collections.emptyList();
    }

    @Override
    public Reservation getReservation(long id) {
        return new Reservation();
    }
}
