package com.RunCode.archiving.repository;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivingRepository extends JpaRepository<Archiving, Long> {

}
