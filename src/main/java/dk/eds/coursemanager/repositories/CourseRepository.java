package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.Course;
import dk.eds.coursemanager.models.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByCourseType(CourseType type);

    List<Course> findAllByCourseType_Id(Long id);
}
