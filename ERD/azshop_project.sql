SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Cart;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Authority;
DROP TABLE IF EXISTS Card;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Sales;




/* Create Tables */

CREATE TABLE Address
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	address_name varchar(50),
	address varchar(100) NOT NULL,
	address_detail varchar(50) NOT NULL,
	postcode varchar(10) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE Authority
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE Card
(
	id int NOT NULL AUTO_INCREMENT,
	c_num varchar(20) NOT NULL,
	c_year varchar(2) NOT NULL,
	c_month varchar(2) NOT NULL,
	c_cvc varchar(3) NOT NULL,
	balance int,
	PRIMARY KEY (id)
);


CREATE TABLE Cart
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	product_id int NOT NULL,
	amount int,
	regdate datetime DEFAULT now(),
	PRIMARY KEY (id)
);


CREATE TABLE Category
(
	maincode varchar(10),
	subcode varchar(10),
	mainname varchar(50),
	subname varchar(50)
);


CREATE TABLE Product
(
	id int NOT NULL AUTO_INCREMENT,
	p_name varchar(50) NOT NULL,
	main_cate varchar(10) NOT NULL,
	sub_cate varchar(10) NOT NULL,
	p_img varchar(100),
	detail longtext,
	price int NOT NULL,
	stock int,
	p_rank int,
	PRIMARY KEY (id)
);


CREATE TABLE Review
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	product_id int NOT NULL,
	rating double NOT NULL,
	content longtext NOT NULL,
	reviewdate datetime DEFAULT now(),
	reply text,
	replydate datetime DEFAULT now(),
	PRIMARY KEY (id)
);


CREATE TABLE Sales
(
	id int NOT NULL AUTO_INCREMENT,
	u_username varchar(50),
	p_id int NOT NULL,
	amount int NOT NULL,
	address varchar(50) NOT NULL,
	address_detail varchar(50) NOT NULL,
	postcode varchar(10) NOT NULL,
	deliveryreq varchar(300),
	tracknum varchar(50),
	PRIMARY KEY (id)
);


CREATE TABLE User
(
	id int NOT NULL AUTO_INCREMENT,
	authority_id int NOT NULL,
	username varchar(50) NOT NULL,
	password varchar(200) NOT NULL,
	nickname varchar(30) NOT NULL,
	email varchar(50),
	profileimg varchar(100),
	u_status varchar(5) CHECK(u_status IN ('USE', 'SLEEP', 'OUT')),
	phone varchar(11) NOT NULL,
	logdate datetime,
	regdate datetime DEFAULT now(),
	PRIMARY KEY (id),
	UNIQUE (username),
	UNIQUE (email)
);



/* Create Foreign Keys */

ALTER TABLE User
	ADD FOREIGN KEY (authority_id)
	REFERENCES Authority (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Cart
	ADD FOREIGN KEY (product_id)
	REFERENCES Product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Review
	ADD FOREIGN KEY (product_id)
	REFERENCES Product (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Address
	ADD FOREIGN KEY (user_id)
	REFERENCES User (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Cart
	ADD FOREIGN KEY (user_id)
	REFERENCES User (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Review
	ADD FOREIGN KEY (user_id)
	REFERENCES User (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



