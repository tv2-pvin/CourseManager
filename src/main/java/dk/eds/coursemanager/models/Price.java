package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "price")
@EntityListeners(AuditingEntityListener.class)
public class Price {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Float price;

    @ManyToOne(fetch = FetchType.EAGER)
    private ParticipantType participantType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    public Price(Float price, ParticipantType participantType, Course course) {
        this.price = price;
        this.participantType = participantType;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
