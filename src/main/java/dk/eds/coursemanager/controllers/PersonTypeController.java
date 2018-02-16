package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.PersonType;
import dk.eds.coursemanager.repositories.PersonRepository;
import dk.eds.coursemanager.repositories.PersonTypeRepository;
import dk.eds.coursemanager.resources.PersonResource;
import dk.eds.coursemanager.resources.PersonTypeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/person-types")
public class PersonTypeController {

    private final
    PersonRepository personRepository;
    private final
    PersonTypeRepository personTypeRepository;

    @Autowired
    public PersonTypeController(PersonRepository personRepository, PersonTypeRepository personTypeRepository) {
        this.personRepository = personRepository;
        this.personTypeRepository = personTypeRepository;
    }


    @GetMapping("{id}/people")
    public ResponseEntity<List<PersonResource>> getAllPeopleForPersonType(@PathVariable("id") Long id) {
        if (personTypeRepository.exists(id)) {
            PersonType type = personTypeRepository.findOne(id);
            return ResponseEntity.ok(
                    personRepository.findPeopleByPersonType(type)
                            .stream()
                            .filter(p -> p.getUser().isEnabled())
                            .map(PersonResource::new)
                            .collect(Collectors.toList())
            );
        } else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public PersonTypeResource createPersonType(@Valid @RequestBody PersonType personType) {
        return new PersonTypeResource(personTypeRepository.save(personType));
    }

    @GetMapping
    public List<PersonTypeResource> getAllPersonTypes() {
        return personTypeRepository.findAll().stream()
                .map(PersonTypeResource::new)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonTypeResource> getPersonType(@PathVariable("id") Long id) {
        if (personTypeRepository.exists(id))
            return ResponseEntity.ok(new PersonTypeResource(personTypeRepository.findOne(id)));
        else
            return ResponseEntity.notFound().build();
    }
}
