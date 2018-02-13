package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course_type")
@EntityListeners(AuditingEntityListener.class)
public class CourseType {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String typeName;

    public CourseType() {
    }

    CourseType(String typeName) {
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
