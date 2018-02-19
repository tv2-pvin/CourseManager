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
    private Person participant;
    @ManyToOne(fetch = FetchType.EAGER)
    private ParticipantType participantType;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    public Participant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getParticipant() {
        return participant;
    }

    public void setParticipant(Person participant) {
        this.participant = participant;
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
