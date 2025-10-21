package com.RunCode.archiving.repository;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.archiving.domain.Lap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface LapRepository extends JpaRepository<Lap, Long> {

    List<Lap> findByArchivingIdOrderByLapNumber(Long archivingId);
}
