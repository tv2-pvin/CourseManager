package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.controllers.helpers.ControllerUtils;
import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.Room;
import dk.eds.coursemanager.repositories.BookingRepository;
import dk.eds.coursemanager.repositories.CityRepository;
import dk.eds.coursemanager.repositories.LocationRepository;
import dk.eds.coursemanager.repositories.RoomRepository;
import dk.eds.coursemanager.resources.BookingResource;
import dk.eds.coursemanager.resources.RoomResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rooms")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    BookingRepository bookingRepository;

    @GetMapping
    public List<RoomResource> getAllRooms() {
        return roomRepository.findAll().stream().map(RoomResource::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createRoom(@Valid @RequestBody Room room) {
        Location location = ControllerUtils.createOrFetchLocation(room.getLocation(), locationRepository, cityRepository);
        room.setLocation(location);
        return ResponseEntity.created(new RoomResource(roomRepository.save(room)).getSelfRelURI()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<RoomResource> getRoom(@PathVariable("id") Long id) {
        if (!roomRepository.exists(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new RoomResource(roomRepository.findOne(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteRoom(@PathVariable("id") Long id) {
        if (!roomRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            roomRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<RoomResource> updateRoom(@PathVariable("id") Long id, @Valid @RequestBody Room room) {
        if (!roomRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            Room r = roomRepository.findOne(id);
            r.setLocation(ControllerUtils.createOrFetchLocation(room.getLocation(), locationRepository, cityRepository));
            r.setName(room.getName());
            return ResponseEntity.ok(new RoomResource(roomRepository.save(r)));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @GetMapping("{id}/bookings")
    public List<BookingResource> getAllRoomBookings(@PathVariable("id") Long id) {
        return bookingRepository.getAllByRoom_Id(id).stream().map(BookingResource::new).collect(Collectors.toList());
    }
}
