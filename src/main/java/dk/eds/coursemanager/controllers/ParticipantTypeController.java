package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.ParticipantType;
import dk.eds.coursemanager.repositories.ParticipantRepository;
import dk.eds.coursemanager.repositories.ParticipantTypeRepository;
import dk.eds.coursemanager.repositories.PriceRepository;
import dk.eds.coursemanager.resources.ParticipantResource;
import dk.eds.coursemanager.resources.ParticipantTypeResource;
import dk.eds.coursemanager.resources.PriceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/participant-types")
public class ParticipantTypeController {

    private final ParticipantTypeRepository participantTypeRepository;
    private final ParticipantRepository participantRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public ParticipantTypeController(ParticipantTypeRepository participantTypeRepository, ParticipantRepository participantRepository, PriceRepository priceRepository) {
        this.participantTypeRepository = participantTypeRepository;
        this.participantRepository = participantRepository;
        this.priceRepository = priceRepository;
    }

    @GetMapping
    public List<ParticipantTypeResource> getParticipantTypes() {
        return participantTypeRepository.findAll().stream().map(ParticipantTypeResource::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ParticipantTypeResource> createParticipantType(@Valid @RequestBody ParticipantType participantType) {
        return ResponseEntity.created(new ParticipantTypeResource(participantTypeRepository.save(participantType)).getSelfRelURI()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ParticipantTypeResource> getParticipantType(@PathVariable("id") Long id) {
        if (!participantTypeRepository.exists(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new ParticipantTypeResource(participantTypeRepository.findOne(id)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ParticipantTypeResource> updateParticipantType(@PathVariable("id") Long id, @Valid @RequestBody ParticipantType participantType) {
        if (!participantTypeRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            ParticipantType type = participantTypeRepository.findOne(id);
            type.setName(participantType.getName());
            participantTypeRepository.save(type);
            return ResponseEntity.ok(new ParticipantTypeResource(type));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteParticipantType(@PathVariable("id") Long id) {
        if (!participantTypeRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            participantTypeRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @GetMapping("{id}/participants")
    public ResponseEntity<List<ParticipantResource>> getParticipants(@PathVariable("id") Long id) {
        if (!participantTypeRepository.exists(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                participantRepository.findAllByParticipantType_Id(id).stream().map(ParticipantResource::new).collect(Collectors.toList())
        );
    }

    @GetMapping("{id}/prices")
    public ResponseEntity<List<PriceResource>> getPrices(@PathVariable("id") Long id) {
        if (!participantTypeRepository.exists(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                priceRepository.getAllByParticipantType_Id(id).stream().map(PriceResource::new).collect(Collectors.toList())
        );
    }
}
