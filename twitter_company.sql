#创建数据库
create database twitter_company default charset utf8;
#采集twitter上对应的数据
CREATE TABLE `t_company` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) NOT NULL,
  `screen_name` varchar(100) DEFAULT NULL,
  `twitter_id` bigint(20) DEFAULT NULL,
  `location` text,
  `description` text,
  `url` varchar(1000) DEFAULT NULL,
  `followers_count` varchar(20) DEFAULT NULL,
  `friends_count` varchar(20) DEFAULT NULL,
  `listed_count` varchar(20) DEFAULT NULL,
  `create_at` varchar(50) DEFAULT NULL,
  `favourites_count` varchar(20) DEFAULT NULL,
  `verified` tinyint(1) DEFAULT NULL,
  `statuses_count` varchar(20) DEFAULT NULL,
  `state` enum('1','2','0') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62746 DEFAULT CHARSET=utf8

CREATE TABLE `t_company_followers` (
  `companyId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`companyId`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


#采集twitter上各个公司对应粉丝
CREATE TABLE `t_followers` (
  `userId` bigint(20) NOT NULL,
  `screen_name` varchar(50) DEFAULT NULL,
  `location` varchar(500) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `url` varchar(2000) DEFAULT NULL,
  `followers_count` varchar(20) DEFAULT NULL,
  `friends_count` varchar(20) DEFAULT NULL,
  `favourites_count` varchar(20) DEFAULT NULL,
  `verified` tinyint(1) DEFAULT NULL,
  `statuses_count` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8



