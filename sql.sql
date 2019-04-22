/*创建身份类型表*/
create table role
(
	id int auto_increment  not null primary key,
	name varchar(100) not null unique
) CHARACTER SET utf8 COLLATE utf8_general_ci
insert into role values (1,'管理员');
insert into role values ('教师');
insert into role values('学生');

/*创建用户信息表*/
create table userinfo
(
	id varchar(20) not null primary key,
	name varchar(20) ,
	password varchar(100) not null,
	iden varchar(100) not null,
	foreign key(iden) references role(name)
)CHARACTER SET utf8 COLLATE utf8_general_ci
insert into userinfo values ('admin','admin','admin','管理员');
insert into userinfo values ('10001','测试教师','123456','教师');
/*
创建课程信息表
 */
 create table course(
  id int auto_increment not null primary key,
  name varchar(100) not null unique ,
  userid varchar(20) not null,
  foreign key(userid) references userinfo(id)
 )CHARACTER SET utf8 COLLATE utf8_general_ci
 insert into course values(1,'测试课程','10001');
alter table course add column end datetime;
alter table course add column begin datetime;


/**
创建题目表 -判断题
 */

 create table  question(
  id int auto_increment primary key not null,
  name varchar(100) not null,
  content varchar(1000) not null ,
  foreign key(name) references course(name)
 )
 alter table question add column answer varchar(4);
alter table question add unique key(content);



/**
创建题目表 -选择题
 */

create table question_choice(
  id int auto_increment primary key not null,
  name varchar(100) not null,
  a varchar(1000) not null ,
  b varchar(1000) not null ,
  c varchar(1000) not null ,
  d varchar(1000) not null ,
 foreign key(name) references course(name),

);



/**
  创建答题记录表
 */
 create table ans_record(
   id int auto_increment not null primary key,
   promulgator varchar(20) not null,
   stu_id varchar(20) not null,
   type varchar(10) check(type in ('抢答','随机','指定')),
   count int not null,
   result varchar(10) not null,
   foreign key(promulgator) references userinfo(id),
   foreign key(stu_id) references userinfo(id)
 );
 alter table  ans_record add course_name varchar(100) not null ;
 alter table  ans_record add foreign key(course_name) references course(name);
/**
    课程，学生对应表
*/
  create table course_stu(
    id int auto_increment primary key not null,
    course varchar(100) not null ,
    userid varchar (20) not null,
    foreign key(course) references course(name),
    foreign key(userid) references userinfo(id)
  );

/**
  签到记录表
 */
  create table check_record(
  id int auto_increment not null primary key ,
  userid varchar (20) not null,
  course varchar (100) not null,
  date date not null ,
  late varchar(2) check (late in ('准时','迟到')),
  foreign key (userid) references userinfo(id),
  foreign key (course) references course(name)
  );


/**
  套题记录表
 */
create table examination(
  id varchar(10) primary key not null,
   promulgator varchar(20) not null,
   time date  not null ,
   stu_id varchar(20) not null,
   course varchar(100) not null,
   type varchar(10) check(type in ('抢答','随机','指定')),
   foreign key(course) references course(name),
   foreign key(promulgator) references userinfo(id),
   foreign key(stu_id) references userinfo(id)
);
/**
    套题 - 题目 关联表
 */
create table contact(
    id int auto_increment primary key not null,
    type varchar (10),
    exam_id varchar(10) not null,
    question_id int not null,
    foreign key(exam_id) references examination(id),
    foreign key (question_id) references question(id),
    foreign key (question_id) references question_choice(id)
);

/**
    file
 */
create  table file(
    id int auto_increment primary key not null,
    promulgator varchar(20) not null,
    course varchar(100) not null,
    access varchar(1000) not null,
    name varchar(1000) not null,
    foreign key(course) references course(name),
    foreign key(promulgator) references userinfo(id)
)
