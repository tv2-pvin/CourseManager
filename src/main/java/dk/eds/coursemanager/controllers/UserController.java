package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.User;
import dk.eds.coursemanager.models.UserType;
import dk.eds.coursemanager.repositories.CityRepository;
import dk.eds.coursemanager.repositories.LocationRepository;
import dk.eds.coursemanager.repositories.UserRepository;
import dk.eds.coursemanager.repositories.UserTypeRepository;
import dk.eds.coursemanager.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/usertypes")
public class UserController {

    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CityRepository cityRepository;


    @GetMapping("{id}/users")
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

    @PostMapping("{id}/users")
    public ResponseEntity createUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {
        UserType one = userTypeRepository.findOne(id);
        if (one != null) {
            user.setPassword(B_CRYPT_PASSWORD_ENCODER.encode(user.getPassword()));
            user.setUserType(one);
            user.getLocation().setCity(createOrFetchCity(user));
            user.setLocation(createOrFetchLocation(user));

            user = userRepository.save(user);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.created(URI.create(new UserResource(user).getLink("self").getHref())).build();
    }

    private Location createOrFetchLocation(User user) {
        Location loc = user.getLocation();
        if (locationRepository.existsLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity()))
            return locationRepository.getLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity());
        else
            return locationRepository.save(loc);
    }

    private City createOrFetchCity(User user) {
        City userCity = user.getLocation().getCity();
        if (!cityRepository.existsCityByZipCode(userCity.getZipCode()))
            return cityRepository.save(userCity);
        else
            return cityRepository.getCityByZipCode(userCity.getZipCode());
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User update, @PathVariable("id") Long id) {
        if (userRepository.exists(id)) {
            User current = userRepository.findOne(id);
            /*current.setLastName(update.getLastName());
            current.setFirstName(update.getFirstName());
            current.setEmail(update.getEmail());
            current.setPassword(B_CRYPT_PASSWORD_ENCODER.encode(update.getPassword()));
            current.setLocation(update.getLocation());
            current.setUserType(update.getUserType());
            userRepository.save(current);*/
            update.setId(current.getId());
            userRepository.save(update);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public UserType createUserType(@Valid @RequestBody UserType userType) {
        return userTypeRepository.save(userType);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserType> getUserType(@PathVariable("id") Long id) {
        if (userTypeRepository.exists(id))
            return ResponseEntity.ok(userTypeRepository.findOne(id));
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}/users/{username}")
    public ResponseEntity<UserResource> getUser(@PathVariable("id") Long id, @PathVariable("username") String username) {
        if (!userTypeRepository.exists(id)) return ResponseEntity.notFound().build();
        if (!userRepository.existsByUsername(username)) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new UserResource(userRepository.getUserByUsername(username)));
    }
}
