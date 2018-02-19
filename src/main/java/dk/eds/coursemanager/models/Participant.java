package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "participant")
@EntityListeners(AuditingEntityListener.class)
public class Participant {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;
    @ManyToOne(fetch = FetchType.EAGER)
    private ParticipantType participantType;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    public Participant() {
    }

    public Participant(Person person, ParticipantType participantType, Course course) {
        this.person = person;
        this.participantType = participantType;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
