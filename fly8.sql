-- MySQL dump 10.13  Distrib 5.1.40, for Win64 (unknown)
--
-- Host: localhost    Database: fly8
-- ------------------------------------------------------
-- Server version	5.1.40-community-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES gbk */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bi_auto_start_job`
--

DROP TABLE IF EXISTS `bi_auto_start_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_auto_start_job` (
  `ID_AUTO_START_JOB` bigint(20) NOT NULL,
  `START_TYPE` int(11) DEFAULT NULL,
  `ARG_LOG_LEVEL` int(11) DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_AUTO_START_JOB`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_auto_start_job`
--

LOCK TABLES `bi_auto_start_job` WRITE;
/*!40000 ALTER TABLE `bi_auto_start_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_auto_start_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_auto_start_job_attr`
--

DROP TABLE IF EXISTS `bi_auto_start_job_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_auto_start_job_attr` (
  `BI_AUTO_START_JOB_ATTR` bigint(20) NOT NULL,
  `ID_ATTRIBUTE` int(11) DEFAULT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ATTR_TYPE` int(11) DEFAULT NULL,
  `ATTR_KEY` varchar(255) DEFAULT NULL,
  `ATTR_VALUE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BI_AUTO_START_JOB_ATTR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_auto_start_job_attr`
--

