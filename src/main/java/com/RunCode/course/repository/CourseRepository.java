package com.RunCode.course.repository;

import com.RunCode.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    // 로그인한 사용자가 아카이빙은 했지만 리뷰를 아직 작성하지 않은 리뷰 미작성 코스 목록 조회
    // @Query(value = "SELECT c, CASE WHEN b.id IS NULL THEN FALSE ELSE TRUE END " + // 북마크 존재 여부를 boolean으로 반환
    //         "FROM Archiving a " +
    //         "JOIN a.course c " +
    //         "LEFT JOIN Bookmark b ON b.course.id = c.id AND b.user.id = a.user.id " +
    //         "WHERE a.user.id = :userId " +
    //         "AND NOT EXISTS (" +
    //         "    SELECT 1 FROM Review r " +
    //         "    WHERE r.course.id = c.id AND r.user.id = a.user.id" +
    //         ")")

    // course review 미작성 목록 조회 시에 course3개씩 보이는 이슈 수정
    @Query("""
            SELECT DISTINCT c,
                  CASE WHEN EXISTS (
                      SELECT 1 FROM Bookmark b
                      WHERE b.course = c
                        AND b.user.id = :userId
                  ) THEN TRUE ELSE FALSE END
            FROM Course c
            WHERE EXISTS (
                SELECT 1 FROM Archiving a
                WHERE a.course = c
                  AND a.user.id = :userId
            )
              AND NOT EXISTS (
                SELECT 1 FROM Review r
                WHERE r.course = c
                  AND r.user.id = :userId
            )
          """)
    List<Object[]> findUnreviewedCourseEntitiesByUserId(@Param("userId") Long userId);

/*    @Query("""
        select distinct c
        from Course c
        join Archiving a on a.course = c
        left join fetch c.locations l
        where a.user.id = :userId
          and l.locationType = com.RunCode.location.domain.LOCATIONTYPE.START
    """)*/

    @Query("""
    select distinct c
    from Course c
    left join fetch c.locations l
    where exists (
        select 1 from Archiving a
        where a.course = c
          and a.user.id = :userId
    )
      and (l is null or l.locationType = com.RunCode.location.domain.LOCATIONTYPE.START)
""")
    List<Course> findAllArchivedByUserWithStart(@Param("userId") Long userId);
}
