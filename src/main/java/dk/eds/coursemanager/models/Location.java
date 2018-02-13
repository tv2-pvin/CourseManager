package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
@EntityListeners(AuditingEntityListener.class)
public class Location {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String roadName;
    @Column(nullable = false)
    private int roadNumber;
    private String roadNumberDetail;
    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    public Location() {
    }

    public Location(String roadName, int roadNumber, String roadNumberDetail, City city) {
        this.roadName = roadName;
        this.roadNumber = roadNumber;
        this.roadNumberDetail = roadNumberDetail;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public int getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(int roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getRoadNumberDetail() {
        return roadNumberDetail;
    }

    public void setRoadNumberDetail(String roadNumberDetail) {
        this.roadNumberDetail = roadNumberDetail;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
