-- 순서 맞추기: 자식 테이블부터 drop!
-- DROP TABLE IF EXISTS laps;
-- DROP TABLE IF EXISTS archivings;
-- DROP TABLE IF EXISTS locations;
-- DROP TABLE IF EXISTS bookmarks;
-- DROP TABLE IF EXISTS reviews;
-- DROP TABLE IF EXISTS course_tags;
-- DROP TABLE IF EXISTS type_tags;
-- DROP TABLE IF EXISTS course_detail_image;
-- DROP TABLE IF EXISTS courses;
-- DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS tags;
-- DROP TABLE IF EXISTS types;

-- -- 1. 러닝 타입
-- CREATE TABLE types (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     name VARCHAR(100) NOT NULL,
--     description TEXT,
--     thumbnail VARCHAR(255)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 2. 태그
-- CREATE TABLE tags (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     name VARCHAR(100) NOT NULL
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 3. 유저
-- CREATE TABLE users (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     kakao_id VARCHAR(50) NOT NULL UNIQUE,
--     name VARCHAR(100),
--     profile_image VARCHAR(255),
--     nickname VARCHAR(100),
--     type_id INT,
--     CONSTRAINT fk_users_type
--         FOREIGN KEY (type_id) REFERENCES types(id)
--         ON DELETE SET NULL
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 4. 코스 본문
-- CREATE TABLE courses (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     user_id INT NOT NULL,
--     title VARCHAR(150) NOT NULL,
--     content TEXT,
--     address VARCHAR(255),
--     distance DECIMAL(5,1),                  -- 예: 12.0
--     distance_description VARCHAR(255),
--     thumbnail VARCHAR(255),
--     star_average DECIMAL(3,1) DEFAULT 0.0,  -- 예: 4.0
--     review_count INT DEFAULT 0,
--     CONSTRAINT fk_courses_user
--         FOREIGN KEY (user_id) REFERENCES users(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 5. 코스 상세 이미지
-- CREATE TABLE course_detail_image (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     course_id INT NOT NULL,
--     image_url VARCHAR(255) NOT NULL,
--     CONSTRAINT fk_course_detail_image_course
--         FOREIGN KEY (course_id) REFERENCES courses(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 6. 코스-태그 매핑
-- CREATE TABLE course_tags (
--     course_id INT NOT NULL,
--     tag_id INT NOT NULL,
--     PRIMARY KEY (course_id, tag_id),
--     CONSTRAINT fk_course_tags_course
--         FOREIGN KEY (course_id) REFERENCES courses(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE,
--     CONSTRAINT fk_course_tags_tag
--         FOREIGN KEY (tag_id) REFERENCES tags(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 7. 타입-태그 매핑
-- CREATE TABLE type_tags (
--     type_id INT NOT NULL,
--     tag_id INT NOT NULL,
--     PRIMARY KEY (type_id, tag_id),
--     CONSTRAINT fk_type_tags_type
--         FOREIGN KEY (type_id) REFERENCES types(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE,
--     CONSTRAINT fk_type_tags_tag
--         FOREIGN KEY (tag_id) REFERENCES tags(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 8. 위치(코스 시작점 등)
-- CREATE TABLE locations (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     latitude DECIMAL(9,6) NOT NULL,
--     longitude DECIMAL(9,6) NOT NULL,
--     location_type VARCHAR(20) NOT NULL,
--     course_id INT NOT NULL,
--     CONSTRAINT fk_locations_course
--         FOREIGN KEY (course_id) REFERENCES courses(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 9. 아카이빙(러닝 기록)
-- CREATE TABLE archivings (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     user_id INT NOT NULL,
--     course_id INT NOT NULL,
--     date DATE NOT NULL,
--     title VARCHAR(150),
--     content TEXT,
--     thumbnail VARCHAR(255),
--     detail_image VARCHAR(255),
--     distance DECIMAL(5,1),
--     calorie INT,
--     average_pace VARCHAR(20),
--     time VARCHAR(20),
--     altitude INT,
--     cadence INT,
--     CONSTRAINT fk_archivings_user
--         FOREIGN KEY (user_id) REFERENCES users(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE,
--     CONSTRAINT fk_archivings_course
--         FOREIGN KEY (course_id) REFERENCES courses(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 10. 랩 기록
-- CREATE TABLE laps (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     archiving_id INT NOT NULL,
--     lap_number INT NOT NULL,
--     average_pace VARCHAR(20),
--     pace_variation VARCHAR(20),
--     altitude INT,
--     CONSTRAINT fk_laps_archiving
--         FOREIGN KEY (archiving_id) REFERENCES archivings(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 11. 리뷰
-- CREATE TABLE reviews (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     user_id INT NOT NULL,
--     course_id INT NOT NULL,
--     star DECIMAL(2,1) NOT NULL,
--     content TEXT,
--     created_at DATETIME NOT NULL,
--     updated_at DATETIME NOT NULL,
--     CONSTRAINT fk_reviews_user
--         FOREIGN KEY (user_id) REFERENCES users(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE,
--     CONSTRAINT fk_reviews_course
--         FOREIGN KEY (course_id) REFERENCES courses(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -- 12. 북마크
-- CREATE TABLE bookmarks (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     user_id INT NOT NULL,
--     course_id INT NOT NULL,
--     CONSTRAINT fk_bookmarks_user
--         FOREIGN KEY (user_id) REFERENCES users(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE,
--     CONSTRAINT fk_bookmarks_course
--         FOREIGN KEY (course_id) REFERENCES courses(id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- TRUNCATE TABLE types RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE users RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE tags RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE courses RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE course_tags RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE type_tags RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE locations RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE archivings RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE laps RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE reviews RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE bookmarks RESTART IDENTITY CASCADE;
-- TRUNCATE TABLE course_detail_image RESTART IDENTITY CASCADE;

INSERT INTO types (name, description, thumbnail) VALUES ('새벽솔로 도전자', '아침의 고요 속에서 혼자 달리며 기록에 집중하는 타입.\n꾸준한 루틴과 성취감을 통해 성장하는 러너.', 'https://img.example.com/type_1.png'); 
INSERT INTO types (name, description, thumbnail) VALUES ('아침 팀 마라토너', '팀과 함께 아침 러닝으로 하루를 여는 타입.\n동료와의 협력 속에서 목표 달성에 힘을 얻는다.', 'https://img.example.com/type_2.png');
INSERT INTO types (name, description, thumbnail) VALUES ('야간 기록 추격자', '저녁마다 홀로 기록을 갱신하려는 도전적인 타입\n어둠 속에서 자신과의 경쟁을 즐기는 러너.', 'https://img.example.com/type_3.png');
INSERT INTO types (name, description, thumbnail) VALUES ('저녁 러닝 클럽 리더', '퇴근 후 크루와 함께 달리며 목표를 향하는 타입.\n서로를 이끌며 팀워크로 성취를 만들어간다.', 'https://img.example.com/type_4.png');
INSERT INTO types (name, description, thumbnail) VALUES ('즉흥 새벽 질주러', '새벽 공기 속에서 즉흥적으로 달리기를 즐기는 타입.\n순간의 기분과 자유로움을 중시하는 러너.', 'https://img.example.com/type_5.png');
INSERT INTO types (name, description, thumbnail) VALUES ('팀과 함께 즐기는 아침 스프린터', '아침에 모여 짧고 강렬한 달리기를 즐기는 타입.\n성취와 재미를 동시에 추구하는 활기찬 러너.', 'https://img.example.com/type_6.png');
INSERT INTO types (name, description, thumbnail) VALUES ('퇴근 후 기록 도전자', '퇴근 후 즉흥적으로 달리지만 기록 욕심이 있는 타입.\n유연하면서도 끊임없이 한계를 시험한다.', 'https://img.example.com/type_7.png');
INSERT INTO types (name, description, thumbnail) VALUES ('야간 즉흥 러닝 메이트', '밤에 친구들과 즉흥 러닝을 즐기는 타입.\n기록보다 분위기와 에너지를 우선시한다.', 'https://img.example.com/type_8.png');
INSERT INTO types (name, description, thumbnail) VALUES ('루틴형 아침 힐링러', '아침마다 정해진 루틴으로 마음을 다스리는 타입.\n달리기를 작은 명상처럼 여기는 러너.', 'https://img.example.com/type_9.png');
INSERT INTO types (name, description, thumbnail) VALUES ('아침 공원 러닝 메이트', '공원에서 함께 달리며 활기를 얻는 타입.\n교류와 웃음 속에서 힐링을 찾는다.', 'https://img.example.com/type_10.png');
INSERT INTO types (name, description, thumbnail) VALUES ('저녁 루틴 산책러', '저녁 시간에 가볍게 달리며 하루를 정리하는 타입.\n차분하게 자신만의 시간을 즐기는 러너.', 'https://img.example.com/type_10.png');
INSERT INTO types (name, description, thumbnail) VALUES ('퇴근 후 팀 러너', '퇴근 후 동료들과 함께 달리며 스트레스를 풀어내는 타입.\n함께 땀 흘리며 회복을 경험한다.', 'https://img.example.com/type_10.png');
INSERT INTO types (name, description, thumbnail) VALUES ('기분파 아침 러너', '아침의 기분에 따라 자유롭게 달리는 타입.\n속도와 거리보다 그날의 감각을 중시한다.', 'https://img.example.com/type_10.png');
INSERT INTO types (name, description, thumbnail) VALUES ('함께하는 감성 새벽 러너', '새벽 공기와 분위기를 함께 느끼며 달리는 타입.\n교감을 통해 러닝의 즐거움을 더한다.', 'https://img.example.com/type_10.png');
INSERT INTO types (name, description, thumbnail) VALUES ('노을 감상 야간 러너', '노을을 감상하며 천천히 달리는 타입.\n풍경 속에서 힐링을 찾는 감성적인 러너.', 'https://img.example.com/type_10.png');
INSERT INTO types (name, description, thumbnail) VALUES ('저녁 즉흥 러닝 메이트', '저녁에 즉흥적으로 모여 즐겁게 달리는 타입.\n기록보다 대화와 웃음을 우선시한다.', 'https://img.example.com/type_10.png');

INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao001', '김가윤', 'https://img.example.com/avatar_1.jpg', 'runner01', 2);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao002', '김유이', 'https://img.example.com/avatar_2.jpg', 'runner02', 3);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao003', '김현중', 'https://img.example.com/avatar_3.jpg', 'runner03', 4);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao004', '노현호', 'https://img.example.com/avatar_4.jpg', 'runner04', 5);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao005', '송명은', 'https://img.example.com/avatar_5.jpg', 'runner05', 6);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao006', '엄해윤', 'https://img.example.com/avatar_6.jpg', 'runner06', 7);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao007', '박지윤', 'https://img.example.com/avatar_7.jpg', 'runner07', 8);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao008', '윤도운', 'https://img.example.com/avatar_8.jpg', 'runner08', 9);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao009', '조승연', 'https://img.example.com/avatar_9.jpg', 'runner09', 10);
INSERT INTO users (kakao_id, name, profile_image, nickname, type_id) VALUES ('kakao010', '하현상', 'https://img.example.com/avatar_10.jpg', 'runner10', 1);

INSERT INTO tags (name) VALUES ('아침런'); -- S
INSERT INTO tags (name) VALUES ('기록 지향'); -- G
INSERT INTO tags (name) VALUES ('솔로런'); -- M
INSERT INTO tags (name) VALUES ('성취감추구'); -- P
INSERT INTO tags (name) VALUES ('저녁런'); -- N
INSERT INTO tags (name) VALUES ('엔조이'); -- H
INSERT INTO tags (name) VALUES ('단체런'); -- T
INSERT INTO tags (name) VALUES ('풍경 즐기기'); -- F

INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '한강 반포 러닝 코스', '아침 햇살과 강바람을 맞으며 기록에 집중하기 좋은 한강 대표 코스', '서울시 서초구 반포동 (고속터미널역 8-1 출구 도보 10분)', 5.7, '약 5km (반포대교-잠수교 순환)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course1_banpo.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (2, '한강 여의도 러닝 코스', '여의도공원과 샛강 생태공원을 포함한 강변 평탄 구간', '서울시 영등포구 여의도동 (여의도한강공원 일대)', 6.4, '약 5 km (여의도 샛강생태공원 루프 포함)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course2_yeouido.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (3, '청계천 도심 러닝 코스', '도심 속 하천 따라 시원하게 달릴 수 있는 도심로 코스', '서울시 종로구 수표동 (청계광장 인근)', 7.1, '약 3-4 km (청계광장에서 동쪽방향 왕복)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course3_cheonggye.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (4, '양재천 물길 러닝 코스', '강남·서초와 이어지는 나무 그늘 아래 평탄한 스트림 코스', '서울시 서초구 양재동 ~ 강남구 일대 (양재천변)', 7.8, '서울시 서초구 양재동 ~ 강남구 일대 (양재천변)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course4_yangjae.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (5, '서울둘레길 외곽 러닝 코스', '서울 외곽 자연과 마을길을 잇는 트레일 느낌의 긴 코스', '서울시 외곽 산자락 일대 (예: 남양주시경계 등)', 8.5, '약 8-12 km (서울둘레길 1개 구간)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course5_dulegil.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (6, '뚝섬-광진교 한강 러닝 코스', '한강변 자전거길을 공유하며 강변 풍경 따라 달리기 좋은 구간', '서울시 광진구 뚝섬유원지역 인근 (뚝섬유원지 출발)', 9.2, '약 7.9 km (뚝섬유원지-광진교 구간)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course6_ttukseom.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (7, '북악산 성곽길 러닝 코스', '도심과 산이 만나는 곳에서 기분 전환하며 달릴 수 있는 코스', '서울시 종로구 부암동 일대 (북악산 성곽길 시작점)', 9.9, '약 10 km 내외 (성곽길 구간)', 'hhttps://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course7_bugaksan.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '올림픽공원 조깅 코스', '1988올림픽 메모리얼 공간에서 평탄하고 잘 정비된 트랙', '서울시 송파구 올림픽로 424 (올림픽공원 내부)', 10.6, '약 4 km (공원 내부 루프)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course8_olympic.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '월드컵공원 하늘공원 러닝 코스', '도시 속 녹지와 계단을 섞어 달리기 강화되는 기분 좋은 트레일', '서울시 마포구 하늘공원로 95 (월드컵공원 하늘공원)', 11.3, '약 3-5 km (공원 내부 루프)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course9_worldcup.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '안양천 강변 러닝 코스', '강변 나무 그늘 아래 꾸준히 달리기 좋은 강변 산책로', '서울시 구로구 구로동 ~ 양천구 일대 (안양천 산책로)', 12.0, '약 5-6 km (안양천 산책·러닝 구간)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course10_anyang.jpg', 0.0, 0);

-- 추가된 10개의 course
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (2, '송도 센트럴파크 수변런', '수로와 공원이 조화로운 인천 송도 수변 러닝 코스', '인천광역시 연수구 컨벤시아대로 160 (센트럴파크역 인근)', 6.8, '약 6-7 km (센트럴파크 일주 루프)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course11_songdo.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (3, '부산 광안리 해변 러닝', '바다 냄새를 느끼며 시원하게 달릴 수 있는 광안리 해변 코스', '부산광역시 수영구 광안해변로 (광안리해수욕장 일대)', 5.9, '약 5 km (광안리해변 왕복)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course12_busan.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (4, '대전 갑천 자전거도로 런', '강변 풍경을 따라 꾸준히 달릴 수 있는 평탄한 하천 코스', '대전광역시 유성구 어은동 갑천변', 8.1, '약 7-8 km (갑천변 러닝 구간)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course13_daejeon.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (5, '광교호수공원 순환 러닝', '호수 풍경과 나무 데크길을 따라 달릴 수 있는 인기 공원 코스', '경기도 수원시 영통구 광교호수공원', 7.4, '약 7 km (호수 순환)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course14_gwanggyo.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (6, '제주 탑동 바다 러닝', '제주 바다 바람과 탁 트인 해안선을 달릴 수 있는 코스', '제주특별자치도 제주시 탑동해변공연장 일대', 9.5, '약 8-10 km (해안 산책로 왕복)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course15_jeju.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (7, '울산 대왕암 해안 러닝', '해송 숲길과 해안 절경을 함께 즐길 수 있는 힐링 러닝 코스', '울산광역시 동구 일산동 대왕암공원', 10.2, '약 9-10 km (공원 및 해안 산책로)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course16_ulsan.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '춘천 공지천 의암호 러닝', '호수와 산이 어우러진 강원도 대표 러닝 명소', '강원특별자치도 춘천시 송암동 공지천 일대', 11.7, '약 10-12 km (공지천-의암호 일주)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course17_chuncheon.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (2, '성남 탄천 러닝', '탄천을 따라 남북으로 길게 뻗은 평탄한 하천 러닝 명소', '경기도 성남시 분당구 서현동 탄천변', 12.3, '약 10-12 km (분당 탄천 구간)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course18_seongnam.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (3, '광주 풍암호수 러닝', '작지만 집중 러닝하기 좋은 호수 순환 코스', '광주광역시 서구 풍암동 풍암호수공원', 5.4, '약 5 km (호수 순환)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course19_gwangju.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (4, '세종 호수공원 수목원 런', '호수공원과 수목원 구간을 잇는 세종 대표 러닝 코스', '세종특별자치시 한누리대로 1575 (호수공원)', 13.0, '약 12-13 km (호수공원–수목원 루프)', 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/courses/course20_sejong.jpg', 0.0, 0);


INSERT INTO course_detail_image (course_id, image_url) VALUES (1, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/banpo_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (1, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/banpo_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (2, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/yeouido_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (2, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/yeouido_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (3, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/cheonggye_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (3, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/cheonggye_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (4, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/yangjae_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (4, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/yangjae_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (5, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/dulegil_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (5, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/dulegil_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (6, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/ttukseom_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (6, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/ttukseom_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (7, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/bugaksan_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (7, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/bugaksan_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (8, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/olympic_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (8, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/olympic_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (9, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/worldcup_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (9, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/worldcup_detail2.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (10, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/anyang_detail1.png');
INSERT INTO course_detail_image (course_id, image_url) VALUES (10, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/anyang_detail2.png');

INSERT INTO course_detail_image (course_id, image_url) VALUES (11, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/songdo_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (11, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/songdo_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (12, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/busan_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (12, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/busan_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (13, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/daejeon_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (13, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/daejeon_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (14, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/gwanggyo_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (14, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/gwanggyo_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (15, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/jeju_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (15, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/jeju_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (16, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/ulsan_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (16, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/ulsan_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (17, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/chuncheon_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (17, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/chuncheon_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (18, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/seongnam_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (18, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/seongnam_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (19, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/gwangju_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (19, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/gwangju_detail2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (20, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/sejong_detail1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (20, 'https://runcode-bucket.s3.ap-northeast-2.amazonaws.com/course_detail/sejong_detail2.jpg');


INSERT INTO course_tags (course_id, tag_id) VALUES (1, 2);
INSERT INTO course_tags (course_id, tag_id) VALUES (1, 5);
INSERT INTO course_tags (course_id, tag_id) VALUES (2, 3);
INSERT INTO course_tags (course_id, tag_id) VALUES (2, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (3, 4);
INSERT INTO course_tags (course_id, tag_id) VALUES (3, 7);
INSERT INTO course_tags (course_id, tag_id) VALUES (4, 8);
INSERT INTO course_tags (course_id, tag_id) VALUES (4, 5);
INSERT INTO course_tags (course_id, tag_id) VALUES (5, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (6, 7);
INSERT INTO course_tags (course_id, tag_id) VALUES (7, 8);
INSERT INTO course_tags (course_id, tag_id) VALUES (7, 1);
INSERT INTO course_tags (course_id, tag_id) VALUES (8, 2);
INSERT INTO course_tags (course_id, tag_id) VALUES (9, 3);
INSERT INTO course_tags (course_id, tag_id) VALUES (10, 1);
INSERT INTO course_tags (course_id, tag_id) VALUES (10, 4);

INSERT INTO course_tags (course_id, tag_id) VALUES (11, 2);
INSERT INTO course_tags (course_id, tag_id) VALUES (11, 5);
INSERT INTO course_tags (course_id, tag_id) VALUES (12, 3);
INSERT INTO course_tags (course_id, tag_id) VALUES (12, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (13, 4);
INSERT INTO course_tags (course_id, tag_id) VALUES (13, 7);
INSERT INTO course_tags (course_id, tag_id) VALUES (14, 8);
INSERT INTO course_tags (course_id, tag_id) VALUES (14, 5);
INSERT INTO course_tags (course_id, tag_id) VALUES (15, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (16, 7);
INSERT INTO course_tags (course_id, tag_id) VALUES (17, 8);
INSERT INTO course_tags (course_id, tag_id) VALUES (17, 1);
INSERT INTO course_tags (course_id, tag_id) VALUES (18, 2);
INSERT INTO course_tags (course_id, tag_id) VALUES (19, 3);
INSERT INTO course_tags (course_id, tag_id) VALUES (20, 1);
INSERT INTO course_tags (course_id, tag_id) VALUES (20, 4);
-- 타입당 태그 4개 맞춰놨습니다~
-- INSERT INTO tags (name) VALUES ('아침런'); -- S 1
-- INSERT INTO tags (name) VALUES ('기록 지향'); -- G 2
-- INSERT INTO tags (name) VALUES ('솔로런'); -- M 3
-- INSERT INTO tags (name) VALUES ('성취감추구'); -- P 4
-- INSERT INTO tags (name) VALUES ('저녁런'); -- N 5
-- INSERT INTO tags (name) VALUES ('엔조이'); -- H 6
-- INSERT INTO tags (name) VALUES ('단체런'); -- T 7
-- INSERT INTO tags (name) VALUES ('풍경 즐기기'); -- F 8
INSERT INTO type_tags (type_id, tag_id) VALUES (1, 2);
INSERT INTO type_tags (type_id, tag_id) VALUES (1, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (1, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (1, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (2, 2);
INSERT INTO type_tags (type_id, tag_id) VALUES (2, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (2, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (2, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (3, 2);
INSERT INTO type_tags (type_id, tag_id) VALUES (3, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (3, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (3, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (4, 2);
INSERT INTO type_tags (type_id, tag_id) VALUES (4, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (4, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (4, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (5, 2);
INSERT INTO type_tags (type_id, tag_id) VALUES (5, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (5, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (5, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (6, 2);
INSERT INTO type_tags (type_id, tag_id) VALUES (6, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (6, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (6, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (7, 2); -- GFNM
INSERT INTO type_tags (type_id, tag_id) VALUES (7, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (7, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (7, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (8, 2); -- GFNT
INSERT INTO type_tags (type_id, tag_id) VALUES (8, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (8, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (8, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (9, 6); -- HPSM
INSERT INTO type_tags (type_id, tag_id) VALUES (9, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (9, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (9, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (10, 6); -- HPST
INSERT INTO type_tags (type_id, tag_id) VALUES (10, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (10, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (10, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (11, 6); -- HPNM
INSERT INTO type_tags (type_id, tag_id) VALUES (11, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (11, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (11, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (12, 6); -- HPNT
INSERT INTO type_tags (type_id, tag_id) VALUES (12, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (12, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (12, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (13, 6); -- HFSM
INSERT INTO type_tags (type_id, tag_id) VALUES (13, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (13, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (13, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (14, 6); -- HFST
INSERT INTO type_tags (type_id, tag_id) VALUES (14, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (14, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (14, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (15, 6); -- HFNM
INSERT INTO type_tags (type_id, tag_id) VALUES (15, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (15, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (15, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (16, 6); -- HFNT
INSERT INTO type_tags (type_id, tag_id) VALUES (16, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (16, 5);
INSERT INTO type_tags (type_id, tag_id) VALUES (16, 7);

INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.505800, 127.004400, 'START', 1);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.526900, 126.934700, 'START', 2);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.569200, 126.979200, 'START', 3);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.470000, 127.038000, 'START', 4);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.688600, 127.046900, 'START', 5);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.529900, 127.067500, 'START', 6);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.594300, 126.966900, 'START', 7);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.520900, 127.121300, 'START', 8);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.568500, 126.885100, 'START', 9);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.501800, 126.869500, 'START', 10);

INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.392700, 126.638000, 'START', 11);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (35.153200, 129.118300, 'START', 12);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (36.366400, 127.346800, 'START', 13);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.288900, 127.057200, 'START', 14);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (33.514100, 126.522700, 'START', 15);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (35.490900, 129.436700, 'START', 16);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.874900, 127.723400, 'START', 17);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.386900, 127.126400, 'START', 18);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (35.128900, 126.877000, 'START', 19);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (36.485000, 127.259000, 'START', 20);

INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (3, 4, '2025-10-02', '나의 첫 러닝!!', '첫 러닝이라 살짝 긴장했는데 공기가 차가워서 오히려 상쾌했다! 페이스는 무리 안 하고 감 잡는 느낌으로~', 'https://img.example.com/arch_1.jpg', 'https://img.example.com/arch_1_detail.jpg', 5.1, 320, '5''31\"', '31:11', 11, 161);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (4, 5, '2025-10-03', 'Morning Run~', '어제보다 숨이 덜 찼다. 둘레길이 조용해서 머리가 맑아지는 느낌! 이런 코스는 자주 와야겠다.', 'https://img.example.com/arch_2.jpg', 'https://img.example.com/arch_2_detail.jpg', 5.7, 340, '5''32\"', '32:12', 12, 162);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (5, 6, '2025-10-04', '2025/10/04 기록', '몸이 살짝 무거웠는데 음악이 살려줬다..! 마지막 1km에서 페이스 올려서 기분 좋게 마무리!', 'https://img.example.com/arch_3.jpg', 'https://img.example.com/arch_3_detail.jpg', 6.3, 360, '5''33\"', '33:13', 13, 163);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (6, 7, '2025-10-05', '오늘 기록', '산쪽이라 오르막에서 호흡이 확 올라갔지만 경치가 너무 좋아서 버텼다! 다음엔 준비운동 더 하고 와야지~', 'https://img.example.com/arch_4.jpg', 'https://img.example.com/arch_4_detail.jpg', 6.9, 380, '5''34\"', '34:14', 14, 164);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (7, 8, '2025-10-06', '기록 #5', '피곤했는데 뛰고 나니까 진짜 사람이 달라졌다..! 크루랑 같이 오면 더 재밌을 것 같은 코스였다 :)', 'https://img.example.com/arch_5.jpg', 'https://img.example.com/arch_5_detail.jpg', 7.5, 400, '5''35\"', '35:15', 15, 165);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (8, 9, '2025-10-07', '기록 #6', '바람이 좀 강해서 초반엔 흔들렸는데 중반부터 리듬이 딱 잡혔다! 컨디션 나쁜 날에도 이 정도면 만족~', 'https://img.example.com/arch_6.jpg', 'https://img.example.com/arch_6_detail.jpg', 8.1, 420, '5''36\"', '36:16', 16, 166);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (9, 10, '2025-10-08', '10월 8일', '노을이 너무 예뻐서 중간에 사진도 찍고 여유롭게 뛰었다 :) 오늘은 기록보다 분위기 즐기는 날!', 'https://img.example.com/arch_7.jpg', 'https://img.example.com/arch_7_detail.jpg', 8.7, 440, '5''37\"', '37:17', 17, 167);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (10, 1, '2025-10-09', '10/09 러닝 기록', '오늘은 몸이 진짜 가볍게 나갔다! 이 페이스를 기본으로 가져가도 될 것 같아서 뿌듯했다~', 'https://img.example.com/arch_8.jpg', 'https://img.example.com/arch_8_detail.jpg', 9.3, 460, '5''38\"', '38:18', 18, 168);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (1, 2, '2025-10-10', '주말 러닝 기록', '비 오기 전 공기라 좀 눅눅했는데 청계천 쪽 들어가니까 시원해서 살았다..! 끝나고 스트레칭까지 풀코스로 완료!', 'https://img.example.com/arch_9.jpg', 'https://img.example.com/arch_9_detail.jpg', 9.9, 480, '5''39\"', '39:19', 19, 169);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (2, 3, '2025-10-11', ' Evening Run', '벌써 10번째라니..! 첫날보다 확실히 여유가 생겼다. 아침 러닝 루틴 잡히는 중이라 기분 최고!', 'https://img.example.com/arch_10.jpg', 'https://img.example.com/arch_10_detail.jpg', 10.5, 500, '5''40\"', '40:20', 20, 170);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (1, 4, '2025-10-20', '주말 러닝 기록2', '주말 러닝이다. 피곤했지만 뛰니가 정말 상쾌하다~', 'https://img.example.com/arch_9.jpg', 'https://img.example.com/arch_11_detail.jpg', 6.9, 230, '6''39\"', '45:14', 19, 144);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (2, 5, '2025-10-22', ' Evening Run2', '이제 11번째임. 치타는 매일 달린다.', 'https://img.example.com/arch_10.jpg', 'https://img.example.com/arch_12_detail.jpg', 13, 550, '5''21\"', '61:09', 20, 175);

INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (1, 1, '5''21\"', '+1s', 16);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (2, 2, '5''22\"', '+2s', 17);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (3, 3, '5''23\"', '+3s', 18);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (4, 4, '5''24\"', '+4s', 19);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (5, 5, '5''25\"', '+0s', 20);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (6, 6, '5''26\"', '+1s', 21);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (7, 7, '5''27\"', '+2s', 22);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (8, 8, '5''28\"', '+3s', 23);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (9, 9, '5''29\"', '+4s', 24);
INSERT INTO laps (archiving_id, lap_number, average_pace, pace_variation, altitude) VALUES (10, 10, '5''30\"', '+0s', 25);

INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (4, 3, 3.5, '도심인데도 물소리랑 바람이 있어서 생각보다 분위기 좋았어요. 살짝 사람 많은 게 아쉽긴 했습니다.', '2025-10-01T12:00:00', '2025-10-01T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (7, 5, 4.0, '코스 길이가 딱 적당해서 페이스 유지하기 좋았어요! 중간에 오르막만 조금 덜했으면 더 좋았을 듯요.', '2025-10-02T12:00:00', '2025-10-02T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (10, 7, 3.0, '뷰는 괜찮았는데 경사가 조금 많아서 기록 내기는 힘들었어요. 힐 트레이닝용으로는 굿!', '2025-10-03T12:00:00', '2025-10-03T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (3, 9, 3.5, '공원이 잘 정리돼 있어서 달리기 편했어요. 사람 없을 때 오면 더 좋겠다는 생각 들었습니다.', '2025-10-04T12:00:00', '2025-10-04T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (6, 1, 4.0, '한강 라인이라 역시 시원하네요! 바람 불 때는 진짜 기분 좋아요. 주말에는 살짝 붐빕니다.', '2025-10-05T12:00:00', '2025-10-05T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (9, 3, 3.0, '코스 자체는 무난했는데 거리 표시가 좀 애매해서 페이스 맞추기 어려웠어요. 그래도 접근성은 좋아요.', '2025-10-06T12:00:00', '2025-10-06T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (2, 5, 3.5, '자연 느낌 나서 힐링용으로 괜찮았어요. 러닝보단 조깅이나 워킹할 때 더 어울리는 코스 같아요.', '2025-10-07T12:00:00', '2025-10-07T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (5, 7, 4.0, '전망이 탁 트여서 지루할 틈이 없었어요! 친구들이랑 같이 와도 좋을 듯한 루트였습니다 :)', '2025-10-08T12:00:00', '2025-10-08T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (8, 9, 3.0, '후반부에 사람이 좀 많아서 속도 내기는 어려웠어요. 그래도 사진 찍기 좋은 포인트가 많네요!', '2025-10-09T12:00:00', '2025-10-09T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (1, 1, 3.5, '전체적으로 깔끔하고 러너도 많아서 동기부여 됐어요~ 바람 많이 부는 날엔 살짝 힘들 수 있어요.', '2025-10-10T12:00:00', '2025-10-10T12:00:00');

INSERT INTO bookmarks (user_id, course_id) VALUES (8, 6);
INSERT INTO bookmarks (user_id, course_id) VALUES (5, 1);
INSERT INTO bookmarks (user_id, course_id) VALUES (2, 6);
INSERT INTO bookmarks (user_id, course_id) VALUES (9, 1);
INSERT INTO bookmarks (user_id, course_id) VALUES (6, 6);
INSERT INTO bookmarks (user_id, course_id) VALUES (3, 1);
INSERT INTO bookmarks (user_id, course_id) VALUES (10, 6);
INSERT INTO bookmarks (user_id, course_id) VALUES (7, 1);
INSERT INTO bookmarks (user_id, course_id) VALUES (4, 6);
INSERT INTO bookmarks (user_id, course_id) VALUES (1, 1);