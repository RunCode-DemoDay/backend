package com.RunCode.bookmark.service;

import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.bookmark.dto.BookmarkCreateRequest;
import com.RunCode.bookmark.dto.BookmarkCreateResponse;
import com.RunCode.bookmark.dto.BookmarkDeleteResponse;
import com.RunCode.bookmark.dto.BookmarkListResponse;
import com.RunCode.bookmark.repository.BookmarkRepository;
import com.RunCode.course.domain.Course;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    // bookmark 생성
    @Transactional
    public BookmarkCreateResponse createBookmark(Long userId, BookmarkCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("해당 코스를 찾을 수 없습니다."));

        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserAndCourse(user, course);
        // 이미 북마크가 있을 경우 중복 저장되지않고 그냥 반환만
        if (existingBookmark.isPresent()) {
            return BookmarkCreateResponse.of(existingBookmark.get());
        }
        else {
            Bookmark newBookmark = request.toEntity(user, course);

            Bookmark savedBookmark = bookmarkRepository.save(newBookmark);

            return BookmarkCreateResponse.of(savedBookmark);
        }
    }

    // bookmark 삭제
    @Transactional
    public BookmarkDeleteResponse deleteBookmark(Long userId, Long bookmarkId) {

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new EntityNotFoundException("해당 북마크를 찾을 수 없습니다."));

        if (!bookmark.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("해당 북마크를 삭제할 권한이 없습니다.");
        }

        BookmarkDeleteResponse response = BookmarkDeleteResponse.of(bookmark);

        bookmarkRepository.delete(bookmark);

        return response;
    }

    // 내가 생성한 bookmark 목록 조회
    public List<BookmarkListResponse> getMyBookmarks(Long userId, String order) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));

        Sort sort = toSort(order);

        List<Bookmark> bookmarks = bookmarkRepository.findByUserwithCourse(user, sort);

        return bookmarks.stream()
                .map(BookmarkListResponse::of)
                .toList();
    }

    private Sort toSort(String order) {
        if (order == null || order.isBlank() || order.equalsIgnoreCase("latest")) {
            return Sort.by(Sort.Direction.DESC, "date");  // 최신순
        }
        if (order.equalsIgnoreCase("oldest")) {
            return Sort.by(Sort.Direction.ASC, "date");   // 옛날순
        }
        return Sort.by(Sort.Direction.DESC, "date");      // 기본 최신순 - 파라미터 고정이어서 필요없긴 함(예외처리)
    }

}
