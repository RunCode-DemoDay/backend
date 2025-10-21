package com.RunCode.archiving.controller;

import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.service.ArchivingService;
import com.RunCode.common.domain.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archivings")
public class ArchivingController {

    private final ArchivingService archivingService;

    @GetMapping("/archivingId")
    public ResponseEntity<ApiResponse> readArchiving(@PathVariable Long archivingId){
        ArchivingDetailResponse response = archivingService.readArchiving(archivingId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "archiving 상세조회 성공", response) );
    }


}
