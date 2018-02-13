package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private CourseType courseType;

    public Course(String name, CourseType courseType) {
        this.name = name;
        this.courseType = courseType;
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

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
