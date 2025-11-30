package com.RunCode.archiving.service;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.archiving.domain.Lap;
import com.RunCode.archiving.dto.*;

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
import org.springframework.data.domain.Sort;
import java.util.List;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivingService {
    private final ArchivingRepository archivingRepository;
    private final LapRepository lapRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    /*archiving 없으면 코스21번 에 archiving 1개 임시 생성*/
    private static final Long DEMO_COURSE_IDS = 21L;

    @Transactional
    public ArchivingSimpleResponse initDemoArchivingsIfEmpty(Long userId) {
        boolean exists = archivingRepository.existsByUserId(userId);
        if (exists) {
            throw new RuntimeException("이미 아카이빙이 존재하는 사용자입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 사용자를 찾을 수 없습니다."));
        Course course = courseRepository.findById(DEMO_COURSE_IDS)
            .orElseThrow(()-> new EntityNotFoundException("해당 id의 course를 찾을 수 없습니다."));

        // archiving 생성1
        ArchivingDetailRequest demoRequest = ArchivingDetailRequest.builder()
            .user_id(user.getId())
            .course_id(course.getId())
            .title("[DEMO] " + course.getTitle() + " 체험 러닝")
            .content("runcord 첫 실행! 공덕역 러닝할 생각에 떨립니다 ㅎㅎ")
            .thumbnail("https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/archivings/solo_track_2.jpg")
            .detailImage("https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/archivings/exercise_2.jpg")
            .distance(1.00)
            .calorie(50)
            .average_pace("5'45\"")
            .time("1:30")
            .altitude(90)
            .cadence(50)
            .date(LocalDate.now())
            // laps는 필요하면 넣고, 아니면 null 허용
            .build();
        
        Archiving archiving = demoRequest.toEntity(course, user);
        Archiving saved = archivingRepository.save(archiving);
        return ArchivingSimpleResponse.of(saved);

    }


    /* archiving 전체 조회*/
    // 1. 코스별 archiving 목록
    public List<ArchivingSummaryResponse> readAllArchivingByCourse(Long courseId, Long userId){
        List<ArchivingSummaryResponse> archivings = archivingRepository.findByUserIdAndCourseId(userId, courseId)
                .stream().map(ac -> ArchivingSummaryResponse.of(ac))
                .toList();

        return archivings;
    }

    // 2. 내 archiving 목록
    public List<ArchivingSimpleResponse> readAllMyArchiving(Long userId, String order){
        Sort sort = toSort(order);
        List<ArchivingSimpleResponse> archivings = archivingRepository.findByUserId(userId, sort)
                .stream().map(ac -> ArchivingSimpleResponse.of(ac))
                .toList();

        return archivings;

    }


    // archiving 상세 조회
    @Transactional(readOnly = true)
    public ArchivingDetailResponse readArchiving(long archivingId, long userId){

        Archiving archiving = archivingRepository.findByIdAndUserId(archivingId, userId)
                .orElseThrow(()-> new EntityNotFoundException(" 해당 id 의 archiving을 찾을 수 없습니다. "));

        //List<Lap> laps = lapRepository.findByArchivingIdOrderByLapNumber(archivingId);

        return ArchivingDetailResponse.of(archiving);
    }

    //archiving 생성
    @Transactional
    public ArchivingSimpleResponse createArchiving(ArchivingDetailRequest req, Long userId){

        //requestBody값 확인
        if (req.getCourse_id() == null){
            throw new IllegalArgumentException("course_id가 필요합니다.");
        }
        // if (req.getLaps() == null || req.getLaps().isEmpty()){
        //     throw new IllegalArgumentException("lap(구간기록)은 최소 1개 이상이어야 합니다.");
        // }
        if (req.getLaps().size()>42){
            throw new IllegalArgumentException("laps(구간기록)은 최대 42개까지만 허용됩니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 사용자를 찾을 수 없습니다."));

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

    // archiving 수정
    @Transactional
    public ArchivingDetailResponse updateArchiving(Long archivingId, Long userId, ArchivingUpdateRequest req){
        Archiving archiving = archivingRepository.findByIdAndUserId(archivingId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 id의 archiving을 찾을 수 없습니다."));

        if (req.getTitle() == null && req.getContent()==null){
            throw new IllegalArgumentException("잘못된 request 형식입니다.");
        }

        if(req.getTitle()!=null ) {archiving.setTitle(req.getTitle());}
        if(req.getContent()!= null) {archiving.setContent(req.getContent());}

        return ArchivingDetailResponse.of(archiving);
    }


    private Sort toSort(String order) {
        if (order == null || order.isBlank() || order.equalsIgnoreCase("latest")) {
            return Sort.by(Sort.Direction.DESC, "createdAt");  // 최신순
        }
        if (order.equalsIgnoreCase("oldest")) {
            return Sort.by(Sort.Direction.ASC, "createdAt");   // 옛날순
        }
        return Sort.by(Sort.Direction.DESC, "createdAt");      // 기본 최신순 - 파라미터 고정이어서 필요없긴 함(예외처리)
    }

}
