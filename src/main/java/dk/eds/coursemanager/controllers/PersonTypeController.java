package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.PersonType;
import dk.eds.coursemanager.repositories.PersonTypeRepository;
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
    PersonTypeRepository personTypeRepository;

    @Autowired
    public PersonTypeController(PersonTypeRepository personTypeRepository) {
        this.personTypeRepository = personTypeRepository;
    }

    @PostMapping
    public ResponseEntity createPersonType(@Valid @RequestBody PersonType personType) {
        return ResponseEntity.created(new PersonTypeResource(personTypeRepository.save(personType)).getSelfRelURI()).build();
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
