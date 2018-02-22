package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "participant_type")
@EntityListeners(AuditingEntityListener.class)
public class ParticipantType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public ParticipantType() {
    }

    ParticipantType(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
