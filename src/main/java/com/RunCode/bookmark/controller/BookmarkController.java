package com.RunCode.bookmark.controller;

import com.RunCode.bookmark.dto.BookmarkCreateRequest;
import com.RunCode.bookmark.dto.BookmarkCreateResponse;
import com.RunCode.bookmark.dto.BookmarkDeleteResponse;
import com.RunCode.bookmark.dto.BookmarkListResponse;
import com.RunCode.bookmark.service.BookmarkService;
import com.RunCode.common.domain.ApiResponse;

import com.RunCode.user.domain.CustomUserDetails;
import com.RunCode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // bookmark 생성
    @PostMapping()
    public ResponseEntity<ApiResponse> createBookmark(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody BookmarkCreateRequest request) {

        Long userId = userDetails.getUserId();

        BookmarkCreateResponse response = bookmarkService.createBookmark(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, 201, "북마크 생성 성공", response));
    }

    // bookmark 삭제
    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<ApiResponse> deleteBookmark(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long bookmarkId) {

        Long userId = userDetails.getUserId();

        BookmarkDeleteResponse response = bookmarkService.deleteBookmark(userId, bookmarkId);

        return ResponseEntity
                .ok(new ApiResponse(true, 200, "북마크 삭제 성공", response));
    }

    // 내가 생성한 bookmark 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse> getMyBookmarks(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(name = "order", defaultValue = "latest") String order) {

        Long userId = userDetails.getUserId();

        List<BookmarkListResponse> responseList = bookmarkService.getMyBookmarks(userId, order);

        return ResponseEntity
                .ok(new ApiResponse(true, 200, "북마크 목록 조회 성공", responseList));
    }
}
