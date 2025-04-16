CREATE TABLE `total` (
  `TeamNum` integer PRIMARY KEY NOT NULL,
  `sort` varchar(255) NOT NULL,
  `class` integer NOT NULL,
  `time` datetime,
  `number` integer,
  `date` date,
  `money` integer,
  `destination` varchar(255),
  `address` varchar(255),
  `place` varchar(255),
  `detail` varchar(255),
  `team_time` integer,
  `team_num` integer,
  'leader_id' varchar(255)  --Members.id 참조하도록함.
);

CREATE TABLE `Members` (
  `id` varchar(255) PRIMARY KEY NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `school_code` varchar(255),
  `school_department` varchar(255),
  `phone_num` varchar(255) NOT NULL,
  `status` integer DEFAULT 1  --status에 기본값 추가함.
);

CREATE TABLE `joining` (
  `TeamNum` integer NOT NULL,
  `id` varchar(255) NOT NULL,
  `sort` varchar(255) NOT NULL,
  `class` integer NOT NULL,
  `time` datetime,
  `number` integer,
  PRIMARY KEY (`TeamNum`, `id`) --복합적으로 기본 키 설정함.
);

CREATE TABLE `admin` (
  `ad_id` integer PRIMARY KEY NOT NULL,
  `ad_pw` varchar(255) NOT NULL,
  `ad_status` integer,
  `ad_email` varchar(255)
);

ALTER TABLE `total` ADD FOREIGN KEY (`leader_id`) REFERENCES `Members` (`id`);

ALTER TABLE `joining` ADD FOREIGN KEY (`TeamNum`) REFERENCES `total` (`TeamNum`);

--ALTER TABLE `Members` ADD FOREIGN KEY (`id`) REFERENCES `admin` (`ad_id`);

ALTER TABLE `joining` ADD FOREIGN KEY (`id`) REFERENCES `Members` (`id`);

--ALTER TABLE `total` ADD FOREIGN KEY (`TeamNum`) REFERENCES `admin` (`ad_id`);

--ALTER TABLE `joining` ADD FOREIGN KEY (`TeamNum`) REFERENCES `total` (`TeamNum`);
