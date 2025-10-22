package com.RunCode.archiving.service;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.archiving.domain.Lap;
import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.dto.ArchivingDetailRequest;
import com.RunCode.archiving.dto.ArchivingSimpleResponse;
import com.RunCode.archiving.dto.LapRequest;

import com.RunCode.course.domain.Course;
import com.RunCode.user.domain.User;
import com.RunCode.archiving.repository.LapRepository;

import com.RunCode.archiving.repository.ArchivingRepository;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivingService {
    private final ArchivingRepository archivingRepository;
    private final LapRepository lapRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    // archiving 상세 조회
    @Transactional(readOnly = true)
    public ArchivingDetailResponse readArchiving(long archivingId){

        Archiving archiving = archivingRepository.findById(archivingId)
                .orElseThrow(()-> new EntityNotFoundException(" 해당 id 의 archiving을 찾을 수 없습니다. "));

        List<Lap> laps = lapRepository.findByArchivingIdOrderByLapNumber(archivingId);

        return ArchivingDetailResponse.of(archiving, laps);
    }

    //archiving 생성
    @Transactional
    public ArchivingSimpleResponse createArchiving(ArchivingDetailRequest req){

        //requestBody값 확인
        if (req.getCourse_id() == null){
            throw new IllegalArgumentException("course_id가 필요합니다.");
        }
        if (req.getLaps() == null || req.getLaps().isEmpty()){
            throw new IllegalArgumentException("lap(구간기록)은 최소 1개 이상이어야 합니다.");
        }
        if (req.getLaps().size()>42){
            throw new IllegalArgumentException("laps(구간기록)은 최대 42개까지만 허용됩니다.");
        }

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("id=1 사용자를 찾을 수 없습니다."));

        Course course = courseRepository.findById(req.getCourse_id())
                .orElseThrow(()-> new EntityNotFoundException("해당 id의 course를 찾을 수 없습니다."));

        // archiving 생성g
        Archiving ac = req.toEntity(course, user);
        Archiving archiving = archivingRepository.save(ac);

        List<Lap> laps = req.getLaps().stream()
                .map(dto -> dto.toEntity(archiving))
                .toList();


        lapRepository.saveAll(laps);
        return ArchivingSimpleResponse.of(archiving);
    }

}
