package com.RunCode.aws.controller;

import com.RunCode.aws.service.AwsService;
import com.RunCode.common.domain.ApiResponse;
import com.RunCode.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AwsController {

    private final AwsService awsService;


    // Presigned URL 발급 (이미지 업로드용)
    @GetMapping("/upload-url")
    public ResponseEntity<ApiResponse> getUploadUrl(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("fileName") String fileName,
            @RequestParam("contentType") String contentType
    ) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }

        String url = awsService.getUploadImagePresignedUrl(fileName, contentType);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(true, 200, "업로드용 Presigned URL 발급 성공", url));
    }

    // Presigned URL 발급 (이미지 조회용)
    @GetMapping("/download-url/{fileName}")
    public ResponseEntity<ApiResponse> getDownloadUrl(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String fileName
    ) {

        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }

        String url = awsService.getDownloadPresignedUrl(fileName);

        return ResponseEntity
                .ok(new ApiResponse(true, 200, "조회용 Presigned URL 발급 성공", url));
    }
}