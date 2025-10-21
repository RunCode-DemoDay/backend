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
            join fetch ac.course c
            where ac.id = :id
            """)
    Optional<Archiving> findByIdWithCourse(@Param("id") Long id);

}
