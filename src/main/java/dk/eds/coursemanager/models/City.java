package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "city")
@EntityListeners(AuditingEntityListener.class)
public class City {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private int zipCode;
    @Column(nullable = false)
    private String name;

    public City() {
    }

    public City(int zipCode, String name) {
        this.zipCode = zipCode;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