LOCK TABLES `bi_auto_start_job_attr` WRITE;
/*!40000 ALTER TABLE `bi_auto_start_job_attr` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_auto_start_job_attr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_filesys_attribute`
--

DROP TABLE IF EXISTS `bi_filesys_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_filesys_attribute` (
  `ID_FILESYS_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_FILESYS_DIRECTORY` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_FILESYS_ATTRIBUTE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_filesys_attribute`
--

LOCK TABLES `bi_filesys_attribute` WRITE;
/*!40000 ALTER TABLE `bi_filesys_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_filesys_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_filesys_directory`
--

DROP TABLE IF EXISTS `bi_filesys_directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_filesys_directory` (
  `ID_FS_DIRECTORY` bigint(20) NOT NULL,
  `FS_TYPE` int(11) DEFAULT NULL,
  `PATH` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `NOTES` mediumtext,
  PRIMARY KEY (`ID_FS_DIRECTORY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_filesys_directory`
--

LOCK TABLES `bi_filesys_directory` WRITE;
/*!40000 ALTER TABLE `bi_filesys_directory` DISABLE KEYS */;
INSERT INTO `bi_filesys_directory` VALUES (1,1,'C:\\_D','预测存储根目录','用于存放预测数据文件的根目录'),(2,1,'C:\\_D\\_doc','数据存储临时目录','用于存放数据文件的临时目录'),(3,2,'10.11.46.181','FTP测试接口机_181','FTP测试接口机_181'),(4,3,'10.11.46.181','SFTP测试接口机_181','SFTP测试接口机_181'),(5,4,'https://ciastudypattern.googlecode.com/svn/trunk/','存放文档的SVN','存放文档的SVN'),(6,5,'','存放文档的GIT','存放文档的GIT');
/*!40000 ALTER TABLE `bi_filesys_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_filesys_type`
--

DROP TABLE IF EXISTS `bi_filesys_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_filesys_type` (
  `ID_FS_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) NOT NULL,
  `DESCRIPTION` mediumtext NOT NULL,
  PRIMARY KEY (`ID_FS_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_filesys_type`
--

LOCK TABLES `bi_filesys_type` WRITE;
/*!40000 ALTER TABLE `bi_filesys_type` DISABLE KEYS */;
INSERT INTO `bi_filesys_type` VALUES (1,'local','服务器文件系统'),(2,'ftp','FTP文件系统'),(3,'sftp','SFTP文件系统'),(4,'svn','SVN服务器'),(5,'git','GIT服务器');
/*!40000 ALTER TABLE `bi_filesys_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_func_type`
--

DROP TABLE IF EXISTS `bi_func_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_func_type` (
  `ID_FUNC_TYPE` int(11) NOT NULL,
  `CODE` varchar(20) DEFAULT NULL,
  `MODULE_CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `ID_FUNC_TYPE_PARENT` int(11) DEFAULT NULL,
  `HELPTEXT` mediumtext,
  `TYPE_INDEX` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_FUNC_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_func_type`
--

LOCK TABLES `bi_func_type` WRITE;
/*!40000 ALTER TABLE `bi_func_type` DISABLE KEYS */;
INSERT INTO `bi_func_type` VALUES (0,'root','SYS.Base','根',NULL,'功能的根节点',0),(1,'di','DI.Base','数据整合',0,'一级节点：转换/作业',3),(2,'db','DB.Base','数据源',0,'一级节点：数据源',0),(4,'systools','SYS.Base','功能',0,'一级节点：功能',5),(50,'privilege','SYS.Base','用户权限',4,'二级节点：功能-用户权限',0),(501,'createuser','SYS.Base','创建用户',50,'三级节点：功能-用户权限-创建用户',0),(502,'deluser','SYS.Base','删除用户',50,'三级节点：功能-用户权限-删除用户',1),(503,'edituser','SYS.Base','编辑当前用户',50,'三级节点：功能-用户权限-编辑当前用户',2),(60,'repository','SYS.Base','资源库',4,'二级节点：功能-资源库',1),(601,'connrep','SYS.Base','连接资源库',60,'三级节点：功能-资源库-连接资源库',0),(602,'findrep','SYS.Base','探测资源库',60,'三级节点：功能-资源库-探测资源库',1),(603,'createrep','SYS.Base','创建资源库',60,'三级节点：功能-资源库-创建资源库',2),(70,'file','SYS.Base','文件',4,'二级节点：功能-文件',2),(701,'newtrans','DI.Trans.Editor','新建转换',70,'三级节点：功能-文件-新建转换',0),(702,'newjob','DI.Job.Editor','新建作业',70,'三级节点：功能-文件-新建作业',1),(703,'newdb','DB.Advance','添加数据库',70,'三级节点：功能-文件-添加数据库',2),(704,'newfs','FS.Local,FS.FTP','添加文件系统',70,'三级节点：功能-文件-添加文件系统',3),(80,'operate','SYS.Base','操作',4,'二级节点：功能-操作',3),(705,'open','SYS.Advance','打开',70,'三级节点：功能-文件-打开',4),(706,'fileopen','SYS.Advance','从文件打开',70,'三级节点：功能-文件-从文件打开',5),(707,'downloadzip','SYS.Advance','打包下载',70,'三级节点：功能-文件-打包下载',6),(708,'uploadfile','SYS.Advance','上传文件',70,'三级节点：功能-文件-上传文件',7),(90,'wizard','SYS.Base','向导',4,'二级节点：功能-向导',4),(901,'wcreatedb','DB.Advance','创建数据库连接',90,'三级节点：功能-向导-创建数据库连接',0),(902,'wsinglecopy','DB.Advance','单表复制向导',90,'三级节点：功能-向导-单表复制向导',1),(903,'wmulticopy','DB.Advance','多表复制向导',90,'三级节点：功能-向导-多表复制向导',2),(100,'help','SYS.Base','帮助',4,'二级节点：功能-帮助',5),(1001,'showstepplugin','DI.Trans.Editor','显示步骤插件',100,'三级节点：功能-帮助-显示步骤插件',0),(1002,'showjobplugin','DI.Job.Editor','显示作业插件',100,'三级节点：功能-帮助-显示作业插件',1),(1010,'about','SYS.Base','关于',100,'三级节点：功能-帮助-关于',3),(3,'smart','SM.Base','语义模型',0,'一级节点：语义层',1),(5,'fs','FS.Base','文件系统',0,'一级节点：文件系统',4),(6,'report','BA.Base','商业分析',0,'一级节点：报表',2),(1003,'showdbplugin','DB.Advance','显示数据库插件',100,'三级节点：功能-帮助-显示数据库插件',2),(801,'timedexecution','SYS.Advance','定时执行',80,'三级节点：功能-操作-定时执行',NULL),(802,'automatingjobs','DI.Job.Editor','自动执行作业',80,'三级节点：功能-操作-自动执行作业',NULL);
/*!40000 ALTER TABLE `bi_func_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_func_type_attribute`
--

DROP TABLE IF EXISTS `bi_func_type_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_func_type_attribute` (
  `ID_FUNC_TYPE_ATTR` bigint(20) NOT NULL,
  `ID_FUNC_TYPE` int(11) NOT NULL,
  `CODE` varchar(255) NOT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_FUNC_TYPE_ATTR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_func_type_attribute`
--

LOCK TABLES `bi_func_type_attribute` WRITE;
/*!40000 ALTER TABLE `bi_func_type_attribute` DISABLE KEYS */;
INSERT INTO `bi_func_type_attribute` VALUES (0,1010,'url','rest/systools/about'),(1,1010,'header','关于'),(2,1010,'width','420'),(3,1010,'height','480'),(4,501,'header','创建用户'),(5,501,'width','400'),(6,501,'height','300'),(7,501,'url','rest/user/create'),(8,501,'btn','[{\"label\":\"确定\",\"title\":\"确定\",\"url\":\"rest/user/create/save\"},{\"type\":\"cancel\"}]'),(9,1001,'url','rest/systools/stepplugin'),(10,1001,'header','步骤插件'),(11,1001,'height','480'),(12,1001,'width','620'),(13,1002,'url','rest/systools/jobplugin'),(14,1002,'header','作业插件'),(15,1002,'height','480'),(16,1002,'width','620'),(17,1003,'url','rest/systools/dbplugin'),(18,1003,'header','数据库插件'),(19,1003,'height','480'),(20,1003,'width','620');
/*!40000 ALTER TABLE `bi_func_type_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_metro`
--

DROP TABLE IF EXISTS `bi_metro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_metro` (
  `ID_METRO` bigint(20) NOT NULL,
  `METRO_OBJECT` mediumtext,
  `METRO_TYPE` int(11) DEFAULT NULL,
  `IS_REF` char(1) DEFAULT NULL,
  `REF_URL` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `VALID` char(1) DEFAULT NULL,
  `CREATE_USER` varchar(255) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_METRO`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_metro`
--

LOCK TABLES `bi_metro` WRITE;
/*!40000 ALTER TABLE `bi_metro` DISABLE KEYS */;
INSERT INTO `bi_metro` VALUES (1,'记事本1\r\n写点什么',1,'N',NULL,'记事本1','Y',NULL,NULL,NULL,NULL),(2,'记事本2\r\n再写点什么',1,'N',NULL,'记事本2','Y',NULL,NULL,NULL,NULL),(3,'',0,'N',NULL,'图片1','Y',NULL,NULL,NULL,NULL),(4,NULL,0,'N',NULL,'图片2','Y',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bi_metro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_metro_attribute`
--

DROP TABLE IF EXISTS `bi_metro_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_metro_attribute` (
  `ID_METRO_ATTR` bigint(20) NOT NULL,
  `ID_METRO` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_METRO_ATTR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_metro_attribute`
--

LOCK TABLES `bi_metro_attribute` WRITE;
/*!40000 ALTER TABLE `bi_metro_attribute` DISABLE KEYS */;
INSERT INTO `bi_metro_attribute` VALUES (1,1,'id','note1'),(2,1,'backgroundCls','azul'),(3,1,'colspan','3'),(4,1,'rowspan','2'),(5,1,'text','记事板1'),(6,1,'textBackgroundCls','info'),(7,2,'id','note2'),(8,2,'backgroundCls','verde'),(9,2,'colspan','2'),(10,2,'rowspan','2'),(11,2,'text','记事板2'),(12,2,'textBackgroundCls','roxo'),(13,3,'id','image1'),(14,3,'backgroundImg','resources/images/metro/test.jpg'),(15,3,'colspan','2'),(16,4,'id','image2'),(17,4,'backgroundImg','resources/images/metro/test2.jpg'),(18,4,'colspan','2');
/*!40000 ALTER TABLE `bi_metro_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_metro_type`
--

DROP TABLE IF EXISTS `bi_metro_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_metro_type` (
  `ID_METRO_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  PRIMARY KEY (`ID_METRO_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_metro_type`
--

LOCK TABLES `bi_metro_type` WRITE;
/*!40000 ALTER TABLE `bi_metro_type` DISABLE KEYS */;
INSERT INTO `bi_metro_type` VALUES (1,'note','记事本'),(2,'cycle','图片集'),(3,'report','统计报表'),(9,'custom','自定义');
/*!40000 ALTER TABLE `bi_metro_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_msg`
--

DROP TABLE IF EXISTS `bi_msg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_msg` (
  `ID_MSG` bigint(20) NOT NULL,
  `ID_MSG_CHANNEL` int(11) DEFAULT NULL,
  `STATUS` varchar(1) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  `URL` varchar(255) DEFAULT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `EXTEND_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_MSG`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_msg`
--

LOCK TABLES `bi_msg` WRITE;
/*!40000 ALTER TABLE `bi_msg` DISABLE KEYS */;
INSERT INTO `bi_msg` VALUES (0,0,'Y','请于2013-10-31之前，填报10月份预测文件。','rest/aa?year=2013&month=10','2013-10-01 00:00:10','2013-10-31 23:23:59'),(1,1,'Y','请于2013-10-10之前，填报10月份上旬延伸性预测信息。','rest/aa?year=2013&month=10&xun=1','2013-10-01 00:00:10','2013-10-10 23:23:59'),(2,1,'Y','请于2013-10-20之前，填报10月份中旬延伸性预测信息。','rest/aa?year=2013&month=10&xun=2','2013-10-10 00:00:10','2013-10-20 23:23:59');
/*!40000 ALTER TABLE `bi_msg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_msg_channel`
--

DROP TABLE IF EXISTS `bi_msg_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_msg_channel` (
  `ID_MSG_CHANNEL` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `AUTHENTICATE` varchar(1) DEFAULT NULL,
  `STATUS` varchar(1) DEFAULT NULL,
  `VALUE_TEMPLATE` mediumtext,
  `URL_TEMPLATE` varchar(255) DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_MSG_CHANNEL`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_msg_channel`
--

LOCK TABLES `bi_msg_channel` WRITE;
/*!40000 ALTER TABLE `bi_msg_channel` DISABLE KEYS */;
INSERT INTO `bi_msg_channel` VALUES (0,'月预测提醒','每个月将生成一条月预测提醒','N','Y','请于${dataText}之前，填报${month}月份预测文件。','rest/aa?year=${year}&month=${month}',NULL,NULL,NULL,NULL),(1,'延伸性预测提醒','每个月上中下三旬填报延伸性预测提醒','N','Y','请于${dataText}之前，填报${month}月份${xunText}延伸性预测信息。','rest/aa?year=${year}&month=${month}&xun=${xun}',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bi_msg_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_portal_action`
--

DROP TABLE IF EXISTS `bi_portal_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_portal_action` (
  `ID_PORTAL_ACTION` bigint(20) NOT NULL,
  `DESCRIPTION` mediumtext,
  `BEAN_NAME` varchar(255) DEFAULT NULL,
  `METHOD` varchar(255) DEFAULT NULL,
  `IS_SYSTEM` char(1) DEFAULT 'N',
  PRIMARY KEY (`ID_PORTAL_ACTION`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_portal_action`
--

LOCK TABLES `bi_portal_action` WRITE;
/*!40000 ALTER TABLE `bi_portal_action` DISABLE KEYS */;
INSERT INTO `bi_portal_action` VALUES (0,'Metro','cust.service.forecastServices','metroPortal','Y'),(2,'访问报表资源','bi.resource.pivotResource','getResourceForPortal','Y'),(3,'展示报表图片','bi.resource.pivotResource','showImageForPortal','Y'),(1,'打开报表页面','bi.resource.pivotResource','openPivotForPortal','Y');
/*!40000 ALTER TABLE `bi_portal_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_portal_menu`
--

DROP TABLE IF EXISTS `bi_portal_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_portal_menu` (
  `ID_PORTAL_MENU` int(11) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `MODULE_CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `ID_PORTAL_MENU_PARENT` int(11) DEFAULT NULL,
  `HELPTEXT` mediumtext,
  `MENU_INDEX` int(11) DEFAULT NULL,
  `AUTHENTICATE` char(1) DEFAULT 'Y',
  `VALID` char(1) DEFAULT 'Y',
  PRIMARY KEY (`ID_PORTAL_MENU`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_portal_menu`
--

LOCK TABLES `bi_portal_menu` WRITE;
/*!40000 ALTER TABLE `bi_portal_menu` DISABLE KEYS */;
INSERT INTO `bi_portal_menu` VALUES (0,'root','Anomyous','企业级Portal根目录',-1,'企业级Portal根目录',NULL,'N','Y'),(1,'webRoot','Anomyous','互联网Portal根目录',-1,'互联网Portal根目录',NULL,'N','Y'),(1001,'weekReport','Anomyous','经营分析周报',1,NULL,0,'N','Y'),(1011,'overallHealth','Anomyous','整体健康度',1006,NULL,0,'N','Y'),(1012,'keyProductAnalysis','Anomyous','重点产品分析',1006,NULL,1,'N','Y'),(1013,'pcGameOP','Anomyous','端游运营分析',1007,NULL,2,'N','Y'),(1014,'pcGameRD','Anomyous','端游研发状态',1007,NULL,3,'N','Y'),(1015,'pageGameOP','Anomyous','页游运营分析',1007,NULL,4,'N','Y'),(1016,'pageGameRD','Anomyous','页游研发与海外代理',1007,NULL,5,'N','Y'),(1017,'7RoadOP','Anomyous','7Road上线产品及代理运营收入',1007,NULL,6,'N','Y'),(1018,'7RoadRD','Anomyous','7Road研发项目状态',1007,NULL,7,'N','Y'),(1019,'mobogenie','Anomyous','Mobogenie',1007,NULL,8,'N','Y'),(1020,'17173OP','Anomyous','17173总体经营分析',1007,NULL,9,'N','Y'),(1021,'17173MediaVideo','Anomyous','17173媒体和视频',1007,NULL,10,'N','Y'),(1022,'17173PC','Anomyous','17173PC客户端',1007,NULL,11,'N','Y'),(1023,'17173APP','Anomyous','17173移动APP',1007,NULL,12,'N','Y'),(1024,'17173RD','Anomyous','17173研发中项目',1007,NULL,13,'N','Y'),(1025,'webRD','Anomyous','网络应用研发部',1007,NULL,14,'N','Y'),(1006,'overall','Anomyous','整体',1001,NULL,0,'N','Y'),(1007,'others','Anomyous','其他',1001,NULL,1,'N','Y');
/*!40000 ALTER TABLE `bi_portal_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_portal_menu_attribute`
--

DROP TABLE IF EXISTS `bi_portal_menu_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_portal_menu_attribute` (
  `ID_PORTAL_MENU_ATTR` int(11) NOT NULL,
  `ID_PORTAL_MENU` int(11) NOT NULL,
  `CODE` varchar(255) NOT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_PORTAL_MENU_ATTR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_portal_menu_attribute`
--

LOCK TABLES `bi_portal_menu_attribute` WRITE;
/*!40000 ALTER TABLE `bi_portal_menu_attribute` DISABLE KEYS */;
INSERT INTO `bi_portal_menu_attribute` VALUES (60,1011,'action','1'),(61,1011,'param','11'),(62,1012,'action','1'),(63,1012,'param','12'),(64,1013,'action','1'),(65,1013,'param','13'),(66,1014,'action','1'),(67,1014,'param','14'),(68,1015,'action','1'),(69,1015,'param','15'),(70,1016,'action','1'),(71,1016,'param','16'),(72,1017,'action','1'),(73,1017,'param','17'),(74,1018,'action','1'),(75,1018,'param','18'),(76,1019,'action','1'),(77,1019,'param','19'),(78,1020,'action','1'),(79,1020,'param','20'),(80,1021,'action','1'),(81,1021,'param','21'),(82,1022,'action','1'),(83,1022,'param','22'),(84,1023,'action','1'),(85,1023,'param','23'),(86,1024,'action','1'),(87,1024,'param','24'),(88,1025,'action','1'),(89,1025,'param','25');
/*!40000 ALTER TABLE `bi_portal_menu_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_report`
--

DROP TABLE IF EXISTS `bi_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_report` (
  `ID_REPORT` bigint(20) NOT NULL,
  `ID_REPORT_DIRECTORY` int(11) DEFAULT NULL,
  `REPORT_OBJECT` mediumtext,
  `REPORT_TYPE` int(11) DEFAULT NULL,
  `IS_REF` char(1) DEFAULT NULL,
  `ID_REF_REPORT` int(11) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `REPORT_VERSION` varchar(255) DEFAULT NULL,
  `REPORT_STATUS` int(11) DEFAULT NULL,
  `CREATE_USER` varchar(255) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_REPORT`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_report`
--

LOCK TABLES `bi_report` WRITE;
/*!40000 ALTER TABLE `bi_report` DISABLE KEYS */;
INSERT INTO `bi_report` VALUES (0,1,'<fly:composition width=\"600\" height=\"400\">\r\n	<fly:verticalLayout>\r\n		<fly:fieldSet title=\"标识符\">\r\n			<fly:gridLayout column=\"4\" itemWidth=\"35%,15%,35%,15%\">\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" text=\"支持布尔数据类型\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" name=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:QUOTE_ALL_FIELDS\" text=\"标识符使用引号括起来\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:QUOTE_ALL_FIELDS\" name=\"${formId}:QUOTE_ALL_FIELDS\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" text=\"强制标识符使用小写字母\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" text=\"强制标识符使用大写字母\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n			</fly:gridLayout>\r\n		</fly:fieldSet>\r\n		\r\n		<fly:labelObject buddy=\"${formId}:preferredSchemaName\" text=\"默认模式名称（在没有其他模式名时使用）\" />\r\n		\r\n		<fly:inputText id=\"${formId}:preferredSchemaName\" name=\"${formId}:preferredSchemaName\" type=\"text\" value=\"\" />\r\n		\r\n		<fly:labelObject buddy=\"${formId}:connectionType\" text=\"请输入连接成功后要执行的SQL语句，用分号(;)隔开\" />\r\n		\r\n		<textarea id=\"${formId}:connectSQL\" name=\"${formId}:connectSQL\" style=\"width:100%;\" rows=\"6\">\r\n			\r\n		</textarea>\r\n		\r\n	</fly:verticalLayout>\r\n\r\n	<fly:actions>\r\n\r\n		<fly:action sender=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" signal=\"change\" accepter=\"${formId}:preferredSchemaName\" slot=\"disable\" />\r\n\r\n	</fly:actions>\r\n\r\n</fly:composition>',1,'N',NULL,'Dashboard例子',NULL,NULL,NULL,NULL,NULL,NULL),(11,1,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<PivotReport xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"PivotReport.xsd\" showHScroll=\"N\" showVScroll=\"N\" offsetCellNumber=\"0\">\r\n	<Context>\r\n		\r\n	</Context>\r\n\r\n	<Style name=\"title\" align=\"2\" valign=\"2\" fontSize=\"22px\" fontWeight=\"2\" />\r\n	<Style name=\"subtitle\" valign=\"2\" />\r\n\r\n	<Sheet sheetName=\"整体健康度\" defaultColWidth=\"70\" defaultRowHeight=\"25\" colNum=\"11\" showColHead=\"N\" showRowHead=\"N\" showGrid=\"N\">\r\n		<StartPosition cidx=\"5\" ridx=\"3\"/>\r\n		<RowsHeight>\r\n			<elememt idx=\"0\" size=\"10\"/>\r\n			<elememt idx=\"1\" size=\"30\"/>\r\n		</RowsHeight>\r\n		<ColsWidth>\r\n			<elememt idx=\"0\" size=\"10\"/>\r\n			<elememt idx=\"3\" size=\"80\"/>\r\n		</ColsWidth>\r\n		<Region name=\"table\">\r\n			<StartPosition cidx=\"1\" ridx=\"1\"/>\r\n			<Table>\r\n				<Normal>\r\n					<data>\r\n						<row>\r\n							<cell colspan=\"5\" rowspan=\"1\" value=\"畅游-各事业部经营分析周报\" _TAG=\"title\" />\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"——COO卓越运作中心 v1.0\" _TAG=\"subtitle\" />\r\n							<cell colspan=\"3\" rowspan=\"1\" fontColor=\"red\" value=\"第19周（20140505-20140511）\" _TAG=\"subtitle\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"经营指标健康度\" borderTopStyle=\"1\" borderLeftStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"5\" format=\"star\" borderTopStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n							<cell colspan=\"8\" rowspan=\"3\" borderTopStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\" value=\"“☆☆☆☆☆”表示超预期“☆☆☆☆”表示健康“☆☆☆”表示滞后（偏差20%）“☆☆”表示偏差过大（40%）“☆”表示不及格（偏差50%以上）\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"研发项目健康度\" borderLeftStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"4\" format=\"star\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"重点工作健康度\" borderLeftStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"4\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" fontSize=\"16\" fontWeight=\"2\" value=\"本周总结：畅游收入Q2累计达成总表\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"14\">\r\n							<cell colspan=\"11\" rowspan=\"14\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"1、游戏事业部每周收入看盘\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"17\">\r\n							<cell colspan=\"11\" rowspan=\"17\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"2、端游近两年历史收入趋势\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"10\">\r\n							<cell colspan=\"11\" rowspan=\"10\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"3、第七大道近两年收入趋势\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"10\">\r\n							<cell colspan=\"11\" rowspan=\"10\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"4、第七大道各游戏每周收入看盘\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"13\">\r\n							<cell colspan=\"11\" rowspan=\"13\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"5、Mobogenie资源分发量趋势\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"10\">\r\n							<cell colspan=\"11\" rowspan=\"10\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"6、各事业部月度正式员工变动表\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"10\">\r\n							<cell colspan=\"11\" rowspan=\"10\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"7、各事业部月度人均收入分析表\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"10\">\r\n							<cell colspan=\"11\" rowspan=\"10\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderTopStyle=\"1\" value=\"8、各事业部人工费与市场费结构分析\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"10\">\r\n							<cell colspan=\"11\" rowspan=\"10\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n						<row num=\"3\">\r\n							<cell colspan=\"11\" rowspan=\"3\" borderLeftStyle=\"1\" borderRightStyle=\"1\" borderBottomStyle=\"1\" backgroundColor=\"daeef3\"/>\r\n						</row>\r\n					</data>\r\n				</Normal>\r\n			</Table>\r\n		</Region>\r\n		<Region name=\"chartAll\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"8\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"21\"/>\r\n			<Chart type=\"line\">\r\n				<chart>\r\n					<title text=\"畅游整体收入Q2累计达成\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"4-6\", \"4-13\", \"4-20\", \"4-27\", \"5-4\", \"5-11\", \"5-18\", \"5-25\", \"6-1\", \"6-8\", \"6-15\", \"6-22\", \"6-29\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis>\r\n						<labels>\r\n							<formatter>\r\n								return (this.value*100) + \"%\";\r\n							</formatter>\r\n						</labels>\r\n					</yAxis>\r\n					<tooltip>\r\n						<formatter>\r\n							return \'&lt;span style=\"font-size:10px\"&gt;\' + this.point.category + \'&lt;/span&gt;&lt;table&gt;\' +\r\n											\'&lt;tr&gt;&lt;td style=\"color:\' + this.series.color + \';padding:0\">\' + this.series.name + \': &lt;/td&gt;\' +\r\n											\'&lt;td style=\"padding:0\"&gt;&lt;b&gt;\' + (this.point.y*100).toFixed(0) + \'%&lt;/b>&lt;/td&gt;&lt;/tr&gt;\' +\r\n											\'&lt;/table&gt;\';\r\n						</formatter>\r\n					</tooltip>\r\n					<series>\r\n						<item name=\"畅游总收入达成率\">\r\n							<data>[0.05, 0.1, 0.15, 0.21, 0.28, 0.36]</data>\r\n						</item>\r\n						<item name=\"时间进度\">\r\n							<data>[0.07, 0.14, 0.22, 0.3, 0.37, 0.45, 0.53, 0.6, 0.68, 0.76, 0.84, 0.91, 0.99]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart1\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"27\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"43\"/>\r\n			<Chart type=\"barStackedPercent\">\r\n				<chart>\r\n					<title text=\"畅游各事业部Q2收入达成\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"畅游总收入达成率\", \"端游运营\", \"端游研发\", \"页游研发\", \"wan事业部\", \"手游运营\", \"第七大道\", \"173媒体\", \"173视频移动事业部\", \"Webjet\", \"晶茂\", \"海外子公司-北美片区\", \"RC事业部\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis>\r\n						<labels>\r\n							<formatter>\r\n								return this.value + \"%\";\r\n							</formatter>\r\n						</labels>\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"Q2实际达成\">\r\n							<data>[68582, 43870, 591, 897, 1487, 298, 10274, 8818, 5, 6, 1054, 754, 528]</data>\r\n						</item>\r\n						<item name=\"Q2预算（事业部层面）\">\r\n							<data>[122781, 87756, 865, 1891, 3670, 4734, 12951, 5810, 150, 7, 2739, 1698, 510]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart2\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"49\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"58\"/>\r\n			<Chart type=\"line\">\r\n				<chart>\r\n					<title text=\"端游运营2014年趋势分析\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"1月\", \"2月\", \"3月\", \"4月\", \"5月\", \"6月\", \"7月\", \"8月\", \"9月\", \"10月\", \"11月\", \"12月\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis min=\"20000\">\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"2013年实际\">\r\n							<data>[38120, 38523, 53422, 31023, 46241, 50212, 31800, 46700, 41700, 31900, 50800, 49800]</data>\r\n						</item>\r\n						<item name=\"2014年预算\">\r\n							<data>[31420, 41412, 52428, 43623, 43803, 44200]</data>\r\n						</item>\r\n						<item name=\"2014年实际\">\r\n							<data>[33651, 40147, 54022, 26980]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart3\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"64\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"73\"/>\r\n			<Chart type=\"line\">\r\n				<chart>\r\n					<title text=\"第七大道2014年趋势分析\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"1月\", \"2月\", \"3月\", \"4月\", \"5月\", \"6月\", \"7月\", \"8月\", \"9月\", \"10月\", \"11月\", \"12月\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis min=\"20000\">\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"2013年实际\">\r\n							<data>[38120, 38523, 53422, 31023, 46241, 50212, 31800, 46700, 41700, 31900, 50800, 49800]</data>\r\n						</item>\r\n						<item name=\"2014年预算\">\r\n							<data>[31420, 41412, 52428, 43623, 43803, 44200]</data>\r\n						</item>\r\n						<item name=\"2014年实际\">\r\n							<data>[33651, 40147, 54022, 26980]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart4\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"79\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"91\"/>\r\n			<Chart type=\"barPlacement\">\r\n				<chart>\r\n					<title text=\"\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"全部游戏\", \"神曲\", \"弹弹堂\", \"剑影\", \"神创天下\", \"新海神\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis>\r\n						<labels enabled=\"N\" />\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"已完成收入\" pointPadding=\"0\">\r\n							<data>[0.3608, 0.3684, 0.4049, 0.2219, 0.3233, 0.2042]</data>\r\n							<displayData>[\"9192598(36.08%)\", \"6448899(36.84%)\", \"1782451(40.49%)\", \"419654(22.19%)\", \"539023(32.33%)\", \"2570(20.42%)\"]</displayData>\r\n						</item>\r\n						<item name=\"预测收入\" pointPadding=\"0.2\">\r\n							<data>[0.9687, 1.0042, 1.0769, 0.4802, 0.8669, 0.5262]</data>\r\n							<displayData>[\"24680022(96.87%)\", \"17578000(100.42%)\", \"5302500(107.69%)\", \"908286(48.02%)\", \"1445296(86.69%)\", \"6625(52.62%)\"]</displayData>\r\n						</item>\r\n						<item name=\"计划完成值\" pointPadding=\"0.4\">\r\n							<data>[1, 1, 1, 1, 1, 1]</data>\r\n							<displayData>[25478689, 17504580, 4402688, 1891561, 1667268, 12590]</displayData>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart5\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"97\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"106\"/>\r\n			<Chart type=\"line\">\r\n				<chart>\r\n					<title text=\"\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"1月\", \"2月\", \"3月\", \"4月\", \"5月\", \"6月\", \"7月\", \"8月\", \"9月\", \"10月\", \"11月\", \"12月\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis min=\"20000\">\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"2013年实际\">\r\n							<data>[38120, 38523, 53422, 31023, 46241, 50212, 31800, 46700, 41700, 31900, 50800, 49800]</data>\r\n						</item>\r\n						<item name=\"2014年预算\">\r\n							<data>[31420, 41412, 52428, 43623, 43803, 44200]</data>\r\n						</item>\r\n						<item name=\"2014年实际\">\r\n							<data>[33651, 40147, 54022, 26980]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart6\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"112\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"121\"/>\r\n			<Chart type=\"combo\">\r\n				<chart>\r\n					<title text=\"各事业部人员变动情况\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"端游研发\", \"端游运营\", \"页游研发\", \"页游运营\", \"移动研发\", \"移动运营\", \"17173\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis>\r\n						<title text=\"\" />\r\n					</yAxis>\r\n					<yAxis opposite=\"Y\">\r\n						<title text=\"\" />\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"3月实际人数\" yAxis=\"1\" type=\"column\">\r\n							<data>[940, 481, 312, 91,506, 191, 1037]</data>\r\n						</item>\r\n						<item name=\"4月实际人数\" yAxis=\"1\" type=\"column\">\r\n							<data>[922, 461, 299, 90, 457, 160, 1055]</data>\r\n						</item>\r\n						<item name=\"人员变动趋势\" type=\"line\">\r\n							<data>[-18, -20, -13, -1, -49, -31, 18]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart7\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"127\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"136\"/>\r\n			<Chart type=\"column\">\r\n				<chart>\r\n					<title text=\"各事业部4月份人均收入、人均利润\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"端游研发\", \"端游运营\", \"页游研发\", \"页游运营\", \"移动研发\", \"移动运营\", \"17173\"]\r\n						</categories>\r\n					</xAxis>\r\n					<series>\r\n						<item name=\"4月份人均收入\">\r\n							<data>[13.87, 26.23, 2.09, 8.92, 0.03, 0.67, 2.66]</data>\r\n						</item>\r\n						<item name=\"4月份人均利润\">\r\n							<data>[6.39, 13.12, -7.11, -13.73, -5.71, -4.63, -3.31]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart8\" margin=\"5\">\r\n			<StartPosition cidx=\"1\" ridx=\"142\"/>\r\n			<EndPosition cidx=\"11\" ridx=\"151\"/>\r\n			<Chart type=\"columnStackedPercent\">\r\n				<chart>\r\n					<title text=\"事业部4月份人工费和市场费占比\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"端游运营\", \"页游运营\", \"移动运营\", \"端游研发\", \"页游研发\", \"移动研发\", \"17173\"]\r\n						</categories>\r\n					</xAxis>\r\n					<series>\r\n						<item name=\"市场费占比\">\r\n							<data>[1326, 943, 45, 0, 371, 0, 1884]</data>\r\n						</item>\r\n						<item name=\"人工费占比\">\r\n							<data>[1586, 310, 604, 3384, 1033, 1737, 2916]</data>\r\n						</item>\r\n						<item name=\"其他占比\">\r\n							<data>[1072, 212, 503, 2466, 1590, 1344, 1077]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n	</Sheet>\r\n</PivotReport>\r\n',3,'N',NULL,'整体健康度',NULL,NULL,NULL,NULL,NULL,NULL),(12,1,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<PivotReport xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"PivotReport.xsd\" showHScroll=\"N\" showVScroll=\"N\" offsetCellNumber=\"0\">\r\n	<Context type=\"Picture\" name=\"logo\" ext=\"png\">\r\n\r\n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\r\n\r\n	</Context>\r\n	\r\n	<Style name=\"title\" align=\"2\" valign=\"2\" fontSize=\"22px\" fontWeight=\"2\" />\r\n	<Style name=\"subtitle\" valign=\"2\" />\r\n	\r\n	<Style name=\"th1\" backgroundColor=\"#00b050\" fontColor=\"#fff\" fontWeight=\"2\" align=\"1\" />\r\n	<Style name=\"tb1-odd\" backgroundColor=\"#fff\" />\r\n	<Style name=\"tb1-even\" backgroundColor=\"#f2f2f2\" />\r\n	<Style name=\"th2\" fontColor=\"#666\" fontWeight=\"2\" borderTopStyle=\"2\" borderTopColor=\"#666\" borderBottomStyle=\"2\" borderBottomColor=\"#666\" />\r\n	\r\n	<Style name=\"chartTitle\" fontWeight=\"2\" fontSize=\"1.2em\" align=\"1\" />\r\n	<Style name=\"chartSubtitle\" fontSize=\"0.8em\" align=\"1\" />\r\n	\r\n	<DataFormat name=\"health\" type=\"icon\">\r\n		<condition icon=\"flag-01\" oper1=\"5\" val1=\"0.67\" />\r\n		<condition icon=\"flag-03\" oper1=\"2\" val1=\"0.67\" oper2=\"5\" val2=\"0.33\" />\r\n		<condition icon=\"flag-02\" oper1=\"2\" val1=\"0.33\" />\r\n	</DataFormat>\r\n	\r\n	<Sheet sheetName=\"重点产品分析\" defaultColWidth=\"70\" defaultRowHeight=\"25\" colNum=\"11\" showColHead=\"N\" showRowHead=\"N\" showGrid=\"N\">\r\n		<StartPosition cidx=\"5\" ridx=\"3\"/>\r\n		<RowsHeight>\r\n			<elememt idx=\"0\" size=\"50\"/>\r\n		</RowsHeight>\r\n		<ColsWidth>\r\n		</ColsWidth>\r\n		<Region name=\"table\">\r\n			<StartPosition cidx=\"0\" ridx=\"0\"/>\r\n			<Table>\r\n				<Normal>\r\n					<data>\r\n						<row>\r\n							<cell colspan=\"6\" rowspan=\"1\" value=\"2014年重点产品分析\" _TAG=\"title\"/>\r\n							<cell colspan=\"5\" rowspan=\"1\" value=\"——COO卓越运作中心\" _TAG=\"subtitle\"/>\r\n						</row>\r\n						<row/>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"主要重点产品-已上线\" _TAG=\"th1\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"健康度\" _TAG=\"th1\"/>\r\n							<cell colspan=\"4\" rowspan=\"1\" value=\"重点经营指标（KPI）\" _TAG=\"th1\"/>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"重点经营指标（KPI）\" _TAG=\"th1\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"Mobogenie\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"-0.21\" dataFormatRefs=\"health\" valueFormat=\"toPercent(value)\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"资源分发达成率\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"0.79\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"每日累计预算目标\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"1\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"odd\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"17173移动端\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"\" dataFormatRefs=\"health\" valueFormat=\"toPercent(value)\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"MAU目标达成率\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"月度目标时间进度\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"0.35\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"even\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"17173视频业务\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"0.4\" dataFormatRefs=\"health\" valueFormat=\"toPercent(value)\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"MAU目标达成率\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"0.42\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"月累计时间进度\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"0.35\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"odd\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"17173浏览器\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"\" dataFormatRefs=\"health\" valueFormat=\"toPercent(value)\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"MAU目标达成率\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"月累计时间进度\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"0.35\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"even\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"RC海外PC\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"-0.01\" dataFormatRefs=\"health\" valueFormat=\"toPercent(value)\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"Q2 周均DAU目标达成率\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"0.99\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"odd\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"Q2 DAU目标达成率\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"1\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"odd\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"TK坦克世界\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"-0.11\" dataFormatRefs=\"health\" valueFormat=\"toPercent(value)\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"月收入达成率\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"0.31\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"even\"/>\r\n							<cell colspan=\"2\" rowspan=\"1\" value=\"月度目标时间进度\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"0.35\" _TAG=\"tb1\" valueFormat=\"toPercent(value)\" style=\"even\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"\" />\r\n							<cell colspan=\"10\" rowspan=\"1\" expand=\"Y\" value=\"主要风险：人员缺口10人，俄罗斯5.6版本缺动画设计资源，影响开发进度；腾讯版策划案交付延期率基本可控，策划瓶颈基本解除\" />\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"主要重点产品-未上线\" _TAG=\"th1\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"所处阶段\" _TAG=\"th1\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" value=\"主要项目进展\" _TAG=\"th1\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"秦时明月\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"CCB2\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" expand=\"Y\" value=\"5月9日首爆后，官网点击是1W多，目标值是2-3W；删档测试ROI大概在10%左右，效果偏低\" _TAG=\"tb1\" style=\"odd\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"端游运营代理三款产品\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" expand=\"Y\" value=\"\" _TAG=\"tb1\" style=\"even\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"——幻想神域\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"CCB2\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" expand=\"Y\" value=\"5月15日完成技术和版本测试，5月23日CCB2测试开始，CCB2测试时间5月23日-31日，比原计划增加两天\" _TAG=\"tb1\" style=\"odd\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"——EOS\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"CCB1\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" expand=\"Y\" value=\"EOS复盘：采取饥饿营销，放出激活码1596个，测试期间8天共登录1571个账号，回访率没达到预期目标；官网问卷做的不好，没有筛出核心用户\" _TAG=\"tb1\" style=\"even\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"——ASTA\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"启宣\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" expand=\"Y\" value=\"5月14号提供包括CCB1版本的全部功能的版本，组织集中测试体验新版本；\" _TAG=\"tb1\" style=\"odd\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"朋游\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"英文版上线\" _TAG=\"tb1\" style=\"even\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" expand=\"Y\" value=\"完成英文版客户端，本周开始上架MOBO的印尼、泰国、越南地区\" _TAG=\"tb1\" style=\"even\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"3\" rowspan=\"1\" value=\"RC移动端\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"1\" rowspan=\"1\" value=\"市场研究和策略分解\" _TAG=\"tb1\" style=\"odd\"/>\r\n							<cell colspan=\"7\" rowspan=\"1\" expand=\"Y\" value=\"对标YY、QT发展路径，分析海外市场发展机会，制定RC海外策略，细化分解马来西亚切入策略。\" _TAG=\"tb1\" style=\"odd\"/>\r\n						</row>\r\n						<row />\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" value=\"Mobogenie\" _TAG=\"th2\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" value=\"日资源分发趋势\" _TAG=\"chartTitle\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" value=\"（单位：万笔）\" _TAG=\"chartSubtitle\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" expand=\"Y\" value=\"截至5月15日，近七日日均资源分发量3483万，较上一周增长0.7%；累计资源分发量35.6亿。本周约完成基础目标的79%(-3pp)、挑战目标的65%(-2pp)。\" />\r\n						</row>\r\n						<row num=\"10\" />\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" value=\"新增用户成本情况（Android）\" _TAG=\"chartTitle\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" value=\"（用户单位：千；花费单位：美元）\" _TAG=\"chartSubtitle\"/>\r\n						</row>\r\n						<row>\r\n							<cell colspan=\"11\" rowspan=\"1\" expand=\"Y\" value=\"近一周（5.2-5.8），周累计新增用户656万人，平均7日留存率9.6%，累计留存新增用户63万人，累计花费约3,449美元，平均每1万美元发展新增用户183万人。由于本周留存率较上周下降1PP，同时花费增加，导致发展稳定新增减少、单用户成本提高。\" />\r\n						</row>\r\n					</data>\r\n				</Normal>\r\n			</Table>\r\n		</Region>\r\n		<Region name=\"pic1\">\r\n			<StartPosition cidx=\"0\" ridx=\"0\"/>\r\n			<EndPosition cidx=\"2\" ridx=\"0\"/>\r\n			<Picture ext=\"png\" position=\"0\" height=\"50\">\r\n				<data _ref=\"logo\" />\r\n			</Picture>\r\n		</Region>\r\n		<Region name=\"chart1\" margin=\"5\">\r\n			<StartPosition cidx=\"0\" ridx=\"23\"/>\r\n			<EndPosition cidx=\"10\" ridx=\"32\"/>\r\n			<Chart type=\"line\">\r\n				<chart>\r\n					<title text=\"\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"1月\", \"2月\", \"3月\", \"4月\", \"5月\", \"6月\", \"7月\", \"8月\", \"9月\", \"10月\", \"11月\", \"12月\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis min=\"20000\">\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"2013年实际\">\r\n							<data>[38120, 38523, 53422, 31023, 46241, 50212, 31800, 46700, 41700, 31900, 50800, 49800]</data>\r\n						</item>\r\n						<item name=\"2014年预算\">\r\n							<data>[31420, 41412, 52428, 43623, 43803, 44200]</data>\r\n						</item>\r\n						<item name=\"2014年实际\">\r\n							<data>[33651, 40147, 54022, 26980]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		<Region name=\"chart2\" margin=\"5\">\r\n			<StartPosition cidx=\"0\" ridx=\"36\"/>\r\n			<EndPosition cidx=\"10\" ridx=\"45\"/>\r\n			<Chart type=\"line\">\r\n				<chart>\r\n					<title text=\"\" />\r\n					<xAxis>\r\n						<categories>\r\n							[\"1月\", \"2月\", \"3月\", \"4月\", \"5月\", \"6月\", \"7月\", \"8月\", \"9月\", \"10月\", \"11月\", \"12月\"]\r\n						</categories>\r\n					</xAxis>\r\n					<yAxis min=\"20000\">\r\n					</yAxis>\r\n					<series>\r\n						<item name=\"2013年实际\">\r\n							<data>[38120, 38523, 53422, 31023, 46241, 50212, 31800, 46700, 41700, 31900, 50800, 49800]</data>\r\n						</item>\r\n						<item name=\"2014年预算\">\r\n							<data>[31420, 41412, 52428, 43623, 43803, 44200]</data>\r\n						</item>\r\n						<item name=\"2014年实际\">\r\n							<data>[33651, 40147, 54022, 26980]</data>\r\n						</item>\r\n					</series>\r\n				</chart>\r\n			</Chart>\r\n		</Region>\r\n		\r\n	</Sheet>\r\n</PivotReport>\r\n',3,'N',NULL,'重点产品分析',NULL,NULL,NULL,NULL,NULL,NULL),(13,1,NULL,3,'N',NULL,'端游运营分析',NULL,NULL,NULL,NULL,NULL,NULL),(14,1,NULL,3,'N',NULL,'端游研发状态',NULL,NULL,NULL,NULL,NULL,NULL),(15,1,NULL,3,'N',NULL,'页游运营分析',NULL,NULL,NULL,NULL,NULL,NULL),(16,1,NULL,3,'N',NULL,'页游研发与海外代理',NULL,NULL,NULL,NULL,NULL,NULL),(17,1,NULL,3,'N',NULL,'7Road上线产品及代理运营收入',NULL,NULL,NULL,NULL,NULL,NULL),(18,1,NULL,3,'N',NULL,'7Road研发项目状态',NULL,NULL,NULL,NULL,NULL,NULL),(19,1,NULL,3,'N',NULL,'Mobogenie',NULL,NULL,NULL,NULL,NULL,NULL),(20,1,NULL,3,'N',NULL,'17173总体经营分析',NULL,NULL,NULL,NULL,NULL,NULL),(21,1,NULL,3,'N',NULL,'17173媒体和视频',NULL,NULL,NULL,NULL,NULL,NULL),(22,1,NULL,3,'N',NULL,'17173PC客户端',NULL,NULL,NULL,NULL,NULL,NULL),(23,1,NULL,3,'N',NULL,'17173移动APP',NULL,NULL,NULL,NULL,NULL,NULL),(24,1,NULL,3,'N',NULL,'17173研发中项目',NULL,NULL,NULL,NULL,NULL,NULL),(25,1,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<PivotReport xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"PivotReport.xsd\">\r\n	<Sheet sheetName=\"测试Sheet1\" defaultColWidth=\"140\" defaultRowHeight=\"25\">\r\n		<StartPosition cidx=\"5\" ridx=\"3\" />\r\n		<Region name=\"title\" annotation=\"缓存结果的多维报表例子\">\r\n			<StartPosition cidx=\"0\" ridx=\"0\" />\r\n			<Table>\r\n				<Pivot>\r\n					<data>\r\n						<head>\r\n							<row>\r\n								<cell colspan=\"2\" rowspan=\"1\" _TAG=\"corner\"></cell>\r\n								<cell colspan=\"3\" rowspan=\"1\" _TAG=\"heading-heading\" style=\"span\">\r\n									<drillOther img=\"drill-position-other\" />\r\n									<caption caption=\"指标\" />\r\n								</cell>\r\n							</row>\r\n							<row>\r\n								<cell colspan=\"1\" rowspan=\"1\" _TAG=\"heading-heading\" style=\"even\">\r\n									<drillOther img=\"drill-position-other\" />\r\n									<caption caption=\"Promotion Media\" />\r\n								</cell>\r\n								<cell colspan=\"1\" rowspan=\"1\" _TAG=\"heading-heading\" style=\"even\">\r\n									<drillOther img=\"drill-position-other\" />\r\n									<caption caption=\"Product\" />\r\n								</cell>\r\n								<cell colspan=\"1\" rowspan=\"1\" _TAG=\"column-heading\" style=\"even\">\r\n									<drillOther img=\"drill-position-other\" />\r\n									<caption caption=\"Unit Sales\" />\r\n								</cell>\r\n								<cell colspan=\"1\" rowspan=\"1\" _TAG=\"column-heading\" style=\"odd\">\r\n									<drillOther img=\"drill-position-other\" />\r\n									<caption caption=\"Store Cost\" />\r\n								</cell>\r\n								<cell colspan=\"1\" rowspan=\"1\" _TAG=\"column-heading\" style=\"even\">\r\n									<drillOther img=\"drill-position-other\" />\r\n									<caption caption=\"Store Sales\" />\r\n								</cell>\r\n							</row>\r\n						</head>\r\n						\r\n						<body>\r\n							<row>\r\n								<cell colspan=\"1\" rowspan=\"1\" _TAG=\"row-heading\" style=\"even\" indent=\"0\">\r\n									<drillExpand id=\"wcf52bf3d56\" img=\"drill-position-expand\" />\r\n									<caption caption=\"All Media\" />\r\n								</cell>\r\n								<cell colspan=\"1\" rowspan=\"1\" _TAG=\"row-heading\" style=\"even\" indent=\"0\">\r\n									<drillExpand id=\"wcfb682fe61\" img=\"drill-position-expand\" />\r\n									<caption caption=\"All Products\" />\r\n								</cell>\r\n								<cell style=\"even\" value=\"266,773\" _TAG=\"cell\"/>\r\n								<cell style=\"even\" value=\"225,627.23\" _TAG=\"cell\"/>\r\n								<cell style=\"even\" value=\"562,333.13\" _TAG=\"cell\"/>\r\n							</row>\r\n						</body>\r\n						\r\n					</data>\r\n				</Pivot>\r\n			</Table>\r\n		</Region>\r\n	</Sheet>\r\n</PivotReport>',3,'N',NULL,'网络应用研发部',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bi_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_report_type`
--

DROP TABLE IF EXISTS `bi_report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_report_type` (
  `ID_REPORT_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  PRIMARY KEY (`ID_REPORT_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_report_type`
--

LOCK TABLES `bi_report_type` WRITE;
/*!40000 ALTER TABLE `bi_report_type` DISABLE KEYS */;
INSERT INTO `bi_report_type` VALUES (1,'dashboard','仪表板'),(3,'pivot','多维报表'),(2,'report','报告');
/*!40000 ALTER TABLE `bi_report_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_smart`
--

DROP TABLE IF EXISTS `bi_smart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_smart` (
  `ID_SMART` bigint(20) NOT NULL,
  `ID_SMART_DIRECTORY` int(11) DEFAULT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  `SMART_OBJECT` mediumtext,
  `SMART_TYPE` int(11) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `SMART_VERSION` varchar(255) DEFAULT NULL,
  `SMART_STATUS` int(11) DEFAULT NULL,
  `CREATE_USER` varchar(255) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_SMART`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_smart`
--

LOCK TABLES `bi_smart` WRITE;
/*!40000 ALTER TABLE `bi_smart` DISABLE KEYS */;
INSERT INTO `bi_smart` VALUES (0,2,5,'<?xml version=\"1.0\"?>\r\n<Schema name=\"FoodMart\">\r\n<!--\r\n  == This software is subject to the terms of the Eclipse Public License v1.0\r\n  == Agreement, available at the following URL:\r\n  == http://www.eclipse.org/legal/epl-v10.html.\r\n  == You must accept the terms of that agreement to use this software.\r\n  ==\r\n  == Copyright (C) 2000-2005 Julian Hyde\r\n  == Copyright (C) 2005-2011 Pentaho and others\r\n  == All Rights Reserved.\r\n  -->\r\n\r\n<!-- Shared dimensions -->\r\n\r\n  <Dimension name=\"Store\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\r\n      <Table name=\"store\"/>\r\n      <Level name=\"Store Country\" column=\"store_country\" uniqueMembers=\"true\"/>\r\n      <Level name=\"Store State\" column=\"store_state\" uniqueMembers=\"true\"/>\r\n      <Level name=\"Store City\" column=\"store_city\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Store Name\" column=\"store_name\" uniqueMembers=\"true\">\r\n        <Property name=\"Store Type\" column=\"store_type\"/>\r\n        <Property name=\"Store Manager\" column=\"store_manager\"/>\r\n        <Property name=\"Store Sqft\" column=\"store_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Grocery Sqft\" column=\"grocery_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Frozen Sqft\" column=\"frozen_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Meat Sqft\" column=\"meat_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Has coffee bar\" column=\"coffee_bar\" type=\"Boolean\"/>\r\n        <Property name=\"Street address\" column=\"store_street_address\" type=\"String\"/>\r\n      </Level>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Store Size in SQFT\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\r\n      <Table name=\"store\"/>\r\n      <Level name=\"Store Sqft\" column=\"store_sqft\" type=\"Numeric\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Store Type\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\r\n      <Table name=\"store\"/>\r\n      <Level name=\"Store Type\" column=\"store_type\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Time\" type=\"TimeDimension\">\r\n    <Hierarchy hasAll=\"false\" primaryKey=\"time_id\">\r\n      <Table name=\"time_by_day\"/>\r\n      <Level name=\"Year\" column=\"the_year\" type=\"Numeric\" uniqueMembers=\"true\"\r\n          levelType=\"TimeYears\"/>\r\n      <Level name=\"Quarter\" column=\"quarter\" uniqueMembers=\"false\"\r\n          levelType=\"TimeQuarters\"/>\r\n      <Level name=\"Month\" column=\"month_of_year\" uniqueMembers=\"false\" type=\"Numeric\"\r\n          levelType=\"TimeMonths\"/>\r\n    </Hierarchy>\r\n    <Hierarchy hasAll=\"true\" name=\"Weekly\" primaryKey=\"time_id\">\r\n      <Table name=\"time_by_day\"/>\r\n      <Level name=\"Year\" column=\"the_year\" type=\"Numeric\" uniqueMembers=\"true\"\r\n          levelType=\"TimeYears\"/>\r\n      <Level name=\"Week\" column=\"week_of_year\" type=\"Numeric\" uniqueMembers=\"false\"\r\n          levelType=\"TimeWeeks\"/>\r\n      <Level name=\"Day\" column=\"day_of_month\" uniqueMembers=\"false\" type=\"Numeric\"\r\n          levelType=\"TimeDays\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Product\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"product_id\" primaryKeyTable=\"product\">\r\n      <Join leftKey=\"product_class_id\" rightKey=\"product_class_id\">\r\n        <Table name=\"product\"/>\r\n        <Table name=\"product_class\"/>\r\n      </Join>\r\n<!--\r\n      <Query>\r\n        <SQL dialect=\"generic\">\r\nSELECT *\r\nFROM \"product\", \"product_class\"\r\nWHERE \"product\".\"product_class_id\" = \"product_class\".\"product_class_id\"\r\n        </SQL>\r\n      </Query>\r\n      <Level name=\"Product Family\" column=\"product_family\" uniqueMembers=\"true\"/>\r\n      <Level name=\"Product Department\" column=\"product_department\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Product Category\" column=\"product_category\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Product Subcategory\" column=\"product_subcategory\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Brand Name\" column=\"brand_name\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Product Name\" column=\"product_name\" uniqueMembers=\"true\"/>\r\n-->\r\n      <Level name=\"Product Family\" table=\"product_class\" column=\"product_family\"\r\n          uniqueMembers=\"true\"/>\r\n      <Level name=\"Product Department\" table=\"product_class\" column=\"product_department\"\r\n          uniqueMembers=\"false\"/>\r\n      <Level name=\"Product Category\" table=\"product_class\" column=\"product_category\"\r\n          uniqueMembers=\"false\"/>\r\n      <Level name=\"Product Subcategory\" table=\"product_class\" column=\"product_subcategory\"\r\n          uniqueMembers=\"false\"/>\r\n      <Level name=\"Brand Name\" table=\"product\" column=\"brand_name\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Product Name\" table=\"product\" column=\"product_name\"\r\n          uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Warehouse\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"warehouse_id\">\r\n      <Table name=\"warehouse\"/>\r\n      <Level name=\"Country\" column=\"warehouse_country\" uniqueMembers=\"true\"/>\r\n      <Level name=\"State Province\" column=\"warehouse_state_province\"\r\n          uniqueMembers=\"true\"/>\r\n      <Level name=\"City\" column=\"warehouse_city\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Warehouse Name\" column=\"warehouse_name\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n<!-- Sales -->\r\n<Cube name=\"Sales\" defaultMeasure=\"Unit Sales\">\r\n  <!-- Use annotations to provide translations of this cube\'s caption and\r\n       description into German and French. Use of annotations in this manner is\r\n       experimental and unsupported; just for testing right now. -->\r\n  <Annotations>\r\n    <Annotation name=\"caption.de_DE\">Verkaufen</Annotation>\r\n    <Annotation name=\"caption.fr_FR\">Ventes</Annotation>\r\n    <Annotation name=\"description.fr_FR\">Cube des ventes</Annotation>\r\n    <Annotation name=\"description.de\">Cube Verkaufen</Annotation>\r\n    <Annotation name=\"description.de_AT\">Cube den Verkaufen</Annotation>\r\n  </Annotations>\r\n  <Table name=\"sales_fact_1997\">\r\n<!--\r\n    <AggExclude name=\"agg_l_03_sales_fact_1997\" />\r\n    <AggExclude name=\"agg_ll_01_sales_fact_1997\" />\r\n    <AggExclude name=\"agg_pl_01_sales_fact_1997\" />\r\n    <AggExclude name=\"agg_l_05_sales_fact_1997\" />\r\n-->\r\n    <AggExclude name=\"agg_c_special_sales_fact_1997\" />\r\n<!--\r\n    <AggExclude name=\"agg_c_14_sales_fact_1997\" />\r\n-->\r\n    <AggExclude name=\"agg_lc_100_sales_fact_1997\" />\r\n    <AggExclude name=\"agg_lc_10_sales_fact_1997\" />\r\n    <AggExclude name=\"agg_pc_10_sales_fact_1997\" />\r\n    <AggName name=\"agg_c_special_sales_fact_1997\">\r\n        <AggFactCount column=\"FACT_COUNT\"/>\r\n        <AggIgnoreColumn column=\"foo\"/>\r\n        <AggIgnoreColumn column=\"bar\"/>\r\n        <AggForeignKey factColumn=\"product_id\" aggColumn=\"PRODUCT_ID\" />\r\n        <AggForeignKey factColumn=\"customer_id\" aggColumn=\"CUSTOMER_ID\" />\r\n        <AggForeignKey factColumn=\"promotion_id\" aggColumn=\"PROMOTION_ID\" />\r\n        <AggForeignKey factColumn=\"store_id\" aggColumn=\"STORE_ID\" />\r\n<!--\r\n        <AggMeasure name=\"[Measures].[Avg Unit Sales]\" column=\"UNIT_SALES_AVG\"/>\r\n-->\r\n        <AggMeasure name=\"[Measures].[Unit Sales]\" column=\"UNIT_SALES_SUM\" />\r\n        <AggMeasure name=\"[Measures].[Store Cost]\" column=\"STORE_COST_SUM\" />\r\n        <AggMeasure name=\"[Measures].[Store Sales]\" column=\"STORE_SALES_SUM\" />\r\n        <AggLevel name=\"[Time].[Year]\" column=\"TIME_YEAR\" />\r\n        <AggLevel name=\"[Time].[Quarter]\" column=\"TIME_QUARTER\" />\r\n        <AggLevel name=\"[Time].[Month]\" column=\"TIME_MONTH\" />\r\n    </AggName>\r\n  </Table>\r\n\r\n  <DimensionUsage name=\"Store\" source=\"Store\" foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Store Size in SQFT\" source=\"Store Size in SQFT\"\r\n      foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Store Type\" source=\"Store Type\" foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\r\n  <DimensionUsage name=\"Product\" source=\"Product\" foreignKey=\"product_id\"/>\r\n  <Dimension name=\"Promotion Media\" foreignKey=\"promotion_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Media\" primaryKey=\"promotion_id\" defaultMember=\"All Media\">\r\n      <Table name=\"promotion\"/>\r\n      <Level name=\"Media Type\" column=\"media_type\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Promotions\" foreignKey=\"promotion_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Promotions\" primaryKey=\"promotion_id\" defaultMember=\"[All Promotions]\">\r\n      <Table name=\"promotion\"/>\r\n      <Level name=\"Promotion Name\" column=\"promotion_name\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Customers\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Customers\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Country\" column=\"country\" uniqueMembers=\"true\"/>\r\n      <Level name=\"State Province\" column=\"state_province\" uniqueMembers=\"true\"/>\r\n      <Level name=\"City\" column=\"city\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Name\" column=\"customer_id\" type=\"Numeric\" uniqueMembers=\"true\">\r\n        <NameExpression>\r\n          <SQL dialect=\"oracle\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"hive\">\r\n`customer`.`fullname`\r\n          </SQL>\r\n          <SQL dialect=\"hsqldb\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"access\">\r\nfname + \' \' + lname\r\n          </SQL>\r\n          <SQL dialect=\"postgres\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"mysql\">\r\nCONCAT(`customer`.`fname`, \' \', `customer`.`lname`)\r\n          </SQL>\r\n          <SQL dialect=\"mssql\">\r\nfname + \' \' + lname\r\n          </SQL>\r\n          <SQL dialect=\"derby\">\r\n\"customer\".\"fullname\"\r\n          </SQL>\r\n          <SQL dialect=\"db2\">\r\nCONCAT(CONCAT(\"customer\".\"fname\", \' \'), \"customer\".\"lname\")\r\n          </SQL>\r\n          <SQL dialect=\"luciddb\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"neoview\">\r\n\"customer\".\"fullname\"\r\n          </SQL>\r\n          <SQL dialect=\"teradata\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"generic\">\r\nfullname\r\n          </SQL>\r\n        </NameExpression>\r\n        <OrdinalExpression>\r\n          <SQL dialect=\"oracle\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"hsqldb\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"access\">\r\nfname + \' \' + lname\r\n          </SQL>\r\n          <SQL dialect=\"postgres\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"mysql\">\r\nCONCAT(`customer`.`fname`, \' \', `customer`.`lname`)\r\n          </SQL>\r\n          <SQL dialect=\"mssql\">\r\nfname + \' \' + lname\r\n          </SQL>\r\n          <SQL dialect=\"neoview\">\r\n\"customer\".\"fullname\"\r\n          </SQL>\r\n          <SQL dialect=\"derby\">\r\n\"customer\".\"fullname\"\r\n          </SQL>\r\n          <SQL dialect=\"db2\">\r\nCONCAT(CONCAT(\"customer\".\"fname\", \' \'), \"customer\".\"lname\")\r\n          </SQL>\r\n          <SQL dialect=\"luciddb\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"generic\">\r\nfullname\r\n          </SQL>\r\n        </OrdinalExpression>\r\n        <Property name=\"Gender\" column=\"gender\"/>\r\n        <Property name=\"Marital Status\" column=\"marital_status\"/>\r\n        <Property name=\"Education\" column=\"education\"/>\r\n        <Property name=\"Yearly Income\" column=\"yearly_income\"/>\r\n      </Level>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Education Level\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Education Level\" column=\"education\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Gender\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Gender\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Gender\" column=\"gender\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Marital Status\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Marital Status\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Marital Status\" column=\"marital_status\" uniqueMembers=\"true\" approxRowCount=\"111\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Yearly Income\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Yearly Income\" column=\"yearly_income\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"\r\n      formatString=\"Standard\"/>\r\n  <Measure name=\"Store Cost\" column=\"store_cost\" aggregator=\"sum\"\r\n      formatString=\"#,###.00\"/>\r\n  <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"\r\n      formatString=\"#,###.00\"/>\r\n  <Measure name=\"Sales Count\" column=\"product_id\" aggregator=\"count\"\r\n      formatString=\"#,###\"/>\r\n  <Measure name=\"Customer Count\" column=\"customer_id\"\r\n      aggregator=\"distinct-count\" formatString=\"#,###\"/>\r\n  <Measure name=\"Promotion Sales\" aggregator=\"sum\" formatString=\"#,###.00\">\r\n    <MeasureExpression>\r\n      <SQL dialect=\"access\">\r\nIif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\".\"store_sales\")\r\n      </SQL>\r\n      <SQL dialect=\"oracle\">\r\n(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)\r\n      </SQL>\r\n      <SQL dialect=\"hsqldb\">\r\n(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)\r\n      </SQL>\r\n      <SQL dialect=\"postgres\">\r\n(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)\r\n      </SQL>\r\n      <SQL dialect=\"mysql\">\r\n(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end)\r\n      </SQL>\r\n      <!-- Workaround the fact that Infobright does not have a CASE operator.\r\n           The simpler expression gives wrong results, so some tests are\r\n           disabled. -->\r\n      <SQL dialect=\"neoview\">\r\n(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)\r\n      </SQL>\r\n      <SQL dialect=\"infobright\">\r\n`sales_fact_1997`.`store_sales`\r\n      </SQL>\r\n      <SQL dialect=\"derby\">\r\n(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)\r\n      </SQL>\r\n      <SQL dialect=\"luciddb\">\r\n(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)\r\n      </SQL>\r\n      <SQL dialect=\"db2\">\r\n(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)\r\n      </SQL>\r\n      <SQL dialect=\"generic\">\r\n(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997.store_sales end)\r\n      </SQL>\r\n    </MeasureExpression>\r\n  </Measure>\r\n  <CalculatedMember\r\n      name=\"Profit\"\r\n      dimension=\"Measures\">\r\n    <Formula>[Measures].[Store Sales] - [Measures].[Store Cost]</Formula>\r\n    <CalculatedMemberProperty name=\"FORMAT_STRING\" value=\"$#,##0.00\"/>\r\n  </CalculatedMember>\r\n  <CalculatedMember\r\n      name=\"Profit last Period\"\r\n      dimension=\"Measures\"\r\n      formula=\"COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])\"\r\n      visible=\"false\">\r\n    <CalculatedMemberProperty name=\"FORMAT_STRING\" value=\"$#,##0.00\"/>\r\n    <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"18\"/>\r\n  </CalculatedMember>\r\n  <CalculatedMember\r\n      name=\"Profit Growth\"\r\n      dimension=\"Measures\"\r\n      formula=\"([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]\"\r\n      visible=\"true\"\r\n      caption=\"Gewinn-Wachstum\">\r\n    <CalculatedMemberProperty name=\"FORMAT_STRING\" value=\"0.0%\"/>\r\n  </CalculatedMember>\r\n</Cube>\r\n\r\n<Cube name=\"Warehouse\">\r\n  <Table name=\"inventory_fact_1997\"/>\r\n\r\n  <DimensionUsage name=\"Store\" source=\"Store\" foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Store Size in SQFT\" source=\"Store Size in SQFT\"\r\n      foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Store Type\" source=\"Store Type\" foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\r\n  <DimensionUsage name=\"Product\" source=\"Product\" foreignKey=\"product_id\"/>\r\n  <DimensionUsage name=\"Warehouse\" source=\"Warehouse\" foreignKey=\"warehouse_id\"/>\r\n\r\n  <Measure name=\"Store Invoice\" column=\"store_invoice\" aggregator=\"sum\"/>\r\n  <Measure name=\"Supply Time\" column=\"supply_time\" aggregator=\"sum\"/>\r\n  <Measure name=\"Warehouse Cost\" column=\"warehouse_cost\" aggregator=\"sum\"/>\r\n  <Measure name=\"Warehouse Sales\" column=\"warehouse_sales\" aggregator=\"sum\"/>\r\n  <Measure name=\"Units Shipped\" column=\"units_shipped\" aggregator=\"sum\" formatString=\"#.0\"/>\r\n  <Measure name=\"Units Ordered\" column=\"units_ordered\" aggregator=\"sum\" formatString=\"#.0\"/>\r\n  <Measure name=\"Warehouse Profit\" aggregator=\"sum\">\r\n    <MeasureExpression>\r\n      <SQL dialect=\"mysql\">\r\n`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`\r\n      </SQL>\r\n      <SQL dialect=\"infobright\">\r\n`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`\r\n      </SQL>\r\n      <SQL dialect=\"generic\">\r\n&quot;warehouse_sales&quot; - &quot;inventory_fact_1997&quot;.&quot;warehouse_cost&quot;\r\n      </SQL>\r\n    </MeasureExpression>\r\n  </Measure>\r\n  <CalculatedMember\r\n      name=\"Average Warehouse Sale\"\r\n      dimension=\"Measures\">\r\n    <Formula>[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]</Formula>\r\n    <CalculatedMemberProperty name=\"FORMAT_STRING\" value=\"$#,##0.00\"/>\r\n  </CalculatedMember>\r\n  <NamedSet name=\"Top Sellers\">\r\n    <Formula>TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])</Formula>\r\n  </NamedSet>    \r\n</Cube>\r\n\r\n<!-- Test a cube based upon a single table. -->\r\n<Cube name=\"Store\">\r\n  <Table name=\"store\"/>\r\n  <!-- We could have used the shared dimension \"Store Type\", but we\r\n     want to test private dimensions without primary key. -->\r\n  <Dimension name=\"Store Type\">\r\n    <Hierarchy hasAll=\"true\">\r\n      <Level name=\"Store Type\" column=\"store_type\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <!-- We don\'t have to specify primary key or foreign key since the shared\r\n     dimension \"Store\" has the same underlying table as the cube. -->\r\n  <DimensionUsage name=\"Store\" source=\"Store\"/>\r\n\r\n  <Dimension name=\"Has coffee bar\">\r\n    <Hierarchy hasAll=\"true\">\r\n      <Level name=\"Has coffee bar\" column=\"coffee_bar\" uniqueMembers=\"true\"\r\n          type=\"Boolean\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Measure name=\"Store Sqft\" column=\"store_sqft\" aggregator=\"sum\"\r\n      formatString=\"#,###\"/>\r\n  <Measure name=\"Grocery Sqft\" column=\"grocery_sqft\" aggregator=\"sum\"\r\n      formatString=\"#,###\"/>\r\n\r\n</Cube>\r\n\r\n<Cube name=\"HR\">\r\n  <Table name=\"salary\"/>\r\n  <!-- Use private \"Time\" dimension because key is different than public\r\n     \"Time\" dimension. -->\r\n  <Dimension name=\"Time\" type=\"TimeDimension\" foreignKey=\"pay_date\">\r\n    <Hierarchy hasAll=\"false\" primaryKey=\"the_date\">\r\n      <Table name=\"time_by_day\"/>\r\n      <Level name=\"Year\" column=\"the_year\" type=\"Numeric\" uniqueMembers=\"true\"\r\n          levelType=\"TimeYears\"/>\r\n      <Level name=\"Quarter\" column=\"quarter\" uniqueMembers=\"false\"\r\n          levelType=\"TimeQuarters\"/>\r\n      <!-- Use the_month as source for the name, so members look like\r\n           [Time].[1997].[Q1].[Jan] rather than [Time].[1997].[Q1].[1]. -->\r\n      <Level name=\"Month\" column=\"month_of_year\" nameColumn=\"the_month\"\r\n          uniqueMembers=\"false\" type=\"Numeric\" levelType=\"TimeMonths\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Store\" foreignKey=\"employee_id\" >\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"employee_id\"\r\n        primaryKeyTable=\"employee\">\r\n      <Join leftKey=\"store_id\" rightKey=\"store_id\">\r\n        <Table name=\"employee\"/>\r\n        <Table name=\"store\"/>\r\n      </Join>\r\n      <Level name=\"Store Country\" table=\"store\" column=\"store_country\"\r\n          uniqueMembers=\"true\"/>\r\n      <Level name=\"Store State\" table=\"store\" column=\"store_state\"\r\n          uniqueMembers=\"true\"/>\r\n      <Level name=\"Store City\" table=\"store\" column=\"store_city\"\r\n          uniqueMembers=\"false\"/>\r\n      <Level name=\"Store Name\" table=\"store\" column=\"store_name\"\r\n          uniqueMembers=\"true\">\r\n        <Property name=\"Store Type\" column=\"store_type\"/>\r\n        <Property name=\"Store Manager\" column=\"store_manager\"/>\r\n        <Property name=\"Store Sqft\" column=\"store_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Grocery Sqft\" column=\"grocery_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Frozen Sqft\" column=\"frozen_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Meat Sqft\" column=\"meat_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Has coffee bar\" column=\"coffee_bar\" type=\"Boolean\"/>\r\n        <Property name=\"Street address\" column=\"store_street_address\"\r\n            type=\"String\"/>\r\n      </Level>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Pay Type\" foreignKey=\"employee_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"employee_id\"\r\n        primaryKeyTable=\"employee\">\r\n      <Join leftKey=\"position_id\" rightKey=\"position_id\">\r\n        <Table name=\"employee\"/>\r\n        <Table name=\"position\"/>\r\n      </Join>\r\n      <Level name=\"Pay Type\" table=\"position\" column=\"pay_type\"\r\n          uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Store Type\" foreignKey=\"employee_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKeyTable=\"employee\" primaryKey=\"employee_id\">\r\n      <Join leftKey=\"store_id\" rightKey=\"store_id\">\r\n        <Table name=\"employee\"/>\r\n        <Table name=\"store\"/>\r\n      </Join>\r\n      <Level name=\"Store Type\" table=\"store\" column=\"store_type\"\r\n          uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Position\" foreignKey=\"employee_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Position\"\r\n        primaryKey=\"employee_id\">\r\n      <Table name=\"employee\"/>\r\n      <Level name=\"Management Role\" uniqueMembers=\"true\"\r\n          column=\"management_role\"/>\r\n      <Level name=\"Position Title\" uniqueMembers=\"false\"\r\n          column=\"position_title\" ordinalColumn=\"position_id\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Department\" foreignKey=\"department_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"department_id\">\r\n      <Table name=\"department\"/>\r\n      <Level name=\"Department Description\" type=\"Numeric\" uniqueMembers=\"true\"\r\n          column=\"department_id\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Employees\" foreignKey=\"employee_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Employees\"\r\n        primaryKey=\"employee_id\">\r\n      <Table name=\"employee\"/>\r\n      <Level name=\"Employee Id\" type=\"Numeric\" uniqueMembers=\"true\"\r\n          column=\"employee_id\" parentColumn=\"supervisor_id\"\r\n          nameColumn=\"full_name\" nullParentValue=\"0\">\r\n        <Closure parentColumn=\"supervisor_id\" childColumn=\"employee_id\">\r\n          <Table name=\"employee_closure\"/>\r\n        </Closure>\r\n        <Property name=\"Marital Status\" column=\"marital_status\"/>\r\n        <Property name=\"Position Title\" column=\"position_title\"/>\r\n        <Property name=\"Gender\" column=\"gender\"/>\r\n        <Property name=\"Salary\" column=\"salary\"/>\r\n        <Property name=\"Education Level\" column=\"education_level\"/>\r\n        <Property name=\"Management Role\" column=\"management_role\"/>\r\n      </Level>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Measure name=\"Org Salary\" column=\"salary_paid\" aggregator=\"sum\"\r\n      formatString=\"Currency\"/>\r\n  <Measure name=\"Count\" column=\"employee_id\" aggregator=\"count\"\r\n      formatString=\"#,#\"/>\r\n  <Measure name=\"Number of Employees\" column=\"employee_id\"\r\n      aggregator=\"distinct-count\" formatString=\"#,#\"/>\r\n  <CalculatedMember name=\"Employee Salary\" dimension=\"Measures\"\r\n      formatString=\"Currency\"\r\n      formula=\"([Employees].currentmember.datamember, [Measures].[Org Salary])\"/>\r\n  <CalculatedMember name=\"Avg Salary\" dimension=\"Measures\"\r\n      formatString=\"Currency\"\r\n      formula=\"[Measures].[Org Salary]/[Measures].[Number of Employees]\"/>\r\n</Cube>\r\n\r\n<!-- Cube with one ragged hierarchy (otherwise the same as the \"Sales\"\r\n   cube). -->\r\n<Cube name=\"Sales Ragged\">\r\n  <Table name=\"sales_fact_1997\">\r\n    <AggExclude name=\"agg_pc_10_sales_fact_1997\"/>\r\n    <AggExclude name=\"agg_lc_10_sales_fact_1997\"/>\r\n  </Table>\r\n  <Dimension name=\"Store\" foreignKey=\"store_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\r\n      <Table name=\"store_ragged\"/>\r\n      <Level name=\"Store Country\" column=\"store_country\" uniqueMembers=\"true\"\r\n          hideMemberIf=\"Never\"/>\r\n      <Level name=\"Store State\" column=\"store_state\" uniqueMembers=\"true\"\r\n          hideMemberIf=\"IfParentsName\"/>\r\n      <Level name=\"Store City\" column=\"store_city\" uniqueMembers=\"false\"\r\n          hideMemberIf=\"IfBlankName\"/>\r\n      <Level name=\"Store Name\" column=\"store_name\" uniqueMembers=\"true\"\r\n          hideMemberIf=\"Never\">\r\n        <Property name=\"Store Type\" column=\"store_type\"/>\r\n        <Property name=\"Store Manager\" column=\"store_manager\"/>\r\n        <Property name=\"Store Sqft\" column=\"store_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Grocery Sqft\" column=\"grocery_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Frozen Sqft\" column=\"frozen_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Meat Sqft\" column=\"meat_sqft\" type=\"Numeric\"/>\r\n        <Property name=\"Has coffee bar\" column=\"coffee_bar\" type=\"Boolean\"/>\r\n        <Property name=\"Street address\" column=\"store_street_address\" type=\"String\"/>\r\n      </Level>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <Dimension name=\"Geography\" foreignKey=\"store_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\r\n      <Table name=\"store_ragged\"/>\r\n      <Level name=\"Country\" column=\"store_country\" uniqueMembers=\"true\"\r\n          hideMemberIf=\"Never\"/>\r\n      <Level name=\"State\" column=\"store_state\" uniqueMembers=\"true\"\r\n          hideMemberIf=\"IfParentsName\"/>\r\n      <Level name=\"City\" column=\"store_city\" uniqueMembers=\"false\"\r\n          hideMemberIf=\"IfBlankName\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n\r\n  <DimensionUsage name=\"Store Size in SQFT\" source=\"Store Size in SQFT\"\r\n      foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Store Type\" source=\"Store Type\" foreignKey=\"store_id\"/>\r\n  <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\r\n  <DimensionUsage name=\"Product\" source=\"Product\" foreignKey=\"product_id\"/>\r\n  <Dimension name=\"Promotion Media\" foreignKey=\"promotion_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Media\" primaryKey=\"promotion_id\">\r\n      <Table name=\"promotion\"/>\r\n      <Level name=\"Media Type\" column=\"media_type\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Promotions\" foreignKey=\"promotion_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Promotions\" primaryKey=\"promotion_id\">\r\n      <Table name=\"promotion\"/>\r\n      <Level name=\"Promotion Name\" column=\"promotion_name\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Customers\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Customers\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Country\" column=\"country\" uniqueMembers=\"true\"/>\r\n      <Level name=\"State Province\" column=\"state_province\" uniqueMembers=\"true\"/>\r\n      <Level name=\"City\" column=\"city\" uniqueMembers=\"false\"/>\r\n      <Level name=\"Name\" uniqueMembers=\"true\">\r\n        <KeyExpression>\r\n          <SQL dialect=\"oracle\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"hsqldb\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"access\">\r\nfname + \' \' + lname\r\n          </SQL>\r\n          <SQL dialect=\"postgres\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"mysql\">\r\nCONCAT(`customer`.`fname`, \' \', `customer`.`lname`)\r\n          </SQL>\r\n          <SQL dialect=\"mssql\">\r\nfname + \' \' + lname\r\n          </SQL>\r\n          <SQL dialect=\"derby\">\r\n\"customer\".\"fullname\"\r\n          </SQL>\r\n          <SQL dialect=\"db2\">\r\nCONCAT(CONCAT(\"customer\".\"fname\", \' \'), \"customer\".\"lname\")\r\n          </SQL>\r\n          <SQL dialect=\"luciddb\">\r\n\"fname\" || \' \' || \"lname\"\r\n          </SQL>\r\n          <SQL dialect=\"neoview\">\r\n\"customer\".\"fullname\"\r\n          </SQL>\r\n          <SQL dialect=\"generic\">\r\nfullname\r\n          </SQL>\r\n        </KeyExpression>\r\n        <Property name=\"Gender\" column=\"gender\"/>\r\n        <Property name=\"Marital Status\" column=\"marital_status\"/>\r\n        <Property name=\"Education\" column=\"education\"/>\r\n        <Property name=\"Yearly Income\" column=\"yearly_income\"/>\r\n      </Level>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Education Level\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Education Level\" column=\"education\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Gender\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Gender\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Gender\" column=\"gender\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Marital Status\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" allMemberName=\"All Marital Status\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Marital Status\" column=\"marital_status\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Dimension name=\"Yearly Income\" foreignKey=\"customer_id\">\r\n    <Hierarchy hasAll=\"true\" primaryKey=\"customer_id\">\r\n      <Table name=\"customer\"/>\r\n      <Level name=\"Yearly Income\" column=\"yearly_income\" uniqueMembers=\"true\"/>\r\n    </Hierarchy>\r\n  </Dimension>\r\n  <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"\r\n      formatString=\"Standard\"/>\r\n  <Measure name=\"Store Cost\" column=\"store_cost\" aggregator=\"sum\"\r\n      formatString=\"#,###.00\"/>\r\n  <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"\r\n      formatString=\"#,###.00\"/>\r\n  <Measure name=\"Sales Count\" column=\"product_id\" aggregator=\"count\"\r\n      formatString=\"#,###\"/>\r\n  <Measure name=\"Customer Count\" column=\"customer_id\" aggregator=\"distinct-count\"\r\n      formatString=\"#,###\"/>\r\n</Cube>\r\n\r\n<!-- a simpler version of \"Sales\" (with MEMBER_ORDINAL-properties) -->\r\n<Cube name=\"Sales 2\">\r\n   <Table name=\"sales_fact_1997\"/>\r\n\r\n   <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\r\n   <DimensionUsage name=\"Product\" source=\"Product\" foreignKey=\"product_id\"/>\r\n\r\n   <Dimension name=\"Gender\" foreignKey=\"customer_id\">\r\n     <Hierarchy hasAll=\"true\" allMemberName=\"All Gender\" primaryKey=\"customer_id\">\r\n       <Table name=\"customer\"/>\r\n       <Level name=\"Gender\" column=\"gender\" uniqueMembers=\"true\"/>\r\n     </Hierarchy>\r\n   </Dimension>\r\n\r\n   <Measure name=\"Sales Count\" column=\"product_id\" aggregator=\"count\" formatString=\"#,###\">\r\n     <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"1\"/>\r\n   </Measure>\r\n\r\n   <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"    formatString=\"Standard\">\r\n     <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"2\"/>\r\n   </Measure>\r\n\r\n   <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"  formatString=\"#,###.00\">\r\n      <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"3\"/>\r\n    </Measure>\r\n\r\n   <Measure name=\"Store Cost\" column=\"store_cost\" aggregator=\"sum\"    formatString=\"#,###.00\">\r\n      <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"6\"/>\r\n    </Measure>\r\n\r\n   <Measure name=\"Customer Count\" column=\"customer_id\" aggregator=\"distinct-count\" formatString=\"#,###\">\r\n      <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"7\"/>\r\n    </Measure>\r\n\r\n   <CalculatedMember\r\n       name=\"Profit\"\r\n       dimension=\"Measures\">\r\n     <Formula>[Measures].[Store Sales] - [Measures].[Store Cost]</Formula>\r\n     <CalculatedMemberProperty name=\"FORMAT_STRING\" value=\"$#,##0.00\"/>\r\n     <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"4\"/>\r\n   </CalculatedMember>\r\n\r\n   <CalculatedMember\r\n       name=\"Profit last Period\"\r\n       dimension=\"Measures\"\r\n       formula=\"COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])\"\r\n       visible=\"false\">\r\n      <CalculatedMemberProperty name=\"MEMBER_ORDINAL\" value=\"5\"/>\r\n   </CalculatedMember>\r\n</Cube>\r\n\r\n<VirtualCube name=\"Warehouse and Sales\" defaultMeasure=\"Store Sales\">\r\n  <VirtualCubeDimension cubeName=\"Sales\" name=\"Customers\"/>\r\n  <VirtualCubeDimension cubeName=\"Sales\" name=\"Education Level\"/>\r\n  <VirtualCubeDimension cubeName=\"Sales\" name=\"Gender\"/>\r\n  <VirtualCubeDimension cubeName=\"Sales\" name=\"Marital Status\"/>\r\n  <VirtualCubeDimension name=\"Product\"/>\r\n  <VirtualCubeDimension cubeName=\"Sales\" name=\"Promotion Media\"/>\r\n  <VirtualCubeDimension cubeName=\"Sales\" name=\"Promotions\"/>\r\n  <VirtualCubeDimension name=\"Store\"/>\r\n  <VirtualCubeDimension name=\"Time\"/>\r\n  <VirtualCubeDimension cubeName=\"Sales\" name=\"Yearly Income\"/>\r\n  <VirtualCubeDimension cubeName=\"Warehouse\" name=\"Warehouse\"/>\r\n  <VirtualCubeMeasure cubeName=\"Sales\" name=\"[Measures].[Sales Count]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Sales\" name=\"[Measures].[Store Cost]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Sales\" name=\"[Measures].[Store Sales]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Sales\" name=\"[Measures].[Unit Sales]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Sales\" name=\"[Measures].[Profit]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Sales\" name=\"[Measures].[Profit Growth]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Store Invoice]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Supply Time]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Units Ordered]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Units Shipped]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Warehouse Cost]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Warehouse Profit]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Warehouse Sales]\"/>\r\n  <VirtualCubeMeasure cubeName=\"Warehouse\" name=\"[Measures].[Average Warehouse Sale]\"/>\r\n  <!--\r\n  <VirtualCubeMeasure cubeName=\"Sales\" name=\"[Measures].[Store Sales Net]\"/>\r\n  -->\r\n  <CalculatedMember name=\"Profit Per Unit Shipped\" dimension=\"Measures\">\r\n    <Formula>[Measures].[Profit] / [Measures].[Units Shipped]</Formula>\r\n  </CalculatedMember>\r\n</VirtualCube>\r\n\r\n<!-- A California manager can only see customers and stores in California.\r\n     They cannot drill down on Gender. -->\r\n<Role name=\"California manager\">\r\n  <SchemaGrant access=\"none\">\r\n    <CubeGrant cube=\"Sales\" access=\"all\">\r\n      <HierarchyGrant hierarchy=\"[Store]\" access=\"custom\"\r\n          topLevel=\"[Store].[Store Country]\">\r\n        <MemberGrant member=\"[Store].[USA].[CA]\" access=\"all\"/>\r\n        <MemberGrant member=\"[Store].[USA].[CA].[Los Angeles]\" access=\"none\"/>\r\n      </HierarchyGrant>\r\n      <HierarchyGrant hierarchy=\"[Customers]\" access=\"custom\"\r\n          topLevel=\"[Customers].[State Province]\" bottomLevel=\"[Customers].[City]\">\r\n        <MemberGrant member=\"[Customers].[USA].[CA]\" access=\"all\"/>\r\n        <MemberGrant member=\"[Customers].[USA].[CA].[Los Angeles]\" access=\"none\"/>\r\n      </HierarchyGrant>\r\n      <HierarchyGrant hierarchy=\"[Gender]\" access=\"none\"/>\r\n    </CubeGrant>\r\n  </SchemaGrant>\r\n</Role>\r\n\r\n<Role name=\"No HR Cube\">\r\n  <SchemaGrant access=\"all\">\r\n    <CubeGrant cube=\"HR\" access=\"none\"/>\r\n  </SchemaGrant>\r\n</Role>\r\n\r\n</Schema>\r\n',1,'测试用FoodMart多维分析模型',NULL,NULL,NULL,NULL,NULL,NULL),(1,2,5,NULL,1,'销售分析',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bi_smart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_timed_exe`
--

DROP TABLE IF EXISTS `bi_timed_exe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_timed_exe` (
  `ID_TIMED_EXE` bigint(20) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `REPEAT` varchar(1) DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_TIMED_EXE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_timed_exe`
--

LOCK TABLES `bi_timed_exe` WRITE;
/*!40000 ALTER TABLE `bi_timed_exe` DISABLE KEYS */;
INSERT INTO `bi_timed_exe` VALUES (0,'',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `bi_timed_exe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_timed_exe_attr`
--

DROP TABLE IF EXISTS `bi_timed_exe_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_timed_exe_attr` (
  `ID_TIMED_EXE_ATTR` bigint(20) NOT NULL,
  `ID_TIMED_EXT` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_TIMED_EXE_ATTR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_timed_exe_attr`
--

LOCK TABLES `bi_timed_exe_attr` WRITE;
/*!40000 ALTER TABLE `bi_timed_exe_attr` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_timed_exe_attr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_authorization`
--

DROP TABLE IF EXISTS `r_authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_authorization` (
  `RID` bigint(20) NOT NULL,
  `OID` int(11) NOT NULL,
  `OTYPE` int(11) NOT NULL,
  `PERMISSION` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_authorization`
--

LOCK TABLES `r_authorization` WRITE;
/*!40000 ALTER TABLE `r_authorization` DISABLE KEYS */;
INSERT INTO `r_authorization` VALUES (100,20,2,7),(100,104,2,7),(100,106,2,7),(100,14,2,7),(100,15,2,7),(100,105,2,7),(100,109,2,7),(100,110,2,7),(100,111,2,7),(100,112,2,7),(100,113,2,7),(100,114,2,7),(0,14,2,4),(0,15,2,4),(0,20,2,4),(0,104,2,4),(0,105,2,4),(0,106,2,4),(0,109,2,4),(0,110,2,4),(0,111,2,4),(0,112,2,4),(0,113,2,4),(0,114,2,4),(100,116,2,7),(100,117,2,7),(0,116,2,4),(0,117,2,4),(100,118,2,7),(0,118,2,4),(0,18,2,4),(100,18,2,7);
/*!40000 ALTER TABLE `r_authorization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_cluster`
--

DROP TABLE IF EXISTS `r_cluster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_cluster` (
  `ID_CLUSTER` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `BASE_PORT` varchar(255) DEFAULT NULL,
  `SOCKETS_BUFFER_SIZE` varchar(255) DEFAULT NULL,
  `SOCKETS_FLUSH_INTERVAL` varchar(255) DEFAULT NULL,
  `SOCKETS_COMPRESSED` char(1) DEFAULT NULL,
  `DYNAMIC_CLUSTER` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_CLUSTER`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_cluster`
--

LOCK TABLES `r_cluster` WRITE;
/*!40000 ALTER TABLE `r_cluster` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_cluster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_cluster_slave`
--

DROP TABLE IF EXISTS `r_cluster_slave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_cluster_slave` (
  `ID_CLUSTER_SLAVE` bigint(20) NOT NULL,
  `ID_CLUSTER` int(11) DEFAULT NULL,
  `ID_SLAVE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_CLUSTER_SLAVE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_cluster_slave`
--

LOCK TABLES `r_cluster_slave` WRITE;
/*!40000 ALTER TABLE `r_cluster_slave` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_cluster_slave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_condition`
--

DROP TABLE IF EXISTS `r_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_condition` (
  `ID_CONDITION` bigint(20) NOT NULL,
  `ID_CONDITION_PARENT` int(11) DEFAULT NULL,
  `NEGATED` char(1) DEFAULT NULL,
  `OPERATOR` varchar(255) DEFAULT NULL,
  `LEFT_NAME` varchar(255) DEFAULT NULL,
  `CONDITION_FUNCTION` varchar(255) DEFAULT NULL,
  `RIGHT_NAME` varchar(255) DEFAULT NULL,
  `ID_VALUE_RIGHT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_CONDITION`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_condition`
--

LOCK TABLES `r_condition` WRITE;
/*!40000 ALTER TABLE `r_condition` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_database`
--

DROP TABLE IF EXISTS `r_database`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_database` (
  `ID_DATABASE` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ID_DATABASE_TYPE` int(11) DEFAULT NULL,
  `ID_DATABASE_CONTYPE` int(11) DEFAULT NULL,
  `HOST_NAME` varchar(255) DEFAULT NULL,
  `DATABASE_NAME` varchar(255) DEFAULT NULL,
  `PORT` int(11) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `SERVERNAME` varchar(255) DEFAULT NULL,
  `DATA_TBS` varchar(255) DEFAULT NULL,
  `INDEX_TBS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DATABASE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_database`
--

LOCK TABLES `r_database` WRITE;
/*!40000 ALTER TABLE `r_database` DISABLE KEYS */;
INSERT INTO `r_database` VALUES (1,'imeta',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL),(3,'imeta11',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL),(4,'ddds',33,1,NULL,NULL,1521,NULL,'Encrypted ',NULL,NULL,NULL),(5,'foodmart',28,1,'localhost','foodmart',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL);
/*!40000 ALTER TABLE `r_database` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_database_attribute`
--

DROP TABLE IF EXISTS `r_database_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_database_attribute` (
  `ID_DATABASE_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_DATABASE_ATTRIBUTE`),
  UNIQUE KEY `IDX_DATABASE_ATTRIBUTE_AK` (`ID_DATABASE`,`CODE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_database_attribute`
--

LOCK TABLES `r_database_attribute` WRITE;
/*!40000 ALTER TABLE `r_database_attribute` DISABLE KEYS */;
INSERT INTO `r_database_attribute` VALUES (155,1,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(156,1,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(157,1,'PREFERRED_SCHEMA_NAME',NULL),(158,1,'QUOTE_ALL_FIELDS','false'),(159,1,'SQL_CONNECT',NULL),(153,1,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(151,1,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(152,1,'STREAM_RESULTS','N'),(148,1,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(149,1,'USE_POOLING','N'),(59,2,'QUOTE_ALL_FIELDS','false'),(57,2,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(58,2,'PREFERRED_SCHEMA_NAME',NULL),(56,2,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(55,2,'PORT_NUMBER','3306'),(54,2,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(52,2,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(150,1,'IS_CLUSTERED','N'),(81,3,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(80,3,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(79,3,'PORT_NUMBER','3306'),(78,3,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(77,3,'STREAM_RESULTS','N'),(76,3,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(75,3,'IS_CLUSTERED','N'),(74,3,'USE_POOLING','N'),(73,3,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(53,2,'STREAM_RESULTS','N'),(51,2,'IS_CLUSTERED','N'),(50,2,'USE_POOLING','N'),(49,2,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(60,2,'SQL_CONNECT',NULL),(154,1,'PORT_NUMBER','3306'),(82,3,'PREFERRED_SCHEMA_NAME',NULL),(83,3,'QUOTE_ALL_FIELDS','false'),(84,3,'SQL_CONNECT',NULL),(160,4,'USE_POOLING','N'),(161,4,'IS_CLUSTERED','N'),(162,4,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(163,4,'PORT_NUMBER','1521'),(164,4,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(169,5,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(170,5,'USE_POOLING','N'),(171,5,'IS_CLUSTERED','N'),(165,4,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(174,5,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(175,5,'PORT_NUMBER','3306'),(121,6,'USE_POOLING','N'),(122,6,'IS_CLUSTERED','N'),(123,6,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(124,6,'PORT_NUMBER','1521'),(125,6,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(126,6,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(127,6,'PREFERRED_SCHEMA_NAME',NULL),(128,6,'QUOTE_ALL_FIELDS','false'),(129,6,'SQL_CONNECT',NULL),(130,7,'USE_POOLING','N'),(131,7,'IS_CLUSTERED','N'),(132,7,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(133,7,'PORT_NUMBER','1521'),(134,7,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(135,7,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(136,7,'PREFERRED_SCHEMA_NAME',NULL),(137,7,'QUOTE_ALL_FIELDS','false'),(138,7,'SQL_CONNECT',NULL),(139,8,'USE_POOLING','N'),(140,8,'IS_CLUSTERED','N'),(141,8,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(142,8,'PORT_NUMBER','1521'),(143,8,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(144,8,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(145,8,'PREFERRED_SCHEMA_NAME',NULL),(146,8,'QUOTE_ALL_FIELDS','false'),(147,8,'SQL_CONNECT',NULL),(166,4,'PREFERRED_SCHEMA_NAME',NULL),(167,4,'QUOTE_ALL_FIELDS','false'),(168,4,'SQL_CONNECT',NULL),(176,5,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(177,5,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(173,5,'STREAM_RESULTS','N'),(172,5,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(178,5,'PREFERRED_SCHEMA_NAME',NULL),(179,5,'QUOTE_ALL_FIELDS','false'),(180,5,'SQL_CONNECT',NULL);
/*!40000 ALTER TABLE `r_database_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_database_contype`
--

DROP TABLE IF EXISTS `r_database_contype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_database_contype` (
  `ID_DATABASE_CONTYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DATABASE_CONTYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_database_contype`
--

LOCK TABLES `r_database_contype` WRITE;
/*!40000 ALTER TABLE `r_database_contype` DISABLE KEYS */;
INSERT INTO `r_database_contype` VALUES (1,'Native','Native (JDBC)'),(2,'ODBC','ODBC'),(3,'OCI','OCI'),(4,'Plugin','Plugin specific access method'),(5,'JNDI','JNDI');
/*!40000 ALTER TABLE `r_database_contype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_database_type`
--

DROP TABLE IF EXISTS `r_database_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_database_type` (
  `ID_DATABASE_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DATABASE_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_database_type`
--

LOCK TABLES `r_database_type` WRITE;
/*!40000 ALTER TABLE `r_database_type` DISABLE KEYS */;
INSERT INTO `r_database_type` VALUES (1,'DERBY','Apache Derby'),(2,'AS/400','AS/400'),(3,'INTERBASE','Borland Interbase'),(4,'INFINIDB','Calpont InfiniDB'),(5,'DBASE','dBase III, IV or 5'),(6,'EXASOL4','Exasol 4'),(7,'EXTENDB','ExtenDB'),(8,'FIREBIRD','Firebird SQL'),(9,'GENERIC','Generic database'),(10,'GREENPLUM','Greenplum'),(11,'SQLBASE','Gupta SQL Base'),(12,'H2','H2'),(13,'HYPERSONIC','Hypersonic'),(14,'DB2','IBM DB2'),(15,'INFOBRIGHT','Infobright'),(16,'INFORMIX','Informix'),(17,'INGRES','Ingres'),(18,'VECTORWISE','Ingres VectorWise'),(19,'CACHE','Intersystems Cache'),(20,'KettleThin','Kettle thin JDBC driver'),(21,'KINGBASEES','KingbaseES'),(22,'LucidDB','LucidDB'),(23,'SAPDB','MaxDB (SAP DB)'),(24,'MONETDB','MonetDB'),(25,'MSACCESS','MS Access'),(26,'MSSQL','MS SQL Server'),(27,'MSSQLNATIVE','MS SQL Server (Native)'),(28,'MYSQL','MySQL'),(29,'MONDRIAN','Native Mondrian'),(30,'NEOVIEW','Neoview'),(31,'NETEZZA','Netezza'),(32,'OpenERPDatabaseMeta','OpenERP Server'),(33,'ORACLE','Oracle'),(34,'ORACLERDB','Oracle RDB'),(35,'PALO','Palo MOLAP Server'),(36,'POSTGRESQL','PostgreSQL'),(37,'REMEDY-AR-SYSTEM','Remedy Action Request System'),(38,'SAPR3','SAP ERP System'),(39,'SQLITE','SQLite'),(40,'SYBASE','Sybase'),(41,'SYBASEIQ','SybaseIQ'),(42,'TERADATA','Teradata'),(43,'UNIVERSE','UniVerse database'),(44,'VERTICA','Vertica');
/*!40000 ALTER TABLE `r_database_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_dependency`
--

DROP TABLE IF EXISTS `r_dependency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_dependency` (
  `ID_DEPENDENCY` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  `TABLE_NAME` varchar(255) DEFAULT NULL,
  `FIELD_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DEPENDENCY`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_dependency`
--

LOCK TABLES `r_dependency` WRITE;
/*!40000 ALTER TABLE `r_dependency` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_dependency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_directory`
--

DROP TABLE IF EXISTS `r_directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_directory` (
  `ID_DIRECTORY` bigint(20) NOT NULL,
  `ID_DIRECTORY_PARENT` int(11) DEFAULT NULL,
  `DIRECTORY_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DIRECTORY`),
  UNIQUE KEY `IDX_DIRECTORY_AK` (`ID_DIRECTORY_PARENT`,`DIRECTORY_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_directory`
--

LOCK TABLES `r_directory` WRITE;
/*!40000 ALTER TABLE `r_directory` DISABLE KEYS */;
INSERT INTO `r_directory` VALUES (10,0,'_系统预置'),(0,NULL,'数据整合'),(1,NULL,'报表'),(21,1,'客户使用范例'),(20,1,'_系统预置'),(2,NULL,'模型'),(30,2,'_系统预置'),(11,0,'客户使用范例'),(31,2,'客户使用范例'),(32,11,'转换'),(33,11,'作业');
/*!40000 ALTER TABLE `r_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_job`
--

DROP TABLE IF EXISTS `r_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_job` (
  `ID_JOB` bigint(20) NOT NULL,
  `ID_DIRECTORY` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `EXTENDED_DESCRIPTION` mediumtext,
  `JOB_VERSION` varchar(255) DEFAULT NULL,
  `JOB_STATUS` int(11) DEFAULT NULL,
  `ID_DATABASE_LOG` int(11) DEFAULT NULL,
  `TABLE_NAME_LOG` varchar(255) DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  `USE_BATCH_ID` char(1) DEFAULT NULL,
  `PASS_BATCH_ID` char(1) DEFAULT NULL,
  `USE_LOGFIELD` char(1) DEFAULT NULL,
  `SHARED_FILE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_JOB`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_job`
--

LOCK TABLES `r_job` WRITE;
/*!40000 ALTER TABLE `r_job` DISABLE KEYS */;
INSERT INTO `r_job` VALUES (1,0,'我的第一个作业',NULL,NULL,NULL,0,-1,NULL,'-','2012-12-09 12:36:02','admin','2012-12-09 12:36:43','Y','N','Y',NULL);
/*!40000 ALTER TABLE `r_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_job_attribute`
--

DROP TABLE IF EXISTS `r_job_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_job_attribute` (
  `ID_JOB_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_JOB_ATTRIBUTE`),
  UNIQUE KEY `IDX_JOB_ATTRIBUTE_LOOKUP` (`ID_JOB`,`CODE`,`NR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_job_attribute`
--

LOCK TABLES `r_job_attribute` WRITE;
/*!40000 ALTER TABLE `r_job_attribute` DISABLE KEYS */;
INSERT INTO `r_job_attribute` VALUES (1,1,0,'LOG_SIZE_LIMIT',0,NULL),(2,1,0,'JOB_LOG_TABLE_CONNECTION_NAME',0,NULL),(3,1,0,'JOB_LOG_TABLE_SCHEMA_NAME',0,NULL),(4,1,0,'JOB_LOG_TABLE_TABLE_NAME',0,NULL),(5,1,0,'JOB_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(6,1,0,'JOB_LOG_TABLE_FIELD_ID0',0,'ID_JOB'),(7,1,0,'JOB_LOG_TABLE_FIELD_NAME0',0,'ID_JOB'),(8,1,0,'JOB_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(9,1,0,'JOB_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(10,1,0,'JOB_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(11,1,0,'JOB_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(12,1,0,'JOB_LOG_TABLE_FIELD_ID2',0,'JOBNAME'),(13,1,0,'JOB_LOG_TABLE_FIELD_NAME2',0,'JOBNAME'),(14,1,0,'JOB_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(15,1,0,'JOB_LOG_TABLE_FIELD_ID3',0,'STATUS'),(16,1,0,'JOB_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(17,1,0,'JOB_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(18,1,0,'JOB_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(19,1,0,'JOB_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(20,1,0,'JOB_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(21,1,0,'JOB_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(22,1,0,'JOB_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(23,1,0,'JOB_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(24,1,0,'JOB_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(25,1,0,'JOB_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(26,1,0,'JOB_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(27,1,0,'JOB_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(28,1,0,'JOB_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(29,1,0,'JOB_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(30,1,0,'JOB_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(31,1,0,'JOB_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(32,1,0,'JOB_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(33,1,0,'JOB_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(34,1,0,'JOB_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(35,1,0,'JOB_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(36,1,0,'JOB_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(37,1,0,'JOB_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(38,1,0,'JOB_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(39,1,0,'JOB_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(40,1,0,'JOB_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(41,1,0,'JOB_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(42,1,0,'JOB_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(43,1,0,'JOB_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(44,1,0,'JOB_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(45,1,0,'JOB_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(46,1,0,'JOB_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(47,1,0,'JOB_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(48,1,0,'JOB_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(49,1,0,'JOB_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(50,1,0,'JOB_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(51,1,0,'JOB_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(52,1,0,'JOB_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(53,1,0,'JOB_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(54,1,0,'JOB_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(55,1,0,'JOB_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(56,1,0,'JOB_LOG_TABLE_FIELD_ENABLED16',0,'Y'),(57,1,0,'JOB_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(58,1,0,'JOB_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(59,1,0,'JOB_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(60,1,0,'JOB_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(61,1,0,'JOB_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(62,1,0,'JOB_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(63,1,0,'JOBLOG_TABLE_INTERVAL',0,NULL),(64,1,0,'JOBLOG_TABLE_SIZE_LIMIT',0,NULL),(65,1,0,'JOB_ENTRY_LOG_TABLE_CONNECTION_NAME',0,NULL),(66,1,0,'JOB_ENTRY_LOG_TABLE_SCHEMA_NAME',0,NULL),(67,1,0,'JOB_ENTRY_LOG_TABLE_TABLE_NAME',0,NULL),(68,1,0,'JOB_ENTRY_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(69,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(70,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(71,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(72,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(73,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(74,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(75,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(76,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(77,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(78,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID3',0,'JOBNAME'),(79,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(80,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(81,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID4',0,'JOBENTRYNAME'),(82,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(83,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(84,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID5',0,'LINES_READ'),(85,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME5',0,'LINES_READ'),(86,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(87,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID6',0,'LINES_WRITTEN'),(88,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME6',0,'LINES_WRITTEN'),(89,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(90,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID7',0,'LINES_UPDATED'),(91,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME7',0,'LINES_UPDATED'),(92,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(93,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID8',0,'LINES_INPUT'),(94,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME8',0,'LINES_INPUT'),(95,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(96,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID9',0,'LINES_OUTPUT'),(97,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME9',0,'LINES_OUTPUT'),(98,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(99,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID10',0,'LINES_REJECTED'),(100,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME10',0,'LINES_REJECTED'),(101,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(102,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID11',0,'ERRORS'),(103,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME11',0,'ERRORS'),(104,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(105,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID12',0,'RESULT'),(106,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME12',0,'RESULT'),(107,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(108,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID13',0,'NR_RESULT_ROWS'),(109,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME13',0,'NR_RESULT_ROWS'),(110,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(111,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID14',0,'NR_RESULT_FILES'),(112,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME14',0,'NR_RESULT_FILES'),(113,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(114,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID15',0,'LOG_FIELD'),(115,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME15',0,'LOG_FIELD'),(116,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED15',0,'N'),(117,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ID16',0,'COPY_NR'),(118,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_NAME16',0,'COPY_NR'),(119,1,0,'JOB_ENTRY_LOG_TABLE_FIELD_ENABLED16',0,'N'),(120,1,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(121,1,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(122,1,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(123,1,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(124,1,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(125,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(126,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(127,1,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(128,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(129,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(130,1,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(131,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(132,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(133,1,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(134,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(135,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(136,1,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(137,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(138,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(139,1,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(140,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(141,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(142,1,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(143,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(144,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(145,1,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(146,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(147,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(148,1,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(149,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(150,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(151,1,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(152,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(153,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(154,1,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(155,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(156,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(157,1,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(158,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(159,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y');
/*!40000 ALTER TABLE `r_job_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_job_hop`
--

DROP TABLE IF EXISTS `r_job_hop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_job_hop` (
  `ID_JOB_HOP` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY_COPY_FROM` int(11) DEFAULT NULL,
  `ID_JOBENTRY_COPY_TO` int(11) DEFAULT NULL,
  `ENABLED` char(1) DEFAULT NULL,
  `EVALUATION` char(1) DEFAULT NULL,
  `UNCONDITIONAL` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_JOB_HOP`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_job_hop`
--

LOCK TABLES `r_job_hop` WRITE;
/*!40000 ALTER TABLE `r_job_hop` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_job_hop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_job_lock`
--

DROP TABLE IF EXISTS `r_job_lock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_job_lock` (
  `ID_JOB_LOCK` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_USER` int(11) DEFAULT NULL,
  `LOCK_MESSAGE` mediumtext,
  `LOCK_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_JOB_LOCK`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_job_lock`
--

LOCK TABLES `r_job_lock` WRITE;
/*!40000 ALTER TABLE `r_job_lock` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_job_lock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_job_note`
--

DROP TABLE IF EXISTS `r_job_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_job_note` (
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_NOTE` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_job_note`
--

LOCK TABLES `r_job_note` WRITE;
/*!40000 ALTER TABLE `r_job_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_job_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_jobentry`
--

DROP TABLE IF EXISTS `r_jobentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_jobentry` (
  `ID_JOBENTRY` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY_TYPE` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  PRIMARY KEY (`ID_JOBENTRY`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_jobentry`
--

LOCK TABLES `r_jobentry` WRITE;
/*!40000 ALTER TABLE `r_jobentry` DISABLE KEYS */;
INSERT INTO `r_jobentry` VALUES (1,1,65,'START',NULL),(2,1,48,'成功',NULL);
/*!40000 ALTER TABLE `r_jobentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_jobentry_attribute`
--

DROP TABLE IF EXISTS `r_jobentry_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_jobentry_attribute` (
  `ID_JOBENTRY_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` double DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_JOBENTRY_ATTRIBUTE`),
  UNIQUE KEY `IDX_JOBENTRY_ATTRIBUTE_LOOKUP` (`ID_JOBENTRY_ATTRIBUTE`,`CODE`,`NR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_jobentry_attribute`
--

LOCK TABLES `r_jobentry_attribute` WRITE;
/*!40000 ALTER TABLE `r_jobentry_attribute` DISABLE KEYS */;
INSERT INTO `r_jobentry_attribute` VALUES (1,1,1,0,'start',0,'Y'),(2,1,1,0,'dummy',0,'N'),(3,1,1,0,'repeat',0,'N'),(4,1,1,0,'schedulerType',0,NULL),(5,1,1,0,'intervalSeconds',0,NULL),(6,1,1,0,'intervalMinutes',60,NULL),(7,1,1,0,'hour',12,NULL),(8,1,1,0,'minutes',0,NULL),(9,1,1,0,'weekDay',1,NULL),(10,1,1,0,'dayOfMonth',1,NULL);
/*!40000 ALTER TABLE `r_jobentry_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_jobentry_copy`
--

DROP TABLE IF EXISTS `r_jobentry_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_jobentry_copy` (
  `ID_JOBENTRY_COPY` bigint(20) NOT NULL,
  `ID_JOBENTRY` int(11) DEFAULT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY_TYPE` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `GUI_LOCATION_X` int(11) DEFAULT NULL,
  `GUI_LOCATION_Y` int(11) DEFAULT NULL,
  `GUI_DRAW` char(1) DEFAULT NULL,
  `PARALLEL` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_JOBENTRY_COPY`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_jobentry_copy`
--

LOCK TABLES `r_jobentry_copy` WRITE;
/*!40000 ALTER TABLE `r_jobentry_copy` DISABLE KEYS */;
INSERT INTO `r_jobentry_copy` VALUES (1,1,1,65,0,214,148,'Y','N'),(2,2,1,48,0,364,150,'Y','N');
/*!40000 ALTER TABLE `r_jobentry_copy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_jobentry_database`
--

DROP TABLE IF EXISTS `r_jobentry_database`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_jobentry_database` (
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY` int(11) DEFAULT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  KEY `IDX_JOBENTRY_DATABASE_LU1` (`ID_JOB`),
  KEY `IDX_JOBENTRY_DATABASE_LU2` (`ID_DATABASE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_jobentry_database`
--

LOCK TABLES `r_jobentry_database` WRITE;
/*!40000 ALTER TABLE `r_jobentry_database` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_jobentry_database` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_jobentry_type`
--

DROP TABLE IF EXISTS `r_jobentry_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_jobentry_type` (
  `ID_JOBENTRY_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_JOBENTRY_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_jobentry_type`
--

LOCK TABLES `r_jobentry_type` WRITE;
/*!40000 ALTER TABLE `r_jobentry_type` DISABLE KEYS */;
INSERT INTO `r_jobentry_type` VALUES (1,'DOS_UNIX_CONVERTER','DOS和UNIX之间的文本转换'),(2,'DTD_VALIDATOR','DTD 验证'),(3,'DummyJob','Example plugin'),(4,'DataCleanerJobEntry','Execute DataCleaner job'),(5,'FTP_PUT','FTP 上传'),(6,'FTP','FTP 下载'),(7,'FTP_DELETE','FTP 删除'),(8,'FTPS_PUT','FTPS 上传'),(9,'FTPS_GET','FTPS 下载'),(10,'HL7MLLPAcknowledge','HL7 MLLP Acknowledge'),(11,'HL7MLLPInput','HL7 MLLP Input'),(12,'HTTP','HTTP'),(13,'MS_ACCESS_BULK_LOAD','MS Access 批量加载'),(14,'MYSQL_BULK_LOAD','Mysql 批量加载'),(15,'PALO_CUBE_CREATE','Palo Cube Create'),(16,'PALO_CUBE_DELETE','Palo Cube Delete'),(17,'PING','Ping 一台主机'),(18,'GET_POP','POP 收信'),(19,'SFTPPUT','SFTP 上传'),(20,'SFTP','SFTP 下载'),(21,'SHELL','Shell'),(22,'SQL','SQL'),(23,'MSSQL_BULK_LOAD','SQLServer 批量加载'),(24,'SSH2_PUT','SSH2 上传'),(25,'SSH2_GET','SSH2 下载'),(26,'TALEND_JOB_EXEC','Talend 作业执行'),(27,'XSD_VALIDATOR','XSD 验证器'),(28,'XSLT','XSL 转换'),(29,'ZIP_FILE','Zip 压缩文件'),(30,'ABORT','中止作业'),(31,'MYSQL_BULK_FILE','从 Mysql 批量导出到文件'),(32,'DELETE_RESULT_FILENAMES','从结果文件中删除文件'),(33,'JOB','作业'),(34,'EVAL','使用 JavaScript 脚本验证'),(35,'WRITE_TO_FILE','写入文件'),(36,'WRITE_TO_LOG','写日志'),(37,'CREATE_FOLDER','创建一个目录'),(38,'CREATE_FILE','创建文件'),(39,'DELETE_FILE','删除一个文件'),(40,'DELETE_FILES','删除多个文件'),(41,'DELETE_FOLDERS','删除目录'),(42,'SNMP_TRAP','发送 SNMP 自陷'),(43,'SEND_NAGIOS_PASSIVE_CHECK','发送Nagios 被动检查'),(44,'MAIL','发送邮件'),(45,'COPY_MOVE_RESULT_FILENAMES','复制/移动结果文件'),(46,'COPY_FILES','复制文件'),(47,'EXPORT_REPOSITORY','导出资源库到XML文件'),(48,'SUCCESS','成功'),(49,'MSGBOX_INFO','显示消息对话框'),(50,'XML_WELL_FORMED','检查 XML 文件格式'),(51,'WEBSERVICE_AVAILABLE','检查web服务是否可用'),(52,'FILE_EXISTS','检查一个文件是否存在'),(53,'COLUMNS_EXIST','检查列是否存在'),(54,'FILES_EXIST','检查多个文件是否存在'),(55,'CHECK_DB_CONNECTIONS','检查数据库连接'),(56,'CHECK_FILES_LOCKED','检查文件是否被锁'),(57,'CONNECTED_TO_REPOSITORY','检查是否连接到资源库'),(58,'FOLDER_IS_EMPTY','检查目录是否为空'),(59,'TABLE_EXISTS','检查表是否存在'),(60,'SIMPLE_EVAL','检验字段的值'),(61,'FILE_COMPARE','比较文件'),(62,'FOLDERS_COMPARE','比较目录'),(63,'ADD_RESULT_FILENAMES','添加文件到结果文件中'),(64,'TRUNCATE_TABLES','清空表'),(65,'SPECIAL','特殊作业项'),(66,'SYSLOG','用 Syslog 发送信息'),(67,'PGP_ENCRYPT_FILES','用PGP加密文件'),(68,'PGP_DECRYPT_FILES','用PGP解密文件'),(69,'PGP_VERIFY_FILES','用PGP验证文件签名'),(70,'MOVE_FILES','移动文件'),(71,'DELAY','等待'),(72,'WAIT_FOR_SQL','等待SQL'),(73,'WAIT_FOR_FILE','等待文件'),(74,'UNZIP','解压缩文件'),(75,'EVAL_FILES_METRICS','计算文件大小或个数'),(76,'EVAL_TABLE_CONTENT','计算表中的记录数'),(77,'SET_VARIABLES','设置变量'),(78,'TRANS','转换'),(79,'TELNET','远程登录一台主机'),(80,'MAIL_VALIDATOR','邮件验证');
/*!40000 ALTER TABLE `r_jobentry_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_log`
--

DROP TABLE IF EXISTS `r_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_log` (
  `ID_LOG` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ID_LOGLEVEL` int(11) DEFAULT NULL,
  `LOGTYPE` varchar(255) DEFAULT NULL,
  `FILENAME` varchar(255) DEFAULT NULL,
  `FILEEXTENTION` varchar(255) DEFAULT NULL,
  `ADD_DATE` char(1) DEFAULT NULL,
  `ADD_TIME` char(1) DEFAULT NULL,
  `ID_DATABASE_LOG` int(11) DEFAULT NULL,
  `TABLE_NAME_LOG` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_LOG`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_log`
--

LOCK TABLES `r_log` WRITE;
/*!40000 ALTER TABLE `r_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_loglevel`
--

DROP TABLE IF EXISTS `r_loglevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_loglevel` (
  `ID_LOGLEVEL` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_LOGLEVEL`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_loglevel`
--

LOCK TABLES `r_loglevel` WRITE;
/*!40000 ALTER TABLE `r_loglevel` DISABLE KEYS */;
INSERT INTO `r_loglevel` VALUES (1,'Error','错误日志'),(2,'Minimal','最小日志'),(3,'Basic','基本日志'),(4,'Detailed','详细日志'),(5,'Debug','调试'),(6,'Rowlevel','行级日志(非常详细)');
/*!40000 ALTER TABLE `r_loglevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_note`
--

DROP TABLE IF EXISTS `r_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_note` (
  `ID_NOTE` bigint(20) NOT NULL,
  `VALUE_STR` mediumtext,
  `GUI_LOCATION_X` int(11) DEFAULT NULL,
  `GUI_LOCATION_Y` int(11) DEFAULT NULL,
  `GUI_LOCATION_WIDTH` int(11) DEFAULT NULL,
  `GUI_LOCATION_HEIGHT` int(11) DEFAULT NULL,
  `FONT_NAME` mediumtext,
  `FONT_SIZE` int(11) DEFAULT NULL,
  `FONT_BOLD` char(1) DEFAULT NULL,
  `FONT_ITALIC` char(1) DEFAULT NULL,
  `FONT_COLOR_RED` int(11) DEFAULT NULL,
  `FONT_COLOR_GREEN` int(11) DEFAULT NULL,
  `FONT_COLOR_BLUE` int(11) DEFAULT NULL,
  `FONT_BACK_GROUND_COLOR_RED` int(11) DEFAULT NULL,
  `FONT_BACK_GROUND_COLOR_GREEN` int(11) DEFAULT NULL,
  `FONT_BACK_GROUND_COLOR_BLUE` int(11) DEFAULT NULL,
  `FONT_BORDER_COLOR_RED` int(11) DEFAULT NULL,
  `FONT_BORDER_COLOR_GREEN` int(11) DEFAULT NULL,
  `FONT_BORDER_COLOR_BLUE` int(11) DEFAULT NULL,
  `DRAW_SHADOW` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_NOTE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_note`
--

LOCK TABLES `r_note` WRITE;
/*!40000 ALTER TABLE `r_note` DISABLE KEYS */;
INSERT INTO `r_note` VALUES (1,'If you do a preview on the Result step you will note that the generated ID is unique.\nIf you specify another counter name in either of the sequence generator steps, you will get duplicates.\n\nStep \"Generate Rows\" does not actually produce output. It creates 1000 empty lines and splits them evenly\nbefore they reach the two \"Generate ID\" steps (step type \"sequence generator\"). Both sequence generators\ndo have the same \"Counter name\" (double click to edit and see the \"Counter name\" value). That makes the\ngenerated ids unique, even though they are produced by two different sequence generators.',32,28,631,116,NULL,-1,'N','N',0,0,0,255,165,0,100,100,100,'N'),(2,'You basically get the same result if you have 2 row generators (both generating 20 lines, with no\nactual output) that are linked to two ID sequence generators. Both ID sequence generators have got the\nsame \"counter name\" and thus the finally created IDs are unique. The difference to the example above is\nthat the generated IDs are in order (enumerated from 1..40).',45,472,611,70,NULL,-1,'N','N',0,0,0,255,165,0,100,100,100,'N');
/*!40000 ALTER TABLE `r_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_partition`
--

DROP TABLE IF EXISTS `r_partition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_partition` (
  `ID_PARTITION` bigint(20) NOT NULL,
  `ID_PARTITION_SCHEMA` int(11) DEFAULT NULL,
  `PARTITION_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_PARTITION`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_partition`
--

LOCK TABLES `r_partition` WRITE;
/*!40000 ALTER TABLE `r_partition` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_partition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_partition_schema`
--

DROP TABLE IF EXISTS `r_partition_schema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_partition_schema` (
  `ID_PARTITION_SCHEMA` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DYNAMIC_DEFINITION` char(1) DEFAULT NULL,
  `PARTITIONS_PER_SLAVE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_PARTITION_SCHEMA`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_partition_schema`
--

LOCK TABLES `r_partition_schema` WRITE;
/*!40000 ALTER TABLE `r_partition_schema` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_partition_schema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_repository_log`
--

DROP TABLE IF EXISTS `r_repository_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_repository_log` (
  `ID_REPOSITORY_LOG` bigint(20) NOT NULL,
  `REP_VERSION` varchar(255) DEFAULT NULL,
  `LOG_DATE` datetime DEFAULT NULL,
  `LOG_USER` varchar(255) DEFAULT NULL,
  `OPERATION_DESC` mediumtext,
  PRIMARY KEY (`ID_REPOSITORY_LOG`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_repository_log`
--

LOCK TABLES `r_repository_log` WRITE;
/*!40000 ALTER TABLE `r_repository_log` DISABLE KEYS */;
INSERT INTO `r_repository_log` VALUES (1,'4.0','2012-10-27 14:01:29','admin','save transformation \'转换 1\''),(2,'4.0','2012-10-27 14:02:05','admin','save transformation \'转换 1\''),(3,'5.0','2012-11-21 11:33:02','admin','Creation of initial version'),(4,'5.0','2012-11-21 13:28:48','admin','save transformation \'转换 1\''),(5,'5.0','2012-12-06 14:36:27','admin','save transformation \'转换 1\''),(6,'5.0','2012-12-06 14:37:48','admin','save transformation \'测试用的转换\''),(7,'5.0','2012-12-09 12:36:43','admin','save job \'测试作业 1\''),(8,'5.0','2013-01-10 15:30:58','admin','save transformation \'测试用的转换\''),(9,'5.0','2013-01-10 15:37:00','admin','save transformation \'测试用的转换\''),(10,'5.0','2013-01-10 16:55:17','admin','save transformation \'测试用的转换\''),(11,'5.0','2013-01-10 16:55:44','admin','save transformation \'测试用的转换\''),(12,'5.0','2013-01-10 17:00:51','admin','save transformation \'测试用的转换\''),(13,'5.0','2013-01-10 17:01:15','admin','save transformation \'测试用的转换\''),(14,'5.0','2013-01-10 17:01:43','admin','save transformation \'测试用的转换\''),(15,'5.0','2013-01-10 17:01:50','admin','save transformation \'测试用的转换\''),(16,'5.0','2013-01-10 21:26:39','admin','save transformation \'测试用的转换\''),(17,'5.0','2013-01-10 21:49:17','admin','save transformation \'测试用的转换\''),(18,'5.0','2013-01-10 21:49:59','admin','save transformation \'测试用的转换\''),(19,'5.0','2013-01-10 23:14:15','admin','save transformation \'测试用的转换\''),(20,'5.0','2013-01-10 23:17:46','admin','save transformation \'测试用的转换\''),(21,'5.0','2013-01-10 23:35:49','admin','save transformation \'测试用的转换\''),(22,'5.0','2013-01-10 23:36:08','admin','save transformation \'测试用的转换\''),(23,'5.0','2013-01-10 23:37:40','admin','save transformation \'转换 1\''),(24,'5.0','2013-01-10 23:38:10','admin','save transformation \'转换 1\''),(25,'5.0','2013-01-10 23:40:24','admin','save transformation \'转换 1\''),(26,'5.0','2013-01-10 23:41:14','admin','save transformation \'测试用的转换\''),(27,'5.0','2013-01-10 23:41:32','admin','save transformation \'转换 1\''),(28,'5.0','2013-01-10 23:41:59','admin','save transformation \'测试用的转换\''),(29,'5.0','2013-01-10 23:43:08','admin','save transformation \'测试用的转换\''),(30,'5.0','2013-01-10 23:44:54','admin','save transformation \'测试用的转换\''),(31,'5.0','2013-01-10 23:58:20','admin','save transformation \'测试用的转换\''),(32,'5.0','2013-01-11 07:13:45','admin','save transformation \'测试用的转换\''),(33,'5.0','2013-01-11 07:18:07','admin','save transformation \'测试用的转换\''),(34,'5.0','2013-01-11 07:18:18','admin','save transformation \'测试用的转换\''),(35,'5.0','2013-01-11 07:20:31','admin','save transformation \'测试用的转换\''),(36,'5.0','2013-01-11 09:22:06','admin','save transformation \'测试用的转换\''),(37,'5.0','2013-01-11 09:22:44','admin','save transformation \'测试用的转换\''),(38,'5.0','2013-01-11 09:22:59','admin','save transformation \'测试用的转换\''),(39,'5.0','2013-01-11 09:23:12','admin','save transformation \'测试用的转换\''),(40,'5.0','2013-01-11 09:23:44','admin','save transformation \'测试用的转换\''),(41,'5.0','2013-01-11 13:44:15','admin','save transformation \'测试用的转换\''),(42,'5.0','2013-01-11 13:44:22','admin','save transformation \'测试用的转换\''),(43,'5.0','2013-01-12 06:32:13','admin','save transformation \'测试用的转换\''),(44,'5.0','2013-01-12 06:33:08','admin','save transformation \'测试用的转换\''),(45,'5.0','2013-01-20 09:27:22','admin','save transformation \'测试用的转换\''),(46,'5.0','2013-01-20 09:28:01','admin','save transformation \'测试用的转换\''),(47,'5.0','2013-07-07 11:59:13','admin','save transformation \'测试用的转换\''),(48,'5.0','2013-07-07 12:01:48','admin','save transformation \'测试用的转换\''),(49,'5.0','2013-07-07 12:03:27','admin','save transformation \'测试用的转换\''),(50,'5.0','2013-07-07 12:07:42','admin','save transformation \'测试用的转换\''),(51,'5.0','2013-07-07 12:32:49','admin','save transformation \'测试用的转换\''),(52,'5.0','2013-07-24 00:29:27','admin','save transformation \'我的第一个转换\''),(53,'5.0','2013-07-24 00:31:11','admin','save transformation \'我的第一个转换\''),(54,'5.0','2013-08-22 16:25:32','admin','save transformation \'我的第一个转换\''),(55,'5.0','2013-08-22 19:04:20','admin','save transformation \'我的第一个转换\''),(56,'5.0','2013-08-22 19:04:48','admin','save transformation \'我的第一个转换\''),(57,'5.0','2013-08-22 19:05:21','admin','save transformation \'我的第一个转换\''),(58,'5.0','2013-08-22 19:14:25','admin','save transformation \'增加校验列 - 基于CRC32的例子\''),(59,'5.0','2013-08-22 19:16:22','admin','save transformation \'增加校验列 - 基于CRC32的例子\''),(60,'5.0','2013-08-22 19:21:13','admin','save transformation \'增加序列 - 基本例子\''),(61,'5.0','2013-08-22 19:21:55','admin','save transformation \'增加序列 - 基本例子\''),(62,'5.0','2013-08-22 19:24:21','admin','save transformation \'增加序列 - 指定一个通用计数器\''),(63,'5.0','2013-08-22 19:29:59','admin','save transformation \'增加一个序号字段 - 基本例子\''),(64,'5.0','2013-10-29 21:38:59','admin','save transformation \'我的第一个转换\'');
/*!40000 ALTER TABLE `r_repository_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_role`
--

DROP TABLE IF EXISTS `r_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_role` (
  `ID_ROLE` bigint(20) NOT NULL,
  `ROLE_NAME` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_SYS` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_role`
--

LOCK TABLES `r_role` WRITE;
/*!40000 ALTER TABLE `r_role` DISABLE KEYS */;
INSERT INTO `r_role` VALUES (0,'anomyous','匿名用户角色','Y'),(1,'super_admin','超级管理员角色','Y'),(2,'admin','管理员角色','Y'),(10,'di_editor','DI编辑员角色','Y'),(11,'di_operator','DI操作员角色','Y'),(100,'forecaster','预报员角色','N');
/*!40000 ALTER TABLE `r_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_slave`
--

DROP TABLE IF EXISTS `r_slave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_slave` (
  `ID_SLAVE` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `HOST_NAME` varchar(255) DEFAULT NULL,
  `PORT` varchar(255) DEFAULT NULL,
  `WEB_APP_NAME` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PROXY_HOST_NAME` varchar(255) DEFAULT NULL,
  `PROXY_PORT` varchar(255) DEFAULT NULL,
  `NON_PROXY_HOSTS` varchar(255) DEFAULT NULL,
  `MASTER` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_SLAVE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_slave`
--

LOCK TABLES `r_slave` WRITE;
/*!40000 ALTER TABLE `r_slave` DISABLE KEYS */;
INSERT INTO `r_slave` VALUES (1,'ID-Server','localhost','8181',NULL,'cluster','Encrypted 2be98afc86aa7f2e4cb1aa265cd86aac8',NULL,NULL,NULL,'N');
/*!40000 ALTER TABLE `r_slave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_step`
--

DROP TABLE IF EXISTS `r_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_step` (
  `ID_STEP` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `ID_STEP_TYPE` int(11) DEFAULT NULL,
  `DISTRIBUTE` char(1) DEFAULT NULL,
  `COPIES` int(11) DEFAULT NULL,
  `GUI_LOCATION_X` int(11) DEFAULT NULL,
  `GUI_LOCATION_Y` int(11) DEFAULT NULL,
  `GUI_DRAW` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_STEP`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_step`
--

LOCK TABLES `r_step` WRITE;
/*!40000 ALTER TABLE `r_step` DISABLE KEYS */;
INSERT INTO `r_step` VALUES (4,2,'生成记录',NULL,162,'Y',1,184,108,'Y'),(19,1,'表输出',NULL,182,'Y',1,384,184,'Y'),(18,1,'Access 输入',NULL,1,'Y',1,184,184,'Y'),(3,2,'增加校验列',NULL,111,'Y',4,389,110,'Y'),(6,3,'生成记录',NULL,162,'Y',1,184,213,'Y'),(5,3,'增加校验列',NULL,111,'Y',1,374,213,'Y'),(7,4,'Gen ID [1]',NULL,110,'Y',1,352,576,'Y'),(8,4,'Gen ID [2]',NULL,110,'Y',1,351,736,'Y'),(9,4,'Generate ID',NULL,110,'Y',1,355,179,'Y'),(10,4,'Generate ID (2)',NULL,110,'Y',1,360,361,'Y'),(11,4,'Generate Rows',NULL,162,'Y',1,188,267,'Y'),(12,4,'Result',NULL,165,'Y',1,563,251,'Y'),(13,4,'Result 2',NULL,165,'Y',1,568,655,'Y'),(14,4,'Row Gen [1]',NULL,162,'Y',1,158,631,'Y'),(15,4,'Row Gen [2]',NULL,162,'Y',1,157,698,'Y'),(16,5,'Data Grid',NULL,170,'Y',1,235,122,'Y'),(17,5,'nr',NULL,147,'Y',1,434,122,'Y');
/*!40000 ALTER TABLE `r_step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_step_attribute`
--

DROP TABLE IF EXISTS `r_step_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_step_attribute` (
  `ID_STEP_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_STEP_ATTRIBUTE`),
  UNIQUE KEY `IDX_STEP_ATTRIBUTE_LOOKUP` (`ID_STEP`,`CODE`,`NR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_step_attribute`
--

LOCK TABLES `r_step_attribute` WRITE;
/*!40000 ALTER TABLE `r_step_attribute` DISABLE KEYS */;
INSERT INTO `r_step_attribute` VALUES (77,2,4,2,'field_name',0,'transaction_date'),(78,2,4,2,'field_type',0,'Date'),(75,2,4,1,'field_precision',-1,NULL),(76,2,4,1,'set_empty_string',0,'N'),(74,2,4,1,'field_length',-1,NULL),(73,2,4,1,'field_nullif',0,'The 3 quick brown foxes jumped over the 7 lazy dogs.'),(72,2,4,1,'field_group',0,NULL),(331,1,19,0,'cluster_schema',0,NULL),(330,1,19,0,'return_field',0,NULL),(329,1,19,0,'return_keys',0,'N'),(328,1,19,0,'tablename_in_table',0,'Y'),(327,1,19,0,'tablename_field',0,NULL),(326,1,19,0,'tablename_in_field',0,'N'),(325,1,19,0,'partitioning_monthly',0,'Y'),(324,1,19,0,'partitioning_daily',0,'N'),(323,1,19,0,'partitioning_field',0,NULL),(322,1,19,0,'partitioning_enabled',0,'N'),(321,1,19,0,'specify_fields',0,'N'),(320,1,19,0,'use_batch',0,'Y'),(319,1,19,0,'ignore_errors',0,'N'),(318,1,19,0,'truncate',0,'N'),(317,1,19,0,'commit',0,'1000'),(316,1,19,0,'table',0,NULL),(315,1,19,0,'schema',0,NULL),(314,1,19,0,'PARTITIONING_METHOD',0,'none'),(313,1,19,0,'PARTITIONING_SCHEMA',0,NULL),(312,1,18,0,'loadbalance',0,'N'),(311,1,18,0,'cluster_schema',0,NULL),(310,1,18,0,'extensionFieldName',0,NULL),(309,1,18,0,'rootUriNameFieldName',0,NULL),(308,1,18,0,'uriNameFieldName',0,NULL),(307,1,18,0,'lastModificationTimeFieldName',0,NULL),(306,1,18,0,'hiddenFieldName',0,NULL),(305,1,18,0,'pathFieldName',0,NULL),(304,1,18,0,'shortFileFieldName',0,NULL),(303,1,18,0,'reset_rownumber',0,'N'),(302,1,18,0,'table_name',0,NULL),(301,1,18,0,'limit',0,NULL),(300,1,18,0,'rownum_field',0,NULL),(299,1,18,0,'filename_Field',0,NULL),(298,1,18,0,'filefield',0,'N'),(297,1,18,0,'isaddresult',0,'Y'),(296,1,18,0,'rownum',0,'N'),(295,1,18,0,'tablename_field',0,NULL),(294,1,18,0,'tablename',0,'N'),(293,1,18,0,'include_field',0,NULL),(292,1,18,0,'include',0,'N'),(291,1,18,0,'PARTITIONING_METHOD',0,'none'),(290,1,18,0,'PARTITIONING_SCHEMA',0,NULL),(71,2,4,1,'field_decimal',0,NULL),(70,2,4,1,'field_currency',0,NULL),(69,2,4,1,'field_format',0,NULL),(68,2,4,1,'field_type',0,'String'),(67,2,4,1,'field_name',0,'description'),(66,2,4,0,'set_empty_string',0,'N'),(65,2,4,0,'field_precision',-1,NULL),(64,2,4,0,'field_length',-1,NULL),(63,2,4,0,'field_nullif',0,'12394'),(62,2,4,0,'field_group',0,NULL),(61,2,4,0,'field_decimal',0,NULL),(60,2,4,0,'field_currency',0,NULL),(59,2,4,0,'field_format',0,NULL),(58,2,4,0,'field_type',0,'Integer'),(56,2,4,0,'PARTITIONING_METHOD',0,'none'),(57,2,4,0,'field_name',0,'id'),(55,2,4,0,'PARTITIONING_SCHEMA',0,NULL),(54,2,3,0,'loadbalance',0,'N'),(53,2,3,0,'cluster_schema',0,NULL),(52,2,3,2,'field_name',0,'transaction_date'),(51,2,3,1,'field_name',0,'description'),(50,2,3,0,'field_name',0,'id'),(49,2,3,0,'compatibilityMode',0,'Y'),(48,2,3,0,'resultType',0,'string'),(47,2,3,0,'resultfieldName',0,'checksum'),(46,2,3,0,'checksumtype',0,'CRC32'),(45,2,3,0,'PARTITIONING_METHOD',0,'none'),(44,2,3,0,'PARTITIONING_SCHEMA',0,NULL),(79,2,4,2,'field_format',0,'yyyy/MM/dd HH:mm:ss'),(80,2,4,2,'field_currency',0,NULL),(81,2,4,2,'field_decimal',0,NULL),(82,2,4,2,'field_group',0,NULL),(83,2,4,2,'field_nullif',0,'2008/04/23 12:34:56'),(84,2,4,2,'field_length',-1,NULL),(85,2,4,2,'field_precision',-1,NULL),(86,2,4,2,'set_empty_string',0,'N'),(87,2,4,0,'limit',0,'100000'),(88,2,4,0,'cluster_schema',0,NULL),(89,2,4,0,'loadbalance',0,'N'),(139,3,6,3,'field_decimal',0,NULL),(138,3,6,3,'field_currency',0,NULL),(137,3,6,3,'field_format',0,NULL),(136,3,6,3,'field_type',0,'Boolean'),(135,3,6,3,'field_name',0,'D'),(134,3,6,2,'set_empty_string',0,'N'),(133,3,6,2,'field_precision',-1,NULL),(132,3,6,2,'field_length',-1,NULL),(131,3,6,2,'field_nullif',0,'19820319'),(130,3,6,2,'field_group',0,NULL),(129,3,6,2,'field_decimal',0,NULL),(128,3,6,2,'field_currency',0,NULL),(127,3,6,2,'field_format',0,NULL),(126,3,6,2,'field_type',0,'Integer'),(125,3,6,2,'field_name',0,'C'),(124,3,6,1,'set_empty_string',0,'N'),(123,3,6,1,'field_precision',-1,NULL),(122,3,6,1,'field_length',-1,NULL),(121,3,6,1,'field_nullif',0,'2008/12/31 18:29:12'),(120,3,6,1,'field_group',0,NULL),(119,3,6,1,'field_decimal',0,NULL),(118,3,6,1,'field_currency',0,NULL),(117,3,6,1,'field_format',0,'yyyy/MM/dd HH:mm:ss'),(116,3,6,1,'field_type',0,'Date'),(115,3,6,1,'field_name',0,'B'),(114,3,6,0,'set_empty_string',0,'N'),(113,3,6,0,'field_precision',-1,NULL),(112,3,6,0,'field_length',-1,NULL),(111,3,6,0,'field_nullif',0,'Foobar'),(110,3,6,0,'field_group',0,NULL),(109,3,6,0,'field_decimal',0,NULL),(108,3,6,0,'field_currency',0,NULL),(107,3,6,0,'field_format',0,NULL),(106,3,6,0,'field_type',0,'String'),(105,3,6,0,'field_name',0,'A'),(104,3,6,0,'PARTITIONING_METHOD',0,'none'),(103,3,6,0,'PARTITIONING_SCHEMA',0,NULL),(102,3,5,0,'loadbalance',0,'N'),(101,3,5,0,'cluster_schema',0,NULL),(100,3,5,4,'field_name',0,'E'),(99,3,5,3,'field_name',0,'D'),(98,3,5,2,'field_name',0,'C'),(97,3,5,1,'field_name',0,'B'),(96,3,5,0,'field_name',0,'A'),(95,3,5,0,'compatibilityMode',0,'Y'),(94,3,5,0,'resultType',0,'string'),(93,3,5,0,'resultfieldName',0,'checksum'),(92,3,5,0,'checksumtype',0,'CRC32'),(91,3,5,0,'PARTITIONING_METHOD',0,'none'),(90,3,5,0,'PARTITIONING_SCHEMA',0,NULL),(140,3,6,3,'field_group',0,NULL),(141,3,6,3,'field_nullif',0,'true'),(142,3,6,3,'field_length',-1,NULL),(143,3,6,3,'field_precision',-1,NULL),(144,3,6,3,'set_empty_string',0,'N'),(145,3,6,4,'field_name',0,'E'),(146,3,6,4,'field_type',0,'Number'),(147,3,6,4,'field_format',0,NULL),(148,3,6,4,'field_currency',0,NULL),(149,3,6,4,'field_decimal',0,'.'),(150,3,6,4,'field_group',0,','),(151,3,6,4,'field_nullif',0,'123.456'),(152,3,6,4,'field_length',-1,NULL),(153,3,6,4,'field_precision',-1,NULL),(154,3,6,4,'set_empty_string',0,'N'),(155,3,6,0,'limit',0,'1'),(156,3,6,0,'cluster_schema',0,NULL),(157,3,6,0,'loadbalance',0,'N'),(158,4,7,0,'PARTITIONING_SCHEMA',0,NULL),(159,4,7,0,'PARTITIONING_METHOD',0,'none'),(160,4,7,0,'valuename',0,'AnotherID'),(161,4,7,0,'use_database',0,'N'),(162,4,7,0,'schema',0,NULL),(163,4,7,0,'seqname',0,'SEQ_'),(164,4,7,0,'use_counter',0,'Y'),(165,4,7,0,'counter_name',0,'another-counter'),(166,4,7,0,'start_at',0,'1'),(167,4,7,0,'increment_by',0,'1'),(168,4,7,0,'max_value',0,'9999999'),(169,4,7,0,'cluster_schema',0,NULL),(170,4,7,0,'loadbalance',0,'N'),(171,4,8,0,'PARTITIONING_SCHEMA',0,NULL),(172,4,8,0,'PARTITIONING_METHOD',0,'none'),(173,4,8,0,'valuename',0,'AnotherID'),(174,4,8,0,'use_database',0,'N'),(175,4,8,0,'schema',0,NULL),(176,4,8,0,'seqname',0,'SEQ_'),(177,4,8,0,'use_counter',0,'Y'),(178,4,8,0,'counter_name',0,'another-counter'),(179,4,8,0,'start_at',0,'1'),(180,4,8,0,'increment_by',0,'1'),(181,4,8,0,'max_value',0,'9999999'),(182,4,8,0,'cluster_schema',0,NULL),(183,4,8,0,'loadbalance',0,'N'),(184,4,9,0,'PARTITIONING_SCHEMA',0,NULL),(185,4,9,0,'PARTITIONING_METHOD',0,'none'),(186,4,9,0,'valuename',0,'ID'),(187,4,9,0,'use_database',0,'N'),(188,4,9,0,'schema',0,NULL),(189,4,9,0,'seqname',0,'SEQ_'),(190,4,9,0,'use_counter',0,'Y'),(191,4,9,0,'counter_name',0,'id-counter'),(192,4,9,0,'start_at',0,'1'),(193,4,9,0,'increment_by',0,'1'),(194,4,9,0,'max_value',0,'9999999'),(195,4,9,0,'cluster_schema',0,NULL),(196,4,9,0,'loadbalance',0,'N'),(197,4,10,0,'PARTITIONING_SCHEMA',0,NULL),(198,4,10,0,'PARTITIONING_METHOD',0,'none'),(199,4,10,0,'valuename',0,'ID'),(200,4,10,0,'use_database',0,'N'),(201,4,10,0,'schema',0,NULL),(202,4,10,0,'seqname',0,'SEQ_'),(203,4,10,0,'use_counter',0,'Y'),(204,4,10,0,'counter_name',0,'id-counter'),(205,4,10,0,'start_at',0,'1'),(206,4,10,0,'increment_by',0,'1'),(207,4,10,0,'max_value',0,'9999999'),(208,4,10,0,'cluster_schema',0,NULL),(209,4,10,0,'loadbalance',0,'N'),(210,4,11,0,'PARTITIONING_SCHEMA',0,NULL),(211,4,11,0,'PARTITIONING_METHOD',0,'none'),(212,4,11,0,'limit',0,'40'),(213,4,11,0,'cluster_schema',0,NULL),(214,4,11,0,'loadbalance',0,'N'),(215,4,12,0,'PARTITIONING_SCHEMA',0,NULL),(216,4,12,0,'PARTITIONING_METHOD',0,'none'),(217,4,12,0,'cluster_schema',0,NULL),(218,4,12,0,'loadbalance',0,'N'),(219,4,13,0,'PARTITIONING_SCHEMA',0,NULL),(220,4,13,0,'PARTITIONING_METHOD',0,'none'),(221,4,13,0,'cluster_schema',0,NULL),(222,4,13,0,'loadbalance',0,'N'),(223,4,14,0,'PARTITIONING_SCHEMA',0,NULL),(224,4,14,0,'PARTITIONING_METHOD',0,'none'),(225,4,14,0,'limit',0,'20'),(226,4,14,0,'cluster_schema',0,NULL),(227,4,14,0,'loadbalance',0,'N'),(228,4,15,0,'PARTITIONING_SCHEMA',0,NULL),(229,4,15,0,'PARTITIONING_METHOD',0,'none'),(230,4,15,0,'limit',0,'20'),(231,4,15,0,'cluster_schema',0,NULL),(232,4,15,0,'loadbalance',0,'N'),(233,5,16,0,'PARTITIONING_SCHEMA',0,NULL),(234,5,16,0,'PARTITIONING_METHOD',0,'none'),(235,5,16,0,'field_name',0,'group'),(236,5,16,0,'field_type',0,'Integer'),(237,5,16,0,'field_format',0,'#'),(238,5,16,0,'field_currency',0,NULL),(239,5,16,0,'field_decimal',0,NULL),(240,5,16,0,'field_group',0,NULL),(241,5,16,0,'field_length',7,NULL),(242,5,16,0,'field_precision',-1,NULL),(243,5,16,0,'set_empty_string',0,'N'),(244,5,16,1,'field_name',0,'value'),(245,5,16,1,'field_type',0,'String'),(246,5,16,1,'field_format',0,NULL),(247,5,16,1,'field_currency',0,NULL),(248,5,16,1,'field_decimal',0,NULL),(249,5,16,1,'field_group',0,NULL),(250,5,16,1,'field_length',50,NULL),(251,5,16,1,'field_precision',-1,NULL),(252,5,16,1,'set_empty_string',0,'N'),(253,5,16,0,'nr_lines',13,NULL),(254,5,16,0,'item_0',0,'1'),(255,5,16,0,'item_1',0,'One'),(256,5,16,1,'item_0',0,'1'),(257,5,16,1,'item_1',0,'Two'),(258,5,16,2,'item_0',0,'1'),(259,5,16,2,'item_1',0,'Three'),(260,5,16,3,'item_0',0,'1'),(261,5,16,3,'item_1',0,'Four'),(262,5,16,4,'item_0',0,'2'),(263,5,16,4,'item_1',0,'Five'),(264,5,16,5,'item_0',0,'2'),(265,5,16,5,'item_1',0,'Six'),(266,5,16,6,'item_0',0,'2'),(267,5,16,6,'item_1',0,'Seven'),(268,5,16,7,'item_0',0,'3'),(269,5,16,7,'item_1',0,'Eight'),(270,5,16,8,'item_0',0,'3'),(271,5,16,8,'item_1',0,'Nine'),(272,5,16,9,'item_0',0,'4'),(273,5,16,9,'item_1',0,'Ten'),(274,5,16,10,'item_0',0,'4'),(275,5,16,10,'item_1',0,'Eleven'),(276,5,16,11,'item_0',0,'4'),(277,5,16,11,'item_1',0,'Twelve'),(278,5,16,12,'item_0',0,'4'),(279,5,16,12,'item_1',0,'Thirteen'),(280,5,16,0,'cluster_schema',0,NULL),(281,5,16,0,'loadbalance',0,'N'),(282,5,17,0,'PARTITIONING_SCHEMA',0,NULL),(283,5,17,0,'PARTITIONING_METHOD',0,'none'),(284,5,17,0,'start',0,'1'),(285,5,17,0,'increment',0,'1'),(286,5,17,0,'resultfieldName',0,'nr'),(287,5,17,0,'field_name',0,'group'),(288,5,17,0,'cluster_schema',0,NULL),(289,5,17,0,'loadbalance',0,'N'),(332,1,19,0,'loadbalance',0,'N');
/*!40000 ALTER TABLE `r_step_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_step_database`
--

DROP TABLE IF EXISTS `r_step_database`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_step_database` (
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP` int(11) DEFAULT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  KEY `IDX_STEP_DATABASE_LU1` (`ID_TRANSFORMATION`),
  KEY `IDX_STEP_DATABASE_LU2` (`ID_DATABASE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_step_database`
--

LOCK TABLES `r_step_database` WRITE;
/*!40000 ALTER TABLE `r_step_database` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_step_database` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_step_type`
--

DROP TABLE IF EXISTS `r_step_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_step_type` (
  `ID_STEP_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `HELPTEXT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_STEP_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_step_type`
--

LOCK TABLES `r_step_type` WRITE;
/*!40000 ALTER TABLE `r_step_type` DISABLE KEYS */;
INSERT INTO `r_step_type` VALUES (1,'AccessInput','Access 输入','Read data from a Microsoft Access file'),(2,'AccessOutput','Access 输出','Stores records into an MS-Access database table.'),(3,'BIRTOutput','BIRT Output','Executes a BIRT Report Design (.rptdesign)'),(4,'ClosureGenerator','Closure Generator','This step allows you to generates a closure table using parent-child relationships.'),(5,'CsvInput','CSV文件输入','Simple CSV file input'),(6,'CubeInput','Cube 文件输入','从一个cube读取记录.'),(7,'CubeOutput','Cube输出','把数据写入一个cube'),(8,'TypeExitEdi2XmlStep','Edi to XML','Converts Edi text to generic XML'),(9,'ElasticSearchBulk','ElasticSearch 批量加载','Performs bulk inserts into ElasticSearch'),(10,'ShapeFileReader','ESRI Shapefile Reader','Reads shape file data from an ESRI shape file and linked DBF file'),(11,'MetaInject','ETL元数据注入','This step allows you to inject metadata into an existing transformation prior to execution.  This allows for the creation of dynamic and highly flexible data integration solutions.'),(12,'DummyPlugin','Example plugin','This is an example for a plugin test step'),(13,'ExcelInput','Excel输入','从一个微软的Excel文件里读取数据. 兼容Excel 95, 97 and 2000.'),(14,'ExcelOutput','Excel输出','Stores records into an Excel (XLS) document with formatting information.'),(15,'GetSlaveSequence','Get ID from slave server','Retrieves unique IDs in blocks from a slave server.  The referenced sequence needs to be configured on the slave server in the XML configuration file.'),(16,'TypeExitGoogleAnalyticsInputStep','Google Analytics 输入','Fetches data from google analytics account'),(17,'GPLoad','Greenplum Load','Greenplum Load'),(18,'GPBulkLoader','Greenplum 批量加载','Greenplum Bulk Loader'),(19,'ParallelGzipCsvInput','GZIP CSV Input','Parallel GZIP CSV file input reader'),(20,'HL7Input','HL7 Input','Reads and parses HL7 messages and outputs a series of values from the messages'),(21,'HTTP','HTTP client','Call a web service over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(22,'HTTPPOST','HTTP Post','Call a web service request over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(23,'InfobrightOutput','Infobright 批量加载','Load data to an Infobright database table'),(24,'VectorWiseBulkLoader','Ingres VectorWise 批量加载','This step interfaces with the Ingres VectorWise Bulk Loader \"COPY TABLE\" command.'),(25,'UserDefinedJavaClass','Java 代码','This step allows you to program a step using Java code'),(26,'ScriptValueMod','JavaScript代码','This is a modified plugin for the Scripting Values with improved interface and performance.\nWritten & donated to open source by Martin Lange, Proconis : http://www.proconis.de'),(27,'JsonInput','Json 输入','Extract relevant portions out of JSON structures (file or incoming field) and output rows'),(28,'JsonOutput','Json 输出','Create Json bloc and output it in a field ou a file.'),(29,'LDAPInput','LDAP 输入','Read data from LDAP host'),(30,'LDAPOutput','LDAP 输出','Perform Insert, upsert, update, add or delete operations on records based on their DN (Distinguished  Name).'),(31,'LDIFInput','LDIF 输入','Read data from LDIF files'),(32,'LucidDBBulkLoader','LucidDB 批量加载','Load data into LucidDB by using their bulk load command in streaming mode. (Doesnt work on Windows!)'),(33,'LucidDBStreamingLoader','LucidDB 流加载','Load data into LucidDB by using Remote Rows UDX.'),(34,'TypeExitExcelWriterStep','Microsoft Excel 输出','Writes or appends data to an Excel file'),(35,'MondrianInput','Mondrian 输入','Execute and retrieve data using an MDX query against a Pentaho Analyses OLAP server (Mondrian)'),(36,'MonetDBBulkLoader','MonetDB 批量加载','Load data into MonetDB by using their bulk load command in streaming mode.'),(37,'MultiwayMergeJoin','Multiway Merge Join','Multiway Merge Join'),(38,'MySQLBulkLoader','MySQL 批量加载','MySQL bulk loader step, loading data over a named pipe (not available on MS Windows)'),(39,'OlapInput','OLAP 输入','Execute and retrieve data using an MDX query against any XML/A OLAP datasource using olap4j'),(40,'OpenERPObjectDelete','OpenERP Object Delete','Deletes OpenERP objects'),(41,'OpenERPObjectInput','OpenERP Object Input','Reads data from OpenERP objects'),(42,'OpenERPObjectOutputImport','OpenERP Object Output','Writes data into OpenERP objects using the object import procedure'),(43,'OraBulkLoader','Oracle 批量加载','Use Oracle Bulk Loader to load data'),(44,'PaloCellInput','Palo Cell Input','Reads data from a defined Palo Cube '),(45,'PaloCellOutput','Palo Cell Output','Writes data to a defined Palo Cube'),(46,'PaloDimInput','Palo Dim Input','Reads data from a defined Palo Dimension'),(47,'PaloDimOutput','Palo Dim Output','Writes data to defined Palo Dimension'),(48,'PentahoReportingOutput','Pentaho 报表输出','Executes an existing report (PRPT)'),(49,'PGPDecryptStream','PGP Decrypt stream','Decrypt data stream with PGP'),(50,'PGPEncryptStream','PGP Encrypt stream','Encrypt data stream with PGP'),(51,'PGBulkLoader','PostgreSQL 批量加载','PostgreSQL Bulk Loader'),(52,'Rest','REST Client','Consume RESTfull services.\nREpresentational State Transfer (REST) is a key design idiom that embraces a stateless client-server\narchitecture in which the web services are viewed as resources and can be identified by their URLs'),(53,'RssInput','RSS 输入','Read RSS feeds'),(54,'RssOutput','RSS 输出','Read RSS stream.'),(55,'RuleAccumulator','Rule Accumulator','Execute a rule against a set of all incoming rows'),(56,'RuleExecutor','Rule Executor','Execute a rule against each row passing through'),(57,'S3CSVINPUT','S3 CSV 输入','S3 CSV 输入'),(58,'SalesforceUpsert','Salesforce Upsert','Insert or update records in Salesforce module.'),(59,'SalesforceDelete','Salesforce 删除','Delete records in Salesforce module.'),(60,'SalesforceInsert','Salesforce 插入','Insert records in Salesforce module.'),(61,'SalesforceUpdate','Salesforce 更新','Update records in Salesforce module.'),(62,'SalesforceInput','Salesforce 输入','!BaseStep.TypeTooltipDesc.SalesforceInput!'),(63,'SapInput','SAP 输入','Read data from SAP ERP, optionally with parameters'),(64,'SASInput','SAS 输入','This step reads files in sas7bdat (SAS) native format'),(65,'SFTPPut','SFTP Put','Upload a file or a stream file to remote host via SFTP'),(66,'SingleThreader','Single Threader','Executes a transformation snippet in a single thread.  You need a standard mapping or a transformation with an Injector step where data from the parent transformation will arive in blocks.'),(67,'SocketWriter','Socket 写','Socket writer.  A socket server that can send rows of data to a socket reader.'),(68,'SocketReader','Socket 读','Socket reader.  A socket client that connects to a server (Socket Writer step).'),(69,'SQLFileOutput','SQL 文件输出','Output SQL INSERT statements to file'),(70,'SwitchCase','Switch Case','Switch a row to a certain target step based on the case value in a field.'),(71,'TeraFast','Teradata Fastload 批量加载','The Teradata Fastload Bulk loader'),(72,'WebServiceLookup','Web 服务查询','使用 Web 服务查询信息'),(73,'WMIInput','WMI 输入','Query WMI repository (Management Instrumentation) for class and instance information.\nThis step runs the WMI Query Language (WQL) which is a subset of ANSI SQL.>>>>>>> .r15867'),(74,'XBaseInput','XBase输入','从一个XBase类型的文件(DBF)读取记录'),(75,'getXMLData','XML 文件输入','Get data from XML file by using XPath.\n This step also allows you to parse XML defined in a previous field.'),(76,'XMLInputStream','XML 文件输入 (StAX解析)','This step is capable of processing very large and complex XML files very fast.'),(77,'XMLInputSax','XML 流输入','Read data from an XML file in a streaming fashing, working faster and consuming less memory'),(78,'XMLJoin','XML 连接','Joins a stream of XML-Tags into a target XML string'),(79,'XMLInput','XML输入','从一个XML读取数据'),(80,'XMLOutput','XML输出','写数据到一个XML文件'),(81,'XSLT','XSL 转换','Transform XML stream using XSL (eXtensible Stylesheet Language).'),(82,'YamlInput','Yaml 输入','Read YAML source (file or stream) parse them and convert them to rows and writes these to one or more output. '),(83,'ZipFile','Zip 文件','Zip a file.\nFilename will be extracted from incoming stream.'),(84,'Abort','中止','Abort a transformation'),(85,'FilesFromResult','从结果获取文件','This step allows you to read filenames used or generated in a previous entry in a job.'),(86,'RowsFromResult','从结果获取记录','这个允许你从同一个任务的前一个条目里读取记录.'),(87,'XSDValidator','使用 XSD 检验 XML 文件','Validate XML source (files or streams) against XML Schema Definition.'),(88,'ValueMapper','值映射','Maps values of a certain field from one value to another'),(89,'CloneRow','克隆行','Clone a row as many times as needed'),(90,'Formula','公式','使用 Pentaho 的公式库来计算公式'),(91,'WriteToLog','写日志','Write data to log'),(92,'AnalyticQuery','分析查询','Execute analytic queries over a sorted dataset (LEAD/LAG/FIRST/LAST)'),(93,'GroupBy','分组','以分组的形式创建聚合.{0}这个仅仅在一个已经排好序的输入有效.{1}如果输入没有排序, 仅仅两个连续的记录行被正确处理.'),(94,'SplitFieldToRows3','列拆分为多行','Splits a single string field by delimiter and creates a new row for each split term'),(95,'Denormaliser','列转行','Denormalises rows by looking up key-value pairs and by assigning them to new fields in the输出 rows.{0}This method aggregates and needs the输入 rows to be sorted on the grouping fields'),(96,'Delete','删除','基于关键字删除记录'),(97,'Janino','利用Janino计算Java表达式','Calculate the result of a Java Expression using Janino'),(98,'StringCut','剪切字符串','Strings cut (substring).'),(99,'UnivariateStats','单变量统计','This step computes some simple stats based on a single input field'),(100,'Unique','去除重复记录','去除重复的记录行，保持记录唯一{0}这个仅仅基于一个已经排好序的输入.{1}如果输入没有排序, 仅仅两个连续的记录行被正确处理.'),(101,'SyslogMessage','发送信息至Syslog','Send message to Syslog server'),(102,'Mail','发送邮件','Send eMail.'),(103,'MergeRows','合并记录','合并两个数据流, 并根据某个关键字排序.  这两个数据流被比较，以标识相等的、变更的、删除的和新建的记录.'),(104,'ExecProcess','启动一个进程','Execute a process and return the result'),(105,'UniqueRowsByHashSet','唯一行 (哈希值)','Remove double rows and leave only unique occurrences by using a HashSet.'),(106,'FixedInput','固定宽度文件输入','Fixed file input'),(107,'MemoryGroupBy','在内存中分组','Builds aggregates in a group by fashion.\nThis step doesn\'t require sorted input.'),(108,'AddXML','增加XML列','Encode several fields into an XML fragment'),(109,'Constant','增加常量','给记录增加一到多个常量'),(110,'Sequence','增加序列','从序列获取下一个值'),(111,'CheckSum','增加校验列','Add a checksum column for each input row'),(112,'ProcessFiles','处理文件','Process one file per row (copy or move or delete).\nThis step only accept filename in input.'),(113,'FilesToResult','复制文件到结果','This step allows you to set filenames in the result of this transformation.\nSubsequent job entries can then use this information.'),(114,'RowsToResult','复制记录到结果','使用这个步骤把记录写到正在执行的任务.{0}信息将会被传递给同一个任务里的下一个条目.'),(115,'SelectValues','字段选择','选择或移除记录里的字。{0}此外，可以设置字段的元数据: 类型, 长度和精度.'),(116,'StringOperations','字符串操作','Apply certain operations like trimming, padding and others to string value.'),(117,'ReplaceString','字符串替换','Replace all occurences a word in a string with another word.'),(118,'SymmetricCryptoTrans','对称加密','Encrypt or decrypt a string using symmetric encryption.\nAvailable algorithms are DES, AEC, TripleDES.'),(119,'SetValueConstant','将字段值设置为常量','Set value of a field to a constant'),(120,'Delay','延迟行','Output each input row after a delay'),(121,'DynamicSQLRow','执行Dynamic SQL','Execute dynamic SQL statement build in a previous field'),(122,'ExecSQL','执行SQL脚本','执行一个SQL脚本, 另外，可以使用输入的记录作为参数'),(123,'ExecSQLRow','执行SQL脚本(字段流替换)','Execute SQL script extracted from a field\ncreated in a previous step.'),(124,'JobExecutor','执行作业','This step executes a Pentaho Data Integration job, sets parameters and passes rows.'),(125,'FieldSplitter','拆分字段','当你想把一个字段拆分成多个时，使用这个类型.'),(126,'SortedMerge','排序合并','Sorted Merge'),(127,'SortRows','排序记录','基于字段值把记录排序(升序或降序)'),(128,'InsertUpdate','插入更新','基于关键字更新或插入记录到数据库.'),(129,'ChangeFileEncoding','改变文件编码','Change file encoding and create a new file'),(130,'NumberRange','数值范围','Create ranges based on numeric field'),(131,'SynchronizeAfterMerge','数据同步','This step perform insert/update/delete in one go based on the value of a field. '),(132,'DBLookup','数据库查询','使用字段值在数据库里查询值'),(133,'DBJoin','数据库连接','使用数据流里的值作为参数执行一个数据库查询'),(134,'Validator','数据检验','Validates passing data based on a set of rules'),(135,'PrioritizeStreams','数据流优先级排序','Prioritize streams in an order way.'),(136,'ReservoirSampling','数据采样','[Transform] Samples a fixed number of rows from the incoming stream'),(137,'LoadFileInput','文件内容加载至内存','Load file content in memory'),(138,'TextFileInput','文本文件输入','从一个文本文件（几种格式）里读取数据{0}这些数据可以被传递到下一个步骤里...'),(139,'TextFileOutput','文本文件输出','写记录到一个文本文件.'),(140,'Mapping','映射 (子转换)','运行一个映射 (子转换), 使用MappingInput和MappingOutput来指定接口的字段'),(141,'MappingInput','映射输入规范','指定一个映射的字段输入'),(142,'MappingOutput','映射输出规范','指定一个映射的字段输出'),(143,'Update','更新','基于关键字更新记录到数据库'),(144,'IfNull','替换NULL值','Sets a field value to a constant if it is null.'),(145,'SampleRows','样本行','Filter rows based on the line number.'),(146,'JavaFilter','根据Java代码过滤记录','Filter rows using java code'),(147,'FieldsChangeSequence','根据字段值来改变序列','Add sequence depending of fields value change.\nEach time value of at least one field change, PDI will reset sequence. '),(148,'WebServiceAvailable','检查web服务是否可用','Check if a webservice is available'),(149,'FileExists','检查文件是否存在','Check if a file exists'),(150,'FileLocked','检查文件是否已被锁定','Check if a file is locked by another process'),(151,'TableExists','检查表是否存在','Check if a table exists on a specified connection'),(152,'ColumnExists','检查表里的列是否存在','Check if a column exists in a table on a specified connection.'),(153,'DetectEmptyStream','检测空流','This step will output one empty row if input stream is empty\n(ie when input stream does not contain any row)'),(154,'CreditCardValidator','检验信用卡号码是否有效','The Credit card validator step will help you tell:\n(1) if a credit card number is valid (uses LUHN10 (MOD-10) algorithm)\n(2) which credit card vendor handles that number\n(VISA, MasterCard, Diners Club, EnRoute, American Express (AMEX),...)'),(155,'MailValidator','检验邮件地址','Check if an email address is valid.'),(156,'FuzzyMatch','模糊匹配','Finding approximate matches to a string using matching algorithms.\nRead a field from a main stream and output approximative value from lookup stream.'),(157,'RegexEval','正则表达式','Regular expression Evaluation\nThis step uses a regular expression to evaluate a field. It can also extract new fields out of an existing field with capturing groups.'),(158,'TableCompare','比较表','Compares 2 tables and gives back a list of differences'),(159,'StreamLookup','流查询','从转换中的其它流里查询值.'),(160,'StepMetastructure','流的元数据','This is a step to read the metadata of the incoming stream.'),(161,'SecretKeyGenerator','生成密钥','Generate secrete key for algorithms such as DES, AEC, TripleDES.'),(162,'RowGenerator','生成记录','产生一些空记录或相等的行.'),(163,'RandomValue','生成随机数','Generate random value'),(164,'RandomCCNumberGenerator','生成随机的信用卡号','Generate random valide (luhn check) credit card numbers'),(165,'Dummy','空操作 (什么也不做)','这个步骤类型什么都不作.{0} 当你想测试或拆分数据流的时候有用.'),(166,'DimensionLookup','维度查询/更新','在一个数据仓库里更新一个渐变维 {0} 或者在这个维里查询信息.'),(167,'CombinationLookup','联合查询/更新','更新数据仓库里的一个junk维 {0} 可选的, 科研查询维里的信息.{1}junk维的主键是所有的字段.'),(168,'AggregateRows','聚合记录','这个步骤类型允许你聚合记录.{0}它不能使用在分组的情况.'),(169,'AutoDoc','自动文档输出','This step automatically generates documentation based on input in the form of a list of transformations and jobs'),(170,'DataGrid','自定义常量数据','Enter rows of static data in a grid, usually for testing, reference or demo purpose'),(171,'GetPreviousRowField','获取上一次的记录','Get fields value of previous row.'),(172,'GetVariable','获取变量','Determine the values of certain (environment or Kettle) variables and put them in field values.'),(173,'GetSubFolders','获取子目录名','Read a parent folder and return all subfolders'),(174,'GetFileNames','获取文件名','Get file names from the operating system and send them to the next step.'),(175,'GetFilesRowsCount','获取文件行数','Returns rows count for text files.'),(176,'SystemInfo','获取系统信息','获取系统信息，例如时间、日期.'),(177,'GetTableNames','获取表名','Get table names from database connection and send them to the next step'),(178,'GetRepositoryNames','获取资源库配置','Lists detailed information about transformations and/or jobs in a repository'),(179,'Flattener','行扁平化','Flattens consequetive rows based on the order in which they appear in the输入 stream'),(180,'Normaliser','行转列','De-normalised information can be normalised using this step type.'),(181,'TableInput','表输入','从数据库表里读取信息.'),(182,'TableOutput','表输出','写信息到一个数据库表'),(183,'Calculator','计算器','通过执行简单的计算创建一个新字段'),(184,'JoinRows','记录关联 (笛卡尔输出)','这个步骤的输出是输入流的笛卡尔的结果.{0} 输出结果的记录数是输入流记录之间的乘积.'),(185,'Injector','记录注射','Injector step to allow to inject rows into the transformation through the java API'),(186,'MergeJoin','记录集连接','Joins two streams on a given key and outputs a joined set. The input streams must be sorted on the join key'),(187,'NullIf','设置值为NULL','如果一个字段值等于某个固定值，那么把这个字段值设置成null'),(188,'SetVariable','设置变量','Set environment variables based on a single input row.'),(189,'SetValueField','设置字段值','Set value of a field with another value field'),(190,'DetectLastRow','识别流的最后一行','Last row will be marked'),(191,'DBProc','调用DB存储过程','通过调用数据库存储过程获得返回值.'),(192,'StepsMetrics','转换步骤信息统计','Return metrics for one or several steps'),(193,'FilterRows','过滤记录','使用简单的相等来过滤记录'),(194,'SSH','运行SSH命令','Run SSH commands and returns result.'),(195,'Append','追加流','Append 2 streams in an ordered way'),(196,'MailInput','邮件信息输入','Read POP3/IMAP server and retrieve messages'),(197,'PropertyInput','配置文件输入','Read data (key, value) from properties files.'),(198,'PropertyOutput','配置文件输出','Write data to properties file'),(199,'BlockingStep','阻塞数据','This step blocks until all incoming rows have been processed.  Subsequent steps only recieve the last input row to this step.'),(200,'BlockUntilStepsFinish','阻塞数据直到步骤都完成','Block this step until selected steps finish.'),(201,'ReportFileOutput','报表文件输出','通过报表设计器设计一个报表样式和设定参数.');
/*!40000 ALTER TABLE `r_step_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_attribute`
--

DROP TABLE IF EXISTS `r_trans_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_attribute` (
  `ID_TRANS_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_TRANS_ATTRIBUTE`),
  UNIQUE KEY `IDX_TRANS_ATTRIBUTE_LOOKUP` (`ID_TRANSFORMATION`,`CODE`,`NR`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_attribute`
--

LOCK TABLES `r_trans_attribute` WRITE;
/*!40000 ALTER TABLE `r_trans_attribute` DISABLE KEYS */;
INSERT INTO `r_trans_attribute` VALUES (1270,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(1249,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1250,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(1251,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(1252,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1253,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(1254,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(1255,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1256,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(1257,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(1258,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1259,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(1260,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(1261,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1262,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(1263,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(1264,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1265,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1266,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1267,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1268,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(1269,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(1248,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(1246,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1247,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(1245,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(1244,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(1231,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1232,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(1233,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(1234,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1235,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(1236,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(1237,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1238,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(1239,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(1240,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1241,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(1242,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(1243,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1207,1,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(1208,1,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(1209,1,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1210,1,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(1211,1,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(1212,1,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1213,1,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(1214,1,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(1215,1,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1216,1,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(1217,1,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(1218,1,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1219,1,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1220,1,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1221,1,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1222,1,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(1223,1,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(1224,1,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(1225,1,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(1226,1,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(1227,1,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(1228,1,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1229,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1230,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1206,1,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1203,1,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1204,1,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(1205,1,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(1193,1,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(1194,1,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1195,1,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(1196,1,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(1197,1,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1198,1,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(1199,1,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(1200,1,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1201,1,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(1202,1,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(1171,1,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(1172,1,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(1173,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(1174,1,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(1175,1,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(1176,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(1177,1,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(1178,1,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(1179,1,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(1180,1,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(1181,1,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(1182,1,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1183,1,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1184,1,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1185,1,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1186,1,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1187,1,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1188,1,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1189,1,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(1190,1,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(1191,1,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1192,1,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(1169,1,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(1170,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'Y'),(1167,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(1168,1,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(1144,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1145,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(1146,1,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(1147,1,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(1148,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1149,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(1150,1,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(1151,1,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(1152,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1153,1,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(1154,1,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(1155,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1156,1,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(1157,1,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(1158,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1159,1,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(1160,1,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(1161,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(1162,1,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(1163,1,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(1164,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(1165,1,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(1166,1,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(1116,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1117,1,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1118,1,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1119,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1120,1,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(1121,1,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(1122,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1123,1,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(1124,1,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(1125,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1126,1,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(1127,1,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(1128,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1129,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(1130,1,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(1131,1,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(1132,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1133,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(1134,1,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(1135,1,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(1136,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1137,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(1138,1,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(1139,1,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(1140,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1141,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(1142,1,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(1143,1,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(1105,1,0,'LOG_SIZE_LIMIT',0,NULL),(1106,1,0,'LOG_INTERVAL',0,NULL),(1107,1,0,'TRANSFORMATION_TYPE',0,'Normal'),(1108,1,0,'SLEEP_TIME_EMPTY',50,NULL),(1109,1,0,'SLEEP_TIME_FULL',50,NULL),(1110,1,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(1111,1,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(1112,1,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(1113,1,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1114,1,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1115,1,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1096,1,0,'UNIQUE_CONNECTIONS',0,'N'),(1097,1,0,'FEEDBACK_SHOWN',0,'Y'),(1098,1,0,'FEEDBACK_SIZE',50000,NULL),(1099,1,0,'USING_THREAD_PRIORITIES',0,'Y'),(1100,1,0,'SHARED_FILE',0,NULL),(1101,1,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(1102,1,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(1103,1,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,'100'),(1104,1,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(387,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(386,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(385,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(384,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(383,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(382,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(381,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(380,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(379,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(378,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(377,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(376,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(375,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(374,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(373,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(372,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(371,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(370,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(369,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(368,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(367,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(366,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(365,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(364,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(363,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(362,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(361,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(360,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(359,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(358,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(357,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(356,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(355,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(354,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(353,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(352,2,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(351,2,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(350,2,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(349,2,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(348,2,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(347,2,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(346,2,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(345,2,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(344,2,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(343,2,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(342,2,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(341,2,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(340,2,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(339,2,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(338,2,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(337,2,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(336,2,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(335,2,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(334,2,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(333,2,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(332,2,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(331,2,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(330,2,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(329,2,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(328,2,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(327,2,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(326,2,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(325,2,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(324,2,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(323,2,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(322,2,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(321,2,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(320,2,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(319,2,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(318,2,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(317,2,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(316,2,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(315,2,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(314,2,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(313,2,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(312,2,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(311,2,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(310,2,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(309,2,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(308,2,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(307,2,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(306,2,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(305,2,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(304,2,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(303,2,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(302,2,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(301,2,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(300,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(299,2,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(298,2,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(297,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(296,2,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(295,2,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(294,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'N'),(293,2,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(292,2,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(291,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(290,2,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(289,2,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(288,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(287,2,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(286,2,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(285,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(284,2,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(283,2,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(282,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(281,2,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(280,2,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(279,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(278,2,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(277,2,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(276,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(275,2,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(274,2,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(273,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(272,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'N'),(271,2,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(270,2,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(269,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(268,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(267,2,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(266,2,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(265,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(264,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(263,2,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(262,2,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(261,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(260,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(259,2,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(258,2,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(257,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(256,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(255,2,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(254,2,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(253,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(252,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(251,2,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(250,2,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(249,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(248,2,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(247,2,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(246,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(245,2,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(244,2,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(243,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'N'),(242,2,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(241,2,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(240,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(239,2,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(238,2,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(237,2,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(236,2,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(235,2,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(234,2,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(233,2,0,'SLEEP_TIME_FULL',50,NULL),(232,2,0,'SLEEP_TIME_EMPTY',50,NULL),(231,2,0,'TRANSFORMATION_TYPE',0,'Normal'),(230,2,0,'LOG_INTERVAL',0,NULL),(229,2,0,'LOG_SIZE_LIMIT',0,NULL),(228,2,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(227,2,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,NULL),(226,2,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(225,2,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(224,2,0,'SHARED_FILE',0,NULL),(223,2,0,'USING_THREAD_PRIORITIES',0,'Y'),(222,2,0,'FEEDBACK_SIZE',50000,NULL),(221,2,0,'FEEDBACK_SHOWN',0,'N'),(220,2,0,'UNIQUE_CONNECTIONS',0,'N'),(388,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(389,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(390,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(391,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(392,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(393,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(394,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(395,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(396,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(397,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(398,2,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(399,2,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(400,2,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(401,2,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(402,2,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(403,2,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(404,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(405,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(406,2,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(407,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(408,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(409,2,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(410,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(411,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(412,2,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(413,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(414,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(415,2,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(416,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(417,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(418,2,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(419,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(420,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(421,2,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(422,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(423,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(424,2,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(425,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(426,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(427,2,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(428,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(429,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(430,2,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(431,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(432,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(433,2,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(434,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(435,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(436,2,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(437,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(438,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(606,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(605,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(604,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(603,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(602,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(601,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(600,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(599,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(598,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(597,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(596,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(595,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(594,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(593,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(592,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(591,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(590,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(589,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(588,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(587,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(586,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(585,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(584,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(583,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(582,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(581,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(580,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(579,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(578,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(577,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(576,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(575,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(574,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(573,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(572,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(571,3,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(570,3,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(569,3,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(568,3,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(567,3,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(566,3,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(565,3,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(564,3,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(563,3,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(562,3,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(561,3,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(560,3,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(559,3,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(558,3,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(557,3,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(556,3,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(555,3,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(554,3,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(553,3,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(552,3,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(551,3,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(550,3,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(549,3,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(548,3,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(547,3,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(546,3,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(545,3,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(544,3,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(543,3,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(542,3,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(541,3,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(540,3,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(539,3,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(538,3,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(537,3,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(536,3,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(535,3,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(534,3,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(533,3,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(532,3,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(531,3,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(530,3,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(529,3,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(528,3,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(527,3,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(526,3,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(525,3,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(524,3,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(523,3,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(522,3,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(521,3,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(520,3,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(519,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(518,3,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(517,3,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(516,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(515,3,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(514,3,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(513,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'N'),(512,3,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(511,3,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(510,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(509,3,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(508,3,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(507,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(506,3,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(505,3,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(504,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(503,3,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(502,3,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(501,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(500,3,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(499,3,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(498,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(497,3,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(496,3,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(495,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(494,3,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(493,3,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(492,3,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(491,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'N'),(490,3,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(489,3,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(488,3,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(487,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(486,3,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(485,3,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(484,3,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(483,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(482,3,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(481,3,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(480,3,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(479,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(478,3,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(477,3,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(476,3,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(475,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(474,3,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(473,3,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(472,3,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(471,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(470,3,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(469,3,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(468,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(467,3,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(466,3,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(465,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(464,3,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(463,3,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(462,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'N'),(461,3,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(460,3,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(459,3,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(458,3,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(457,3,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(456,3,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(455,3,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(454,3,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(453,3,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(452,3,0,'SLEEP_TIME_FULL',50,NULL),(451,3,0,'SLEEP_TIME_EMPTY',50,NULL),(450,3,0,'TRANSFORMATION_TYPE',0,'Normal'),(449,3,0,'LOG_INTERVAL',0,NULL),(448,3,0,'LOG_SIZE_LIMIT',0,NULL),(447,3,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(446,3,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,NULL),(445,3,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(444,3,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(443,3,0,'SHARED_FILE',0,NULL),(442,3,0,'USING_THREAD_PRIORITIES',0,'Y'),(441,3,0,'FEEDBACK_SIZE',50000,NULL),(440,3,0,'FEEDBACK_SHOWN',0,'Y'),(439,3,0,'UNIQUE_CONNECTIONS',0,'N'),(607,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(608,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(609,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(610,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(611,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(612,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(613,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(614,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(615,3,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(616,3,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(617,3,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(618,3,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(619,3,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(620,3,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(621,3,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(622,3,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(623,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(624,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(625,3,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(626,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(627,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(628,3,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(629,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(630,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(631,3,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(632,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(633,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(634,3,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(635,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(636,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(637,3,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(638,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(639,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(640,3,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(641,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(642,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(643,3,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(644,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(645,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(646,3,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(647,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(648,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(649,3,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(650,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(651,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(652,3,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(653,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(654,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(655,3,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(656,3,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(657,3,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(658,4,0,'UNIQUE_CONNECTIONS',0,'N'),(659,4,0,'FEEDBACK_SHOWN',0,'Y'),(660,4,0,'FEEDBACK_SIZE',5000,NULL),(661,4,0,'USING_THREAD_PRIORITIES',0,'Y'),(662,4,0,'SHARED_FILE',0,NULL),(663,4,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(664,4,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(665,4,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,NULL),(666,4,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(667,4,0,'LOG_SIZE_LIMIT',0,NULL),(668,4,0,'LOG_INTERVAL',0,NULL),(669,4,0,'TRANSFORMATION_TYPE',0,'Normal'),(670,4,0,'SLEEP_TIME_EMPTY',1,NULL),(671,4,0,'SLEEP_TIME_FULL',1,NULL),(672,4,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(673,4,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(674,4,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(675,4,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(676,4,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(677,4,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(678,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(679,4,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(680,4,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(681,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'N'),(682,4,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(683,4,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(684,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(685,4,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(686,4,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(687,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(688,4,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(689,4,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(690,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(691,4,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(692,4,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(693,4,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(694,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(695,4,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(696,4,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(697,4,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(698,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(699,4,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(700,4,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(701,4,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(702,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(703,4,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(704,4,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(705,4,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(706,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(707,4,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(708,4,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(709,4,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(710,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'N'),(711,4,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(712,4,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(713,4,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(714,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(715,4,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(716,4,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(717,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(718,4,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(719,4,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(720,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(721,4,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(722,4,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(723,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(724,4,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(725,4,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(726,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(727,4,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(728,4,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(729,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(730,4,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(731,4,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(732,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'N'),(733,4,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(734,4,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(735,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(736,4,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(737,4,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(738,4,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(739,4,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(740,4,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(741,4,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(742,4,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(743,4,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(744,4,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(745,4,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(746,4,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(747,4,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(748,4,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(749,4,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(750,4,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(751,4,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(752,4,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(753,4,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(754,4,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(755,4,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(756,4,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(757,4,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(758,4,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(759,4,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(760,4,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(761,4,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(762,4,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(763,4,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(764,4,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(765,4,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(766,4,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(767,4,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(768,4,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(769,4,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(770,4,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(771,4,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(772,4,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(773,4,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(774,4,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(775,4,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(776,4,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(777,4,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(778,4,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(779,4,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(780,4,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(781,4,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(782,4,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(783,4,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(784,4,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(785,4,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(786,4,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(787,4,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(788,4,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(789,4,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(790,4,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(791,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(792,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(793,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(794,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(795,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(796,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(797,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(798,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(799,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(800,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(801,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(802,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(803,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(804,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(805,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(806,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(807,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(808,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(809,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(810,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(811,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(812,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(813,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(814,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(815,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(816,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(817,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(818,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(819,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(820,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(821,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(822,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(823,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(824,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(825,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(826,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(827,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(828,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(829,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(830,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(831,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(832,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(833,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(834,4,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(835,4,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(836,4,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(837,4,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(838,4,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(839,4,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(840,4,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(841,4,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(842,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(843,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(844,4,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(845,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(846,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(847,4,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(848,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(849,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(850,4,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(851,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(852,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(853,4,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(854,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(855,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(856,4,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(857,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(858,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(859,4,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(860,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(861,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(862,4,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(863,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(864,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(865,4,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(866,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(867,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(868,4,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(869,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(870,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(871,4,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(872,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(873,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(874,4,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(875,4,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(876,4,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(877,5,0,'UNIQUE_CONNECTIONS',0,'N'),(878,5,0,'FEEDBACK_SHOWN',0,'Y'),(879,5,0,'FEEDBACK_SIZE',50000,NULL),(880,5,0,'USING_THREAD_PRIORITIES',0,'Y'),(881,5,0,'SHARED_FILE',0,NULL),(882,5,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(883,5,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(884,5,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,'100'),(885,5,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(886,5,0,'LOG_SIZE_LIMIT',0,NULL),(887,5,0,'LOG_INTERVAL',0,NULL),(888,5,0,'TRANSFORMATION_TYPE',0,'Normal'),(889,5,0,'SLEEP_TIME_EMPTY',50,NULL),(890,5,0,'SLEEP_TIME_FULL',50,NULL),(891,5,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(892,5,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(893,5,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(894,5,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(895,5,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(896,5,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(897,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(898,5,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(899,5,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(900,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(901,5,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(902,5,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(903,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(904,5,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(905,5,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(906,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(907,5,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(908,5,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(909,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(910,5,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(911,5,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(912,5,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(913,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(914,5,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(915,5,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(916,5,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(917,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(918,5,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(919,5,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(920,5,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(921,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(922,5,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(923,5,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(924,5,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(925,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(926,5,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(927,5,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(928,5,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(929,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(930,5,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(931,5,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(932,5,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(933,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(934,5,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(935,5,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(936,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(937,5,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(938,5,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(939,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(940,5,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(941,5,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(942,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(943,5,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(944,5,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(945,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(946,5,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(947,5,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(948,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(949,5,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(950,5,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(951,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'Y'),(952,5,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(953,5,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(954,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(955,5,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(956,5,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(957,5,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(958,5,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(959,5,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(960,5,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(961,5,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(962,5,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(963,5,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(964,5,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(965,5,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(966,5,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(967,5,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(968,5,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(969,5,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(970,5,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(971,5,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(972,5,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(973,5,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(974,5,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(975,5,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(976,5,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(977,5,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(978,5,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(979,5,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(980,5,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(981,5,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(982,5,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(983,5,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(984,5,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(985,5,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(986,5,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(987,5,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(988,5,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(989,5,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(990,5,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(991,5,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(992,5,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(993,5,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(994,5,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(995,5,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(996,5,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(997,5,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(998,5,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(999,5,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1000,5,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1001,5,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1002,5,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1003,5,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(1004,5,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(1005,5,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(1006,5,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(1007,5,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(1008,5,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(1009,5,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1010,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1011,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1012,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1013,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(1014,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(1015,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1016,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(1017,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(1018,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1019,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(1020,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(1021,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1022,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(1023,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(1024,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1025,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(1026,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(1027,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1028,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(1029,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(1030,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1031,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(1032,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(1033,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1034,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(1035,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(1036,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1037,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(1038,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(1039,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1040,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(1041,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(1042,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1043,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(1044,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(1045,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1046,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1047,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1048,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1049,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(1050,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(1051,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(1052,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(1053,5,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(1054,5,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(1055,5,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(1056,5,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(1057,5,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(1058,5,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(1059,5,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1060,5,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1061,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1062,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1063,5,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1064,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1065,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1066,5,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(1067,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(1068,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1069,5,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(1070,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(1071,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1072,5,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(1073,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(1074,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1075,5,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(1076,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(1077,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1078,5,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(1079,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(1080,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1081,5,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(1082,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(1083,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1084,5,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(1085,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(1086,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1087,5,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(1088,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(1089,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1090,5,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(1091,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(1092,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1093,5,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(1094,5,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(1095,5,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1271,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(1272,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(1273,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(1274,1,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(1275,1,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(1276,1,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(1277,1,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(1278,1,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1279,1,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1280,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1281,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1282,1,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1283,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1284,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1285,1,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(1286,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(1287,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1288,1,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(1289,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(1290,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1291,1,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(1292,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(1293,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1294,1,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(1295,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(1296,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1297,1,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(1298,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(1299,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1300,1,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(1301,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(1302,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1303,1,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(1304,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(1305,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1306,1,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(1307,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(1308,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1309,1,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(1310,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(1311,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1312,1,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(1313,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(1314,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y');
/*!40000 ALTER TABLE `r_trans_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_cluster`
--

DROP TABLE IF EXISTS `r_trans_cluster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_cluster` (
  `ID_TRANS_CLUSTER` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_CLUSTER` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_CLUSTER`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_cluster`
--

LOCK TABLES `r_trans_cluster` WRITE;
/*!40000 ALTER TABLE `r_trans_cluster` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_trans_cluster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_hop`
--

DROP TABLE IF EXISTS `r_trans_hop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_hop` (
  `ID_TRANS_HOP` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP_FROM` int(11) DEFAULT NULL,
  `ID_STEP_TO` int(11) DEFAULT NULL,
  `ENABLED` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_HOP`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_hop`
--

LOCK TABLES `r_trans_hop` WRITE;
/*!40000 ALTER TABLE `r_trans_hop` DISABLE KEYS */;
INSERT INTO `r_trans_hop` VALUES (3,3,6,5,'Y'),(2,2,4,3,'Y'),(13,1,18,19,'Y'),(4,4,11,9,'Y'),(5,4,9,12,'Y'),(6,4,10,12,'Y'),(7,4,11,10,'Y'),(8,4,14,7,'Y'),(9,4,15,8,'Y'),(10,4,7,13,'Y'),(11,4,8,13,'Y'),(12,5,16,17,'Y');
/*!40000 ALTER TABLE `r_trans_hop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_lock`
--

DROP TABLE IF EXISTS `r_trans_lock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_lock` (
  `ID_TRANS_LOCK` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_USER` int(11) DEFAULT NULL,
  `LOCK_MESSAGE` mediumtext,
  `LOCK_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_LOCK`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_lock`
--

LOCK TABLES `r_trans_lock` WRITE;
/*!40000 ALTER TABLE `r_trans_lock` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_trans_lock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_note`
--

DROP TABLE IF EXISTS `r_trans_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_note` (
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_NOTE` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_note`
--

LOCK TABLES `r_trans_note` WRITE;
/*!40000 ALTER TABLE `r_trans_note` DISABLE KEYS */;
INSERT INTO `r_trans_note` VALUES (4,1),(4,2);
/*!40000 ALTER TABLE `r_trans_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_partition_schema`
--

DROP TABLE IF EXISTS `r_trans_partition_schema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_partition_schema` (
  `ID_TRANS_PARTITION_SCHEMA` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_PARTITION_SCHEMA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_PARTITION_SCHEMA`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_partition_schema`
--

LOCK TABLES `r_trans_partition_schema` WRITE;
/*!40000 ALTER TABLE `r_trans_partition_schema` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_trans_partition_schema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_slave`
--

DROP TABLE IF EXISTS `r_trans_slave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_slave` (
  `ID_TRANS_SLAVE` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_SLAVE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_SLAVE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_slave`
--

LOCK TABLES `r_trans_slave` WRITE;
/*!40000 ALTER TABLE `r_trans_slave` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_trans_slave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_trans_step_condition`
--

DROP TABLE IF EXISTS `r_trans_step_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_trans_step_condition` (
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP` int(11) DEFAULT NULL,
  `ID_CONDITION` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_trans_step_condition`
--

LOCK TABLES `r_trans_step_condition` WRITE;
/*!40000 ALTER TABLE `r_trans_step_condition` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_trans_step_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_transformation`
--

DROP TABLE IF EXISTS `r_transformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_transformation` (
  `ID_TRANSFORMATION` bigint(20) NOT NULL,
  `ID_DIRECTORY` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `EXTENDED_DESCRIPTION` mediumtext,
  `TRANS_VERSION` varchar(255) DEFAULT NULL,
  `TRANS_STATUS` int(11) DEFAULT NULL,
  `ID_STEP_READ` int(11) DEFAULT NULL,
  `ID_STEP_WRITE` int(11) DEFAULT NULL,
  `ID_STEP_INPUT` int(11) DEFAULT NULL,
  `ID_STEP_OUTPUT` int(11) DEFAULT NULL,
  `ID_STEP_UPDATE` int(11) DEFAULT NULL,
  `ID_DATABASE_LOG` int(11) DEFAULT NULL,
  `TABLE_NAME_LOG` varchar(255) DEFAULT NULL,
  `USE_BATCHID` char(1) DEFAULT NULL,
  `USE_LOGFIELD` char(1) DEFAULT NULL,
  `ID_DATABASE_MAXDATE` int(11) DEFAULT NULL,
  `TABLE_NAME_MAXDATE` varchar(255) DEFAULT NULL,
  `FIELD_NAME_MAXDATE` varchar(255) DEFAULT NULL,
  `OFFSET_MAXDATE` double DEFAULT NULL,
  `DIFF_MAXDATE` double DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  `SIZE_ROWSET` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANSFORMATION`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_transformation`
--

LOCK TABLES `r_transformation` WRITE;
/*!40000 ALTER TABLE `r_transformation` DISABLE KEYS */;
INSERT INTO `r_transformation` VALUES (1,0,'我的第一个转换',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','Y',-1,NULL,NULL,0,0,'-','2012-10-27 14:01:42','yuce','2013-10-29 21:38:59',10000),(2,32,'增加校验列 - 基于CRC32的例子',NULL,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','N',-1,NULL,NULL,0,0,NULL,'2011-05-25 17:16:55','admin','2013-08-22 19:16:22',1000),(3,32,'增加序列 - 基本例子',NULL,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','N',-1,NULL,NULL,0,0,NULL,'2013-08-22 19:18:03','admin','2013-08-22 19:21:55',10000),(4,32,'增加序列 - 指定一个通用计数器',NULL,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','N',-1,NULL,NULL,0,0,NULL,'2013-08-22 19:22:18','admin','2013-08-22 19:24:21',1000),(5,32,'增加一个序号字段 - 基本例子',NULL,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','Y',-1,NULL,NULL,0,0,'-','2012-04-20 21:41:36','admin','2013-08-22 19:29:59',10000);
/*!40000 ALTER TABLE `r_transformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user`
--

DROP TABLE IF EXISTS `r_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_user` (
  `ID_USER` bigint(20) NOT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ENABLED` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_USER`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user`
--

LOCK TABLES `r_user` WRITE;
/*!40000 ALTER TABLE `r_user` DISABLE KEYS */;
INSERT INTO `r_user` VALUES (1,'admin','2be98afc86aa7f2e4cb79ce61d68bacd1','系统管理员','系统管理账户','Y'),(0,'anomyous','','匿名','匿名登录账户','Y'),(3,'yuce','2be98afc86aa7f2e4cb79ce10c787acdf','预测','预测操作账户','Y');
/*!40000 ALTER TABLE `r_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_role`
--

DROP TABLE IF EXISTS `r_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_user_role` (
  `RID` bigint(20) NOT NULL,
  `UID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_role`
--

LOCK TABLES `r_user_role` WRITE;
/*!40000 ALTER TABLE `r_user_role` DISABLE KEYS */;
INSERT INTO `r_user_role` VALUES (100,3),(0,0),(1,1);
/*!40000 ALTER TABLE `r_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_value`
--

DROP TABLE IF EXISTS `r_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_value` (
  `ID_VALUE` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VALUE_TYPE` varchar(255) DEFAULT NULL,
  `VALUE_STR` varchar(255) DEFAULT NULL,
  `IS_NULL` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_VALUE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_value`
--

LOCK TABLES `r_value` WRITE;
/*!40000 ALTER TABLE `r_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_version`
--

DROP TABLE IF EXISTS `r_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_version` (
  `ID_VERSION` bigint(20) NOT NULL,
  `MAJOR_VERSION` int(11) DEFAULT NULL,
  `MINOR_VERSION` int(11) DEFAULT NULL,
  `UPGRADE_DATE` datetime DEFAULT NULL,
  `IS_UPGRADE` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID_VERSION`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_version`
--

LOCK TABLES `r_version` WRITE;
/*!40000 ALTER TABLE `r_version` DISABLE KEYS */;
INSERT INTO `r_version` VALUES (1,5,0,'2012-10-25 16:29:18','N');
/*!40000 ALTER TABLE `r_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-11 13:18:38
