package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.annotations.LoginRequired;
import dk.eds.coursemanager.models.UserType;
import dk.eds.coursemanager.repositories.CityRepository;
import dk.eds.coursemanager.repositories.LocationRepository;
import dk.eds.coursemanager.repositories.UserRepository;
import dk.eds.coursemanager.repositories.UserTypeRepository;
import dk.eds.coursemanager.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/usertypes")
public class UserTypeController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CityRepository cityRepository;


    @GetMapping("{id}/users")
    @LoginRequired
    public ResponseEntity<List<UserResource>> getAllUsersForUserType(@PathVariable("id") Long id) {
        if (userTypeRepository.exists(id)) {
            return ResponseEntity.ok(
                    userRepository.getUsersByUserType(userTypeRepository.findOne(id))
                            .stream()
                            .map(UserResource::new)
                            .collect(Collectors.toList()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @LoginRequired
    public UserType createUserType(@Valid @RequestBody UserType userType) {
        return userTypeRepository.save(userType);
    }

    @GetMapping("{id}")
    @LoginRequired
    public ResponseEntity<UserType> getUserType(@PathVariable("id") Long id) {
        if (userTypeRepository.exists(id))
            return ResponseEntity.ok(userTypeRepository.findOne(id));
        else
            return ResponseEntity.notFound().build();
    }
}
