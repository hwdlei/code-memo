-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.16 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 user_dev 的数据库结构
USE `user_dev`;

-- 导出  表 user_dev.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `mid` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `uid` bigint(20) unsigned NOT NULL COMMENT '客户id',
  `name` char(20) NOT NULL DEFAULT '' COMMENT '姓名',
  `nick` char(20) NOT NULL DEFAULT '' COMMENT '昵称',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别：1=男性；2=女性；0=未知',
  `update_time` datetime NOT NULL COMMENT '加入时间',
  PRIMARY KEY (`mid`),
  KEY `Index 2` (`uid`,`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
