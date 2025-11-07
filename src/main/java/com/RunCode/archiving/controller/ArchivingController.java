package com.RunCode.archiving.controller;

import com.RunCode.archiving.dto.ArchivingDetailRequest;
import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.dto.ArchivingSimpleResponse;
import com.RunCode.archiving.dto.ArchivingUpdateRequest;
import com.RunCode.archiving.service.ArchivingService;
import com.RunCode.common.domain.ApiResponse;
import com.RunCode.user.domain.CustomUserDetails;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archivings")
public class ArchivingController {

    private final ArchivingService archivingService;
    private final UserService userService;

    
    // 내 archiving 전체조회
    @GetMapping()
    public ResponseEntity<ApiResponse> readAllMyArchivings(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(name = "order") String order
            //String authHeader
    ){
        //Long userId = 1L;
        /*login 연동*/
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();
        List<ArchivingSimpleResponse> response = archivingService.readAllMyArchiving(userId, order);
        return ResponseEntity.ok(new ApiResponse(true, 200, "내 archiving 전체 조회 성공", response) );
    }

    // archiving 상세 조회
    @GetMapping("/{archivingId}")
    public ResponseEntity<ApiResponse> readArchiving(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long archivingId
    ){
        /*login 연동*/
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();
        ArchivingDetailResponse response = archivingService.readArchiving(archivingId, userId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "archiving 상세조회 성공", response) );
    }
    
    
    // archiving 생성
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createArchiving(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ArchivingDetailRequest req
    ) {
        /*login 연동*/
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();

        ArchivingSimpleResponse response = archivingService.createArchiving(req, userId);
        URI location = URI.create("/archivings/" + response.getArchiving_id());
        return ResponseEntity
                .created(location)
                .body((new ApiResponse(true,201,"archiving 생성 성공",response)));
    }

    // archiving수정
    @PatchMapping(value="/{archivingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateArchiving(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long archivingId,
            @RequestBody ArchivingUpdateRequest req
            //String authHeader
            ){

        //Long userId = 1L;
        /*login 연동*/
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();
        ArchivingDetailResponse response = archivingService.updateArchiving(archivingId, userId, req);

        return ResponseEntity.ok(
                new ApiResponse(true, 200, "archiving 수정 성공", response)
        );
    }


}
