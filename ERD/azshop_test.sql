INSERT INTO Category 
VALUES(1, 1, '패션의류', '여성의류');
INSERT INTO Category 
VALUES(1, 2, '패션의류', '남성의류');
INSERT INTO Category 
VALUES(1, 3, '패션의류', '남여공용');
INSERT INTO Category 
VALUES(1, 4, '패션의류', '신발');
INSERT INTO Category 
VALUES(2, 1, '식품', '과일');
INSERT INTO Category 
VALUES(2, 2, '식품', '채소');
INSERT INTO Category 
VALUES(2, 3, '식품', '쌀/잡곡');
INSERT INTO Category 
VALUES(2, 4, '식품', '냉동/간편');
INSERT INTO Category 
VALUES(3, 1, '주방용품', '냄비/프라이팬');
INSERT INTO Category 
VALUES(3, 2, '주방용품', '그릇/접시');
INSERT INTO Category 
VALUES(3, 3, '주방용품', '수저/젖가락');
INSERT INTO Category 
VALUES(4, 1, '유아용품', '기저귀');
INSERT INTO Category 
VALUES(4, 2, '유아용품', '분유');
INSERT INTO Category 
VALUES(4, 3, '유아용품', '매트/안전용품');
INSERT INTO Category 
VALUES(4, 4, '유아용품', '유모차');
INSERT INTO Category 
VALUES(5, 1, '가전/디지털', '냉장고');
INSERT INTO Category 
VALUES(5, 2, '가전/디지털', '세탁기');
INSERT INTO Category 
VALUES(5, 3, '가전/디지털', '청소기');
INSERT INTO Category 
VALUES(5, 4, '가전/디지털', '에어컨');
INSERT INTO Category 
VALUES(5, 5, '가전/디지털', 'TV/컴퓨터');
INSERT INTO Category 
VALUES(6, 1, '반려동물용품', '강아지사료');
INSERT INTO Category 
VALUES(6, 2, '반려동물용품', '강아지간식');
INSERT INTO Category 
VALUES(6, 3, '반려동물용품', '고양이사료');
INSERT INTO Category 
VALUES(6, 4, '반려동물용품', '고양이간식');
INSERT INTO Category 
VALUES(6, 5, '반려동물용품', '장난감');
INSERT INTO Category 
VALUES(1, 5, '패션의류', '아동');

DELETE FROM Product 
WHERE id = 3;

TRUNCATE Category;
TRUNCATE Cart_Product;
SELECT * FROM Product;
SELECT * FROM Cart_Product;
SELECT * FROM Category;