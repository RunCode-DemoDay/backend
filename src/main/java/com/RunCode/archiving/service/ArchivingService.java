package com.RunCode.archiving.service;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.archiving.domain.Lap;
import com.RunCode.archiving.dto.LapDto;
import com.RunCode.archiving.repository.LapRepository;
import com.RunCode.course.dto.CourseSimpleReponse;

import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.repository.ArchivingRepository;
import com.RunCode.course.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivingService {
    private final ArchivingRepository archivingRepository;
    private final LapRepository lapRepository;

    // archiving 상세 조회
    public ArchivingDetailResponse readArchiving(long archivingId){

        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(()-> new EntityNotFoundException(" 해당 id 의 archiving을 찾을 수 없습니다. "));

        List<Lap> laps = lapRepository.findByArchivingIdOrderByLapNumber(archivingId);


        return ArchivingDetailResponse.of(archiving, laps);

    }



}
