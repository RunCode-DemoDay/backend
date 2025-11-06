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

TRUNCATE TABLE types RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE tags RESTART IDENTITY CASCADE;
TRUNCATE TABLE courses RESTART IDENTITY CASCADE;
TRUNCATE TABLE course_tags RESTART IDENTITY CASCADE;
TRUNCATE TABLE type_tags RESTART IDENTITY CASCADE;
TRUNCATE TABLE locations RESTART IDENTITY CASCADE;
TRUNCATE TABLE archivings RESTART IDENTITY CASCADE;
TRUNCATE TABLE laps RESTART IDENTITY CASCADE;
TRUNCATE TABLE reviews RESTART IDENTITY CASCADE;
TRUNCATE TABLE bookmarks RESTART IDENTITY CASCADE;
TRUNCATE TABLE course_detail_image RESTART IDENTITY CASCADE;

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

INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '한강 반포 러닝 코스', '아침 햇살과 강바람을 맞으며 기록에 집중하기 좋은 한강 대표 코스', '서울시 서초구 반포동 (고속터미널역 8-1 출구 도보 10분)', 5.7, '약 5km (반포대교-잠수교 순환)', 'https://img.example.com/course_1.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (2, '한강 여의도 러닝 코스', '여의도공원과 샛강 생태공원을 포함한 강변 평탄 구간', '서울시 영등포구 여의도동 (여의도한강공원 일대)', 6.4, '약 5 km (여의도 샛강생태공원 루프 포함)', 'https://img.example.com/course_2.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (3, '청계천 도심 러닝 코스', '도심 속 하천 따라 시원하게 달릴 수 있는 도심로 코스', '서울시 종로구 수표동 (청계광장 인근)', 7.1, '약 3-4 km (청계광장에서 동쪽방향 왕복)', 'https://img.example.com/course_3.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (4, '양재천 물길 러닝 코스', '강남·서초와 이어지는 나무 그늘 아래 평탄한 스트림 코스', '서울시 서초구 양재동 ~ 강남구 일대 (양재천변)', 7.8, '서울시 서초구 양재동 ~ 강남구 일대 (양재천변)', 'https://img.example.com/course_4.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (5, '서울둘레길 외곽 러닝 코스', '서울 외곽 자연과 마을길을 잇는 트레일 느낌의 긴 코스', '서울시 외곽 산자락 일대 (예: 남양주시경계 등)', 8.5, '약 8-12 km (서울둘레길 1개 구간)', 'https://img.example.com/course_5.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (6, '뚝섬-광진교 한강 러닝 코스', '한강변 자전거길을 공유하며 강변 풍경 따라 달리기 좋은 구간', '서울시 광진구 뚝섬유원지역 인근 (뚝섬유원지 출발)', 9.2, '약 7.9 km (뚝섬유원지-광진교 구간)', 'https://img.example.com/course_6.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (7, '북악산 성곽길 러닝 코스', '도심과 산이 만나는 곳에서 기분 전환하며 달릴 수 있는 코스', '서울시 종로구 부암동 일대 (북악산 성곽길 시작점)', 9.9, '약 10 km 내외 (성곽길 구간)', 'https://img.example.com/course_7.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '올림픽공원 조깅 코스', '1988올림픽 메모리얼 공간에서 평탄하고 잘 정비된 트랙', '서울시 송파구 올림픽로 424 (올림픽공원 내부)', 10.6, '약 4 km (공원 내부 루프)', 'https://img.example.com/course_8.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '월드컵공원 하늘공원 러닝 코스', '도시 속 녹지와 계단을 섞어 달리기 강화되는 기분 좋은 트레일', '서울시 마포구 하늘공원로 95 (월드컵공원 하늘공원)', 11.3, '약 3-5 km (공원 내부 루프)', 'https://img.example.com/course_9.jpg', 0.0, 0);
INSERT INTO courses (user_id, title, content, address, distance, distance_description, thumbnail, star_average, review_count) VALUES (1, '안양천 강변 러닝 코스', '강변 나무 그늘 아래 꾸준히 달리기 좋은 강변 산책로', '서울시 구로구 구로동 ~ 양천구 일대 (안양천 산책로)', 12.0, '약 5-6 km (안양천 산책·러닝 구간)', 'https://img.example.com/course_10.jpg', 0.0, 0);
INSERT INTO course_detail_image (course_id, image_url) VALUES (1, 'https://img.example.com/course_1_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (1, 'https://img.example.com/course_1_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (2, 'https://img.example.com/course_2_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (2, 'https://img.example.com/course_2_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (3, 'https://img.example.com/course_3_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (3, 'https://img.example.com/course_3_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (4, 'https://img.example.com/course_4_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (4, 'https://img.example.com/course_4_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (5, 'https://img.example.com/course_5_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (5, 'https://img.example.com/course_5_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (6, 'https://img.example.com/course_6_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (6, 'https://img.example.com/course_6_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (7, 'https://img.example.com/course_7_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (7, 'https://img.example.com/course_7_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (8, 'https://img.example.com/course_8_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (8, 'https://img.example.com/course_8_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (9, 'https://img.example.com/course_9_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (9, 'https://img.example.com/course_9_detail_2.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (10, 'https://img.example.com/course_10_detail_1.jpg');
INSERT INTO course_detail_image (course_id, image_url) VALUES (10, 'https://img.example.com/course_10_detail_2.jpg');
INSERT INTO course_tags (course_id, tag_id) VALUES (1, 2);
INSERT INTO course_tags (course_id, tag_id) VALUES (1, 5);
INSERT INTO course_tags (course_id, tag_id) VALUES (2, 3);
INSERT INTO course_tags (course_id, tag_id) VALUES (2, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (3, 4);
INSERT INTO course_tags (course_id, tag_id) VALUES (3, 7);
INSERT INTO course_tags (course_id, tag_id) VALUES (4, 8);
INSERT INTO course_tags (course_id, tag_id) VALUES (4, 5);
INSERT INTO course_tags (course_id, tag_id) VALUES (5, 1);
INSERT INTO course_tags (course_id, tag_id) VALUES (5, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (6, 5);
INSERT INTO course_tags (course_id, tag_id) VALUES (6, 7);
INSERT INTO course_tags (course_id, tag_id) VALUES (7, 8);
INSERT INTO course_tags (course_id, tag_id) VALUES (7, 1);
INSERT INTO course_tags (course_id, tag_id) VALUES (8, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (8, 2);
INSERT INTO course_tags (course_id, tag_id) VALUES (9, 6);
INSERT INTO course_tags (course_id, tag_id) VALUES (9, 3);
INSERT INTO course_tags (course_id, tag_id) VALUES (10, 1);
INSERT INTO course_tags (course_id, tag_id) VALUES (10, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (1, 6);
INSERT INTO type_tags (type_id, tag_id) VALUES (2, 7);
INSERT INTO type_tags (type_id, tag_id) VALUES (3, 8);
INSERT INTO type_tags (type_id, tag_id) VALUES (4, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (5, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (6, 1);
INSERT INTO type_tags (type_id, tag_id) VALUES (7, 2);
INSERT INTO type_tags (type_id, tag_id) VALUES (8, 3);
INSERT INTO type_tags (type_id, tag_id) VALUES (9, 4);
INSERT INTO type_tags (type_id, tag_id) VALUES (10, 5);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.510000, 127.010000, 'START', 1);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.520000, 127.020000, 'START', 2);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.530000, 127.030000, 'START', 3);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.540000, 127.040000, 'START', 4);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.550000, 127.050000, 'START', 5);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.560000, 127.060000, 'START', 6);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.570000, 127.070000, 'START', 7);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.580000, 127.080000, 'START', 8);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.590000, 127.090000, 'START', 9);
INSERT INTO locations (latitude, longitude, location_type, course_id) VALUES (37.600000, 127.100000, 'START', 10);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (3, 4, '2025-10-02', 'Morning Run #1', 'Felt great on day 1. Weather was perfect.', 'https://img.example.com/arch_1.jpg', 'https://img.example.com/arch_1_detail.jpg', 5.1, 320, '5''31\"', '31:11', 11, 161);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (4, 5, '2025-10-03', 'Morning Run #2', 'Felt great on day 2. Weather was perfect.', 'https://img.example.com/arch_2.jpg', 'https://img.example.com/arch_2_detail.jpg', 5.7, 340, '5''32\"', '32:12', 12, 162);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (5, 6, '2025-10-04', 'Morning Run #3', 'Felt great on day 3. Weather was perfect.', 'https://img.example.com/arch_3.jpg', 'https://img.example.com/arch_3_detail.jpg', 6.3, 360, '5''33\"', '33:13', 13, 163);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (6, 7, '2025-10-05', 'Morning Run #4', 'Felt great on day 4. Weather was perfect.', 'https://img.example.com/arch_4.jpg', 'https://img.example.com/arch_4_detail.jpg', 6.9, 380, '5''34\"', '34:14', 14, 164);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (7, 8, '2025-10-06', 'Morning Run #5', 'Felt great on day 5. Weather was perfect.', 'https://img.example.com/arch_5.jpg', 'https://img.example.com/arch_5_detail.jpg', 7.5, 400, '5''35\"', '35:15', 15, 165);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (8, 9, '2025-10-07', 'Morning Run #6', 'Felt great on day 6. Weather was perfect.', 'https://img.example.com/arch_6.jpg', 'https://img.example.com/arch_6_detail.jpg', 8.1, 420, '5''36\"', '36:16', 16, 166);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (9, 10, '2025-10-08', 'Morning Run #7', 'Felt great on day 7. Weather was perfect.', 'https://img.example.com/arch_7.jpg', 'https://img.example.com/arch_7_detail.jpg', 8.7, 440, '5''37\"', '37:17', 17, 167);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (10, 1, '2025-10-09', 'Morning Run #8', 'Felt great on day 8. Weather was perfect.', 'https://img.example.com/arch_8.jpg', 'https://img.example.com/arch_8_detail.jpg', 9.3, 460, '5''38\"', '38:18', 18, 168);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (1, 2, '2025-10-10', 'Morning Run #9', 'Felt great on day 9. Weather was perfect.', 'https://img.example.com/arch_9.jpg', 'https://img.example.com/arch_9_detail.jpg', 9.9, 480, '5''39\"', '39:19', 19, 169);
INSERT INTO archivings (user_id, course_id, date, title, content, thumbnail, detail_image, distance, calorie, average_pace, time, altitude, cadence) VALUES (2, 3, '2025-10-11', 'Morning Run #10', 'Felt great on day 10. Weather was perfect.', 'https://img.example.com/arch_10.jpg', 'https://img.example.com/arch_10_detail.jpg', 10.5, 500, '5''40\"', '40:20', 20, 170);
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
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (4, 3, 3.5, 'I liked the scenery and pacing options (1).', '2025-10-01T12:00:00', '2025-10-01T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (7, 5, 4.0, 'I liked the scenery and pacing options (2).', '2025-10-02T12:00:00', '2025-10-02T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (10, 7, 3.0, 'I liked the scenery and pacing options (3).', '2025-10-03T12:00:00', '2025-10-03T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (3, 9, 3.5, 'I liked the scenery and pacing options (4).', '2025-10-04T12:00:00', '2025-10-04T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (6, 1, 4.0, 'I liked the scenery and pacing options (5).', '2025-10-05T12:00:00', '2025-10-05T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (9, 3, 3.0, 'I liked the scenery and pacing options (6).', '2025-10-06T12:00:00', '2025-10-06T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (2, 5, 3.5, 'I liked the scenery and pacing options (7).', '2025-10-07T12:00:00', '2025-10-07T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (5, 7, 4.0, 'I liked the scenery and pacing options (8).', '2025-10-08T12:00:00', '2025-10-08T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (8, 9, 3.0, 'I liked the scenery and pacing options (9).', '2025-10-09T12:00:00', '2025-10-09T12:00:00');
INSERT INTO reviews (user_id, course_id, star, content, created_at, updated_at) VALUES (1, 1, 3.5, 'I liked the scenery and pacing options (10).', '2025-10-10T12:00:00', '2025-10-10T12:00:00');
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