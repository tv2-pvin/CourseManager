package dk.eds.coursemanager.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking")
@EntityListeners(AuditingEntityListener.class)
public class Booking {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromTime;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date toTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;
    @ManyToOne(fetch = FetchType.EAGER)
    private Person booker;

    public Booking(Date fromTime, Date toTime, Course course, Room room, Person booker) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.course = course;
        this.room = room;
        this.booker = booker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Person getBooker() {
        return booker;
    }

    public void setBooker(Person booker) {
        this.booker = booker;
    }
}
