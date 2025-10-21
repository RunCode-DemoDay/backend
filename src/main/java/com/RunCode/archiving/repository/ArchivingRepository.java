package com.RunCode.archiving.repository;

import com.RunCode.archiving.domain.Archiving;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface ArchivingRepository extends JpaRepository<Archiving, Long> {

    @Query("""
            select ac from Archiving ac
            join fetch ac.course
            where ac.id = :id
            """)
    Optional<Archiving> findByIdWithCourse(@Param("id") Long id);

/*
    @Query("""
       select ac.id, ac.user.id, ac.course.id, ac.content
       from Archiving ac
       where ac.id = :id
    """)
    Object[] probe(@Param("id") Long id);

*/

}
