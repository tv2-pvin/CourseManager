package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "discount")
@EntityListeners(AuditingEntityListener.class)
public class Discount {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int discountTrigger;

    @Column(nullable = false)
    private float discount_amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private ParticipantType participantType;

    @ManyToOne(fetch = FetchType.EAGER)
    private CourseType courseType;

    public Discount(int discountTrigger, float discount_amount, ParticipantType participantType, CourseType courseType) {
        this.discountTrigger = discountTrigger;
        this.discount_amount = discount_amount;
        this.participantType = participantType;
        this.courseType = courseType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDiscountTrigger() {
        return discountTrigger;
    }

    public void setDiscountTrigger(int discountTrigger) {
        this.discountTrigger = discountTrigger;
    }

    public float getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(float discount_amount) {
        this.discount_amount = discount_amount;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
