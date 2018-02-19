package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Discount;
import dk.eds.coursemanager.repositories.CourseTypeRepository;
import dk.eds.coursemanager.repositories.DiscountRepository;
import dk.eds.coursemanager.repositories.ParticipantTypeRepository;
import dk.eds.coursemanager.resources.DiscountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/discounts")
public class DiscountController {

    private final DiscountRepository discountRepository;
    private final ParticipantTypeRepository participantTypeRepository;
    private final CourseTypeRepository courseTypeRepository;

    @Autowired
    public DiscountController(DiscountRepository discountRepository, ParticipantTypeRepository participantTypeRepository, CourseTypeRepository courseTypeRepository) {
        this.discountRepository = discountRepository;
        this.participantTypeRepository = participantTypeRepository;
        this.courseTypeRepository = courseTypeRepository;
    }

    @GetMapping
    public List<DiscountResource> getAllDiscounts(@RequestParam(value = "course-type-id", required = false) Long courseTypeId,
                                                  @RequestParam(value = "participant-type-id", required = false) Long participantTypeId) {
        List<Discount> discounts;
        if (courseTypeId == null && participantTypeId == null)
            discounts = discountRepository.findAll();
        else if (courseTypeId != null && participantTypeId == null)
            discounts = discountRepository.getDiscountsByCourseType_Id(courseTypeId);
        else if (courseTypeId == null)
            discounts = discountRepository.getDiscountsByParticipantType_Id(participantTypeId);
        else
            discounts = discountRepository.getDiscountsByCourseType_IdAndParticipantType_Id(courseTypeId, participantTypeId);
        return discounts.stream().map(DiscountResource::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<DiscountResource> createDiscount(@Valid @RequestBody Discount discount) {
        if (!participantTypeRepository.exists(discount.getParticipantType().getId()) || !courseTypeRepository.exists(discount.getCourseType().getId()))
            return ResponseEntity.notFound().build();
        return ResponseEntity.created(new DiscountResource(discountRepository.save(discount)).getSelfRelURI()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<DiscountResource> getDiscount(@PathVariable("id") Long id) {
        if (!discountRepository.exists(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DiscountResource(discountRepository.findOne(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DiscountResource> deleteDiscount(@PathVariable("id") Long id) {
        if (!discountRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            discountRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<DiscountResource> updateDiscount(@PathVariable("id") Long id, @Valid @RequestBody Discount update) {
        if (!discountRepository.exists(id) ||
                !participantTypeRepository.exists(update.getParticipantType().getId()) ||
                !courseTypeRepository.exists(update.getCourseType().getId()))
            return ResponseEntity.notFound().build();
        try {
            Discount discount = discountRepository.findOne(id);
            discount.setCourseType(courseTypeRepository.findOne(update.getCourseType().getId()));
            discount.setParticipantType(participantTypeRepository.findOne(update.getParticipantType().getId()));
            discount.setDiscountAmount(update.getDiscountAmount());
            discount.setDiscountTrigger(update.getDiscountTrigger());
            return ResponseEntity.ok(new DiscountResource(discountRepository.save(discount)));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }
}
