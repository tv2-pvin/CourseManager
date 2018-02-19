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
    private float discountAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    private ParticipantType participantType;

    @ManyToOne(fetch = FetchType.EAGER)
    private CourseType courseType;

    public Discount(int discountTrigger, float discountAmount, ParticipantType participantType, CourseType courseType) {
        this.discountTrigger = discountTrigger;
        this.discountAmount = discountAmount;
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

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
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
