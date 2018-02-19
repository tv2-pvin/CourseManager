package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Price;
import dk.eds.coursemanager.repositories.CourseRepository;
import dk.eds.coursemanager.repositories.ParticipantTypeRepository;
import dk.eds.coursemanager.repositories.PriceRepository;
import dk.eds.coursemanager.resources.PriceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/prices")
public class PriceController {

    @Autowired
    PriceRepository priceRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ParticipantTypeRepository participantTypeRepository;

    @GetMapping
    public List<PriceResource> getAllPrices() {
        return priceRepository.findAll().stream().map(PriceResource::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createPrice(@Valid @RequestBody Price price) {
        if (!courseRepository.exists(price.getCourse().getId()) || !participantTypeRepository.exists(price.getParticipantType().getId()))
            return ResponseEntity.notFound().build();
        return ResponseEntity.created(new PriceResource(priceRepository.save(price)).getSelfRelURI()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PriceResource> getPrice(@PathVariable("id") Long id) {
        if (!priceRepository.exists(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new PriceResource(priceRepository.getOne(id)));
    }

    @PutMapping("{id}")
    public ResponseEntity updatePrice(@Valid @RequestBody Price price, @PathVariable("id") Long id) {
        if (!priceRepository.exists(id) ||
                !courseRepository.exists(price.getCourse().getId()) ||
                !participantTypeRepository.exists(price.getParticipantType().getId()))
            return ResponseEntity.notFound().build();
        try {
            Price one = priceRepository.findOne(id);
            one.setCourse(courseRepository.findOne(price.getCourse().getId()));
            one.setParticipantType(participantTypeRepository.findOne(price.getParticipantType().getId()));
            one.setPrice(price.getPrice());
            return ResponseEntity.ok(new PriceResource(priceRepository.save(one)));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletePrice(@PathVariable("id") Long id) {
        if (!priceRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            priceRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }
}
