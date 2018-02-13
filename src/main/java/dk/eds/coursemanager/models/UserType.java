package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "user_type")
@EntityListeners(AuditingEntityListener.class)
public class UserType {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String typeName;

    public UserType() {
    }

    public UserType(String typeName) {
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
