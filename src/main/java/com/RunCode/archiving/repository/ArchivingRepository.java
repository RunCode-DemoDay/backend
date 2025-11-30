package com.RunCode.archiving.repository;

import com.RunCode.archiving.domain.Archiving;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Repository;

@Repository
public interface ArchivingRepository extends JpaRepository<Archiving, Long> {
    @EntityGraph(attributePaths = "course")
    List<Archiving> findByUserId(Long userId, Sort sort);

    @EntityGraph(attributePaths = "course")
    List<Archiving> findByUserIdAndCourseId(Long userId, Long courseId);

    @EntityGraph(attributePaths = {"course", "laps"})
    Optional<Archiving> findById(Long id);

    @EntityGraph(attributePaths = {"course", "laps"})
    Optional<Archiving> findByIdAndUserId(Long id, Long userId);

    @Query("""
            select ac from Archiving ac
            join fetch ac.course
            where ac.id = :id
            """)
    Optional<Archiving> findByIdWithCourse(@Param("id") Long id);

    // archiving 있는지
    boolean existsByUserId(Long userId);

/*
    @Query("""
       select ac.id, ac.user.id, ac.course.id, ac.content
       from Archiving ac
       where ac.id = :id
    """)
    Object[] probe(@Param("id") Long id);

*/

}
