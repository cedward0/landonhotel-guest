package com.frankmoley.lil.roomreservationservice;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GuestClientFallback implements GuestClient {
    @Override
    public List<Guest> getAllGuests() {
        return Collections.emptyList();
    }

    @Override
    public Guest getGuest(long id) {
        Guest guest = new Guest();
        guest.setFirstName("Guest");
        guest.setLastName("OCCUPIED");
        return guest;
    }
}
