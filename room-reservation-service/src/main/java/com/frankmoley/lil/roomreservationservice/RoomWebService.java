package com.frankmoley.lil.roomreservationservice;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomWebService {
    private final RestTemplate restTemplate;

    public RoomWebService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        ResponseEntity<List<Room>> roomEntities = restTemplate
                .exchange("http://ROOMSERVICE/room", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Room>>() {
                        });
        return Optional.of(roomEntities).map(HttpEntity::getBody).orElse(Collections.emptyList());
    }

}
