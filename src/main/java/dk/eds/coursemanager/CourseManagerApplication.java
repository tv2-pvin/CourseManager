package dk.eds.coursemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EntityScan
@EnableWebSecurity
public class CourseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseManagerApplication.class, args);
    }
}
