package com.frankmoley.security.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guestsserv")
public class GuestWebServices {
    GuestRepository guestRepository;

    public GuestWebServices(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }
    @GetMapping
    public Iterable<Guest> getAllGuests(){
        return guestRepository.findAll();
    }
    @GetMapping("/{id}")
    public Guest getGuest(@PathVariable("id") long id){
        return guestRepository.findById(id).get();
    }
}
