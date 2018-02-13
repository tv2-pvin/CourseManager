package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTypeRepository extends JpaRepository<CourseType, Long> {
}
