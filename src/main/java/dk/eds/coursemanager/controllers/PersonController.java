package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.Person;
import dk.eds.coursemanager.models.User;
import dk.eds.coursemanager.repositories.*;
import dk.eds.coursemanager.resources.PersonResource;
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
import static dk.eds.coursemanager.controllers.helpers.PersonControllerHelper.setCity;
import static dk.eds.coursemanager.controllers.helpers.PersonControllerHelper.setLocation;

@RestController
@RequestMapping("api/people")
public class PersonController {

    private final
    PersonRepository personRepository;
    private final
    LocationRepository locationRepository;
    private final
    CityRepository cityRepository;
    private final
    PersonTypeRepository personTypeRepository;
    private final
    UserRepository userRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, LocationRepository locationRepository, CityRepository cityRepository, PersonTypeRepository personTypeRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.locationRepository = locationRepository;
        this.cityRepository = cityRepository;
        this.personTypeRepository = personTypeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<PersonResource>> getAllPersons() {
        return ResponseEntity.ok(personRepository.findAll().stream().filter(u -> u.getUser().isEnabled()).map(PersonResource::new).collect(Collectors.toList()));
    }

    @GetMapping("{username}")
    public ResponseEntity<PersonResource> getPerson(@PathVariable("username") String username) {
        if (!userRepository.existsByUsername(username)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new PersonResource(personRepository.findPersonByUser_Username(username)));
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody Person person) {
        if (!personTypeRepository.exists(person.getPersonType().getId())) return ResponseEntity.notFound().build();

        person.getUser().setPassword(new BCryptPasswordEncoder().encode(person.getUser().getPassword()));
        person.setUser(userRepository.save(person.getUser()));

        person.setPersonType(personTypeRepository.findOne(person.getPersonType().getId()));

        setCity(person, cityRepository);
        setLocation(person, locationRepository);

        person = personRepository.save(person);

        return ResponseEntity.created(URI.create(new PersonResource(person).getLink("self").getHref())).build();
    }


    @PutMapping("{username}")
    public ResponseEntity updatePerson(@PathVariable("username") String username, @Valid @RequestBody Person person) {
        if (!userRepository.existsByUsername(username)) return ResponseEntity.notFound().build();
        try {
            Person p = personRepository.findPersonByUser_Username(username);
            User u = p.getUser();
            u.setPassword(new BCryptPasswordEncoder().encode(person.getUser().getPassword()));
            userRepository.save(u);
            p.setUser(u);
            City city = createOrFetchCity(person, cityRepository);
            Location location = createOrFetchLocation(person, locationRepository);
            location.setCity(city);
            p.setLocation(location);
            Person save = personRepository.save(p);
            return ResponseEntity.ok(new PersonResource(save));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }
}
