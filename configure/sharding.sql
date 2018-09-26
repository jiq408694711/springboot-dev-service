CREATE DATABASE `dbtest2` /*!40100 DEFAULT CHARACTER SET utf8 */;

DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` varchar(32) DEFAULT NULL COMMENT '顺序编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` varchar(32) DEFAULT NULL COMMENT '顺序编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
