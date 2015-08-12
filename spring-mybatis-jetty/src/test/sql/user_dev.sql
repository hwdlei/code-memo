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
CREATE DATABASE IF NOT EXISTS `user_dev` /*!40100 DEFAULT CHARACTER SET utf8 */;
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

-- 正在导入表  user_dev.user 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`mid`, `uid`, `name`, `nick`, `gender`, `update_time`) VALUES
	(100, 1, '张三', '张三昵称', 0, '2014-03-21 15:40:40'),
	(101, 1, '李四', '李四昵称', 1, '2014-03-21 09:32:33'),
	(102, 1, '王五', '王五昵称', 0, '2014-03-21 09:33:27'),
	(103, 2, '张三马甲', 'Orz', 0, '2014-03-21 09:34:26');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
