package com.RunCode.course.repository;

import com.RunCode.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("""
        select distinct c
        from Course c
        join Archiving a on a.course = c
        left join fetch c.locations l
        where a.user.id = :userId
          and l.locationType = com.RunCode.location.domain.LOCATIONTYPE.START
    """)
    List<Course> findAllArchivedByUserWithStart(@Param("userId") Long userId);
}
