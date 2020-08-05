package com.frankmoley.lil.roomreservationservice;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/room-reservations")
public class RoomReservationWebService {
    public static final String RIBBON_URL_ROOMSERVICE_ROOM = "http://ROOMSERVICE/room";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final RoomClient roomClient;
    private final GuestClient guestClient;
    private final ReservationClient reservationClient;
    private final RestTemplate restTemplate;

    public RoomReservationWebService(RoomClient roomClient, GuestClient guestClient, ReservationClient reservationClient, RestTemplate restTemplate) {
        this.roomClient = roomClient;
        this.guestClient = guestClient;
        this.reservationClient = reservationClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<RoomReservation> getRoomReservations(@RequestParam(name = "date", required = false) String dateString) {
        Date date = createDateFromDateString(dateString);
        List<Room> rooms = this.roomClient.getAllRooms();
        return getRoomReservations(date, rooms);
    }

    @GetMapping("/all")
    public List<RoomReservation> getAllRoomReservations(@RequestParam(name = "date", required = false) String dateString) {
        Date date = createDateFromDateString(dateString);
        ResponseEntity<List<Room>> roomEntities = restTemplate
                .exchange(RIBBON_URL_ROOMSERVICE_ROOM, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Room>>() {
                        });
        return getRoomReservations(date, Optional.of(roomEntities).map(HttpEntity::getBody).orElse(Collections.emptyList()));
    }

    private ArrayList<RoomReservation> getRoomReservations(Date date, List<Room> rooms) {
        Map<Long, RoomReservation> roomReservations = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservations.put(room.getId(), roomReservation);
        });
        List<Reservation> reservations = this.reservationClient.getAllReservations(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservations.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestClient.getGuest(reservation.getGuestId());
            roomReservation.setGuestId(guest.getId());
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
        });

        return new ArrayList<>(roomReservations.values());
    }


    private Date createDateFromDateString(String dateString) {
        Date date = null;
        if (null != dateString) {
            try {
                date = DATE_FORMAT.parse(dateString);
            } catch (ParseException pe) {
                date = new Date();
            }
        } else {
            date = new Date();
        }
        return date;
    }

}
