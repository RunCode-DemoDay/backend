package com.RunCode.archiving.controller;

import com.RunCode.archiving.dto.ArchivingDetailRequest;
import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.dto.ArchivingSimpleResponse;
import com.RunCode.archiving.service.ArchivingService;
import com.RunCode.common.domain.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archivings")
public class ArchivingController {

    private final ArchivingService archivingService;

    
    // 내 archiving 전체조회
    @GetMapping()
    public ResponseEntity<ApiResponse> readAllMyArchivings(
            @RequestParam(name = "order") String order
    ){
        List<ArchivingSimpleResponse> response = archivingService.readAllMyArchiving(1L, order);
        return ResponseEntity.ok(new ApiResponse(true, 200, "내 archiving 전체 조회 성공", response) );
    }

    // archiving 상세 조회
    @GetMapping("/{archivingId}")
    public ResponseEntity<ApiResponse> readArchiving(@PathVariable Long archivingId){
        ArchivingDetailResponse response = archivingService.readArchiving(archivingId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "archiving 상세조회 성공", response) );
    }
    
    
    // archiving 생성
    @PostMapping()
    public ResponseEntity<ApiResponse> createArchiving( @RequestBody ArchivingDetailRequest req) {
        ArchivingSimpleResponse response = archivingService.createArchiving(req);
        URI location = URI.create("/archivings/" + response.getArchiving_id());
        return ResponseEntity
                .created(location)
                .body((new ApiResponse(true,201,"archiving 생성 성공",response)));
    }


}
