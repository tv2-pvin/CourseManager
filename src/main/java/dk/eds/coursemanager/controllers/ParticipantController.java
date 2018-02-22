package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Participant;
import dk.eds.coursemanager.repositories.CourseRepository;
import dk.eds.coursemanager.repositories.ParticipantRepository;
import dk.eds.coursemanager.repositories.ParticipantTypeRepository;
import dk.eds.coursemanager.repositories.PersonRepository;
import dk.eds.coursemanager.resources.ParticipantResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/participants")
public class ParticipantController {

    private final ParticipantRepository participantRepository;
    private final CourseRepository courseRepository;
    private final PersonRepository personRepository;
    private final ParticipantTypeRepository participantTypeRepository;

    @Autowired
    public ParticipantController(ParticipantRepository participantRepository, CourseRepository courseRepository, PersonRepository personRepository, ParticipantTypeRepository participantTypeRepository) {
        this.participantRepository = participantRepository;
        this.courseRepository = courseRepository;
        this.personRepository = personRepository;
        this.participantTypeRepository = participantTypeRepository;
    }

    @GetMapping
    public List<ParticipantResource> getAllParticipants() {
        return participantRepository.findAll().stream().map(ParticipantResource::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createParticipant(@Valid @RequestBody Participant participant) {
        if (!participantTypeRepository.exists(participant.getParticipantType().getId()) ||
                !courseRepository.exists(participant.getCourse().getId()) ||
                !personRepository.existsPersonByUser_Username(participant.getPerson().getUser().getUsername()))
            return ResponseEntity.notFound().build();
        participant.setPerson(personRepository.getPersonByUser_Username(participant.getPerson().getUser().getUsername()));
        participant.setCourse(courseRepository.findOne(participant.getCourse().getId()));
        participant.setParticipantType(participantTypeRepository.findOne(participant.getParticipantType().getId()));
        return ResponseEntity.created(new ParticipantResource(participantRepository.save(participant)).getSelfRelURI()).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ParticipantResource> updateParticipant(@Valid @RequestBody Participant participant, @PathVariable("id") Long id) {
        if (!participantRepository.exists(id))
            return ResponseEntity.notFound().build();
        try {
            Participant p = participantRepository.findOne(id);
            p.setParticipantType(participantTypeRepository.findOne(participant.getParticipantType().getId()));
            p.setCourse(courseRepository.findOne(participant.getCourse().getId()));
            p.setPerson(personRepository.getPersonByUser_Username(participant.getPerson().getUser().getUsername()));
            return ResponseEntity.ok(new ParticipantResource(participantRepository.save(p)));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteParticipant(@PathVariable("id") Long id) {
        if (!participantRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            participantRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ParticipantResource> getParticipant(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ParticipantResource(participantRepository.findOne(id)));
    }
}
