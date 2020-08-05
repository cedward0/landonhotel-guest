package com.frankmoley.lil.roomservices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Api(value = "rooms",description = "Some description",tags = ("rooms"))
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;
    
    @ApiOperation(value = "Get ALL rooms", notes = "Gets all rooms in the system", nickname = "getRooms")
    @RequestMapping(value = "/room",method = RequestMethod.GET)
    public List<Room> findAll(@RequestParam(name = "roomNumber",required = false)String roomNumber){
        if (StringUtils.isNotEmpty(roomNumber)){
            return Collections.singletonList(this.roomRepository.findById(Long.valueOf(roomNumber)).orElse(null));
        }
        return (List<Room>)this.roomRepository.findAll();
    }
}
