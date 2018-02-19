package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.Person;
import dk.eds.coursemanager.models.User;
import dk.eds.coursemanager.repositories.*;
import dk.eds.coursemanager.resources.BookingResource;
import dk.eds.coursemanager.resources.ParticipantResource;
import dk.eds.coursemanager.resources.PersonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static dk.eds.coursemanager.controllers.helpers.ControllerUtils.createOrFetchLocation;
import static dk.eds.coursemanager.controllers.helpers.PersonControllerHelper.setCity;
import static dk.eds.coursemanager.controllers.helpers.PersonControllerHelper.setLocation;

@RestController
@RequestMapping("api/person-types/{personTypeId}/people")
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
    private final
    BookingRepository bookingRepository;
    private final
    ParticipantRepository participantRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, LocationRepository locationRepository, CityRepository cityRepository, PersonTypeRepository personTypeRepository, UserRepository userRepository, BookingRepository bookingRepository, ParticipantRepository participantRepository) {
        this.personRepository = personRepository;
        this.locationRepository = locationRepository;
        this.cityRepository = cityRepository;
        this.personTypeRepository = personTypeRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.participantRepository = participantRepository;
    }

    @GetMapping
    public ResponseEntity<List<PersonResource>> getAllPersons(@PathVariable("personTypeId") Long personTypeId) {
        return ResponseEntity.ok(personRepository.findPeopleByPersonType_Id(personTypeId).stream()
                .filter(u -> u.getUser().isEnabled())
                .map(PersonResource::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("{username}")
    public ResponseEntity<PersonResource> getPerson(@PathVariable("personTypeId") Long personTypeId, @PathVariable("username") String username) {
        if (!personTypeRepository.exists(personTypeId) || !userRepository.existsByUsername(username))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new PersonResource(personRepository.getPersonByUser_Username(username)));
    }

    @PostMapping
    public ResponseEntity createUser(@PathVariable("personTypeId") Long personTypeId, @Valid @RequestBody Person person) {
        if (!personTypeRepository.exists(personTypeId)) return ResponseEntity.notFound().build();

        person.getUser().setPassword(new BCryptPasswordEncoder().encode(person.getUser().getPassword()));
        person.setUser(userRepository.save(person.getUser()));

        person.setPersonType(personTypeRepository.findOne(personTypeId));

        setCity(person, cityRepository);
        setLocation(person, locationRepository);

        person = personRepository.save(person);

        return ResponseEntity.created(new PersonResource(person).getSelfRelURI()).build();
    }


    @PutMapping("{username}")
    public ResponseEntity updatePerson(@PathVariable("personTypeId") Long personTypeId, @PathVariable("username") String username, @Valid @RequestBody Person person) {
        if (!personTypeRepository.exists(personTypeId) || !userRepository.existsByUsername(username))
            return ResponseEntity.notFound().build();
        try {
            Person p = personRepository.getPersonByUser_Username(username);
            User u = p.getUser();
            u.setPassword(new BCryptPasswordEncoder().encode(person.getUser().getPassword()));
            userRepository.save(u);
            p.setUser(u);
            Location location = createOrFetchLocation(person.getLocation(), locationRepository, cityRepository);
            p.setLocation(location);
            Person save = personRepository.save(p);
            return ResponseEntity.ok(new PersonResource(save));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @GetMapping("{username}/bookings")
    public ResponseEntity<List<BookingResource>> getBookings(@PathVariable("personTypeId") Long personTypeId, @PathVariable("username") String username) {
        if (!personTypeRepository.exists(personTypeId) || !userRepository.existsByUsername(username))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                bookingRepository.getAllByBooker_User_Username(username).stream().map(BookingResource::new).collect(Collectors.toList())
        );
    }

    @GetMapping("{username}/participants")
    public ResponseEntity<List<ParticipantResource>> getParticipants(@PathVariable("personTypeId") Long personTypeId, @PathVariable("username") String username) {
        if (!personTypeRepository.exists(personTypeId) || !userRepository.existsByUsername(username))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                participantRepository.findAllByPerson_User_Username(username).stream().map(ParticipantResource::new).collect(Collectors.toList())
        );
    }
}
