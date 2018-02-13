package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Token;
import dk.eds.coursemanager.models.User;
import dk.eds.coursemanager.repositories.*;
import dk.eds.coursemanager.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static dk.eds.coursemanager.controllers.helpers.ControllerUtils.createOrFetchCity;
import static dk.eds.coursemanager.controllers.helpers.ControllerUtils.createOrFetchLocation;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    TokenRepository tokenRepository;

    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll().stream().map(UserResource::new).collect(Collectors.toList()));
    }

    @GetMapping("{username}")
    public ResponseEntity<UserResource> getUser(@PathVariable("username") String username) {
        if (!userRepository.existsByUsername(username)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new UserResource(userRepository.getUserByUsername(username)));
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        if (!userTypeRepository.exists(user.getUserType().getId())) return ResponseEntity.notFound().build();
        user.setPassword(B_CRYPT_PASSWORD_ENCODER.encode(user.getPassword()));
        user.setUserType(userTypeRepository.findOne(user.getUserType().getId()));
        user.getLocation().setCity(createOrFetchCity(user, cityRepository));
        user.setLocation(createOrFetchLocation(user, locationRepository));
        user = userRepository.save(user);
        return ResponseEntity.created(URI.create(new UserResource(user).getLink("self").getHref())).build();
    }

    @PostMapping("{username}")
    public ResponseEntity authenticateUser(@PathVariable("username") String username, @Valid @RequestBody User user) {
        if (userRepository.existsByUsername(username)) {
            if (user.isValid()) {
                Token token = tokenRepository.save(Token.generate(user));
                return ResponseEntity.ok(token.getToken());
            }
        }
        return ResponseEntity.status(401).build();

    }
}
