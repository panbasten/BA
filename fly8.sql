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
-- Table structure for table `bi_domain`
--

DROP TABLE IF EXISTS `bi_domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_domain` (
  `ID_DOMAIN` bigint(20) NOT NULL,
  `ID_DOMAIN_DIRECTORY` int(11) DEFAULT NULL,
  `DOMAIN_TYPE` int(11) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  `DOMAIN_VERSION` varchar(255) DEFAULT NULL,
  `DOMAIN_STATUS` int(11) DEFAULT NULL,
  `CREATE_USER` varchar(255) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_DOMAIN`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_domain`
--

LOCK TABLES `bi_domain` WRITE;
/*!40000 ALTER TABLE `bi_domain` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_domain` ENABLE KEYS */;
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
INSERT INTO `bi_filesys_directory` VALUES (1,1,'C:\\_D\\_flywet','预测存储根目录','用于存放预测数据文件的根目录'),(2,1,'C:\\_D\\_bak','数据存储临时目录','用于存放数据文件的临时目录'),(3,2,'10.11.46.181','FTP测试接口机_181','FTP测试接口机_181'),(4,3,'10.11.46.181','SFTP测试接口机_181','SFTP测试接口机_181'),(5,4,'https://ciastudypattern.googlecode.com/svn/trunk/','存放文档的SVN','存放文档的SVN'),(6,5,'','存放文档的GIT','存放文档的GIT');
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
INSERT INTO `bi_func_type` VALUES (0,'root','SYS.Base','根',NULL,'功能的根节点',0),(1,'di','DI.Base','数据整合',0,'一级节点：转换/作业',3),(2,'db','DB.Base','数据源',0,'一级节点：数据源',0),(4,'systools','SYS.Base','功能',0,'一级节点：功能',5),(50,'privilege','SYS.Base','用户权限',4,'二级节点：功能-用户权限',0),(501,'createuser','SYS.Base','创建用户',50,'三级节点：功能-用户权限-创建用户',0),(502,'deluser','SYS.Base','删除用户',50,'三级节点：功能-用户权限-删除用户',1),(503,'edituser','SYS.Base','编辑当前用户',50,'三级节点：功能-用户权限-编辑当前用户',2),(60,'repository','SYS.Base','资源库',4,'二级节点：功能-资源库',1),(601,'connrep','SYS.Base','连接资源库',60,'三级节点：功能-资源库-连接资源库',0),(602,'findrep','SYS.Base','探测资源库',60,'三级节点：功能-资源库-探测资源库',1),(603,'createrep','SYS.Base','创建资源库',60,'三级节点：功能-资源库-创建资源库',2),(70,'file','SYS.Base','文件',4,'二级节点：功能-文件',2),(701,'newtrans','DI.Trans.Editor','新建转换',70,'三级节点：功能-文件-新建转换',0),(702,'newjob','DI.Job.Editor','新建作业',70,'三级节点：功能-文件-新建作业',1),(703,'newdb','DB.Advance','添加数据库',70,'三级节点：功能-文件-添加数据库',2),(704,'newfs','FS.Local,FS.FTP','添加文件系统',70,'三级节点：功能-文件-添加文件系统',3),(80,'operate','SYS.Base','操作',4,'二级节点：功能-操作',3),(705,'open','SYS.Advance','打开',70,'三级节点：功能-文件-打开',4),(706,'fileopen','SYS.Advance','从文件打开',70,'三级节点：功能-文件-从文件打开',5),(707,'downloadzip','SYS.Advance','打包下载',70,'三级节点：功能-文件-打包下载',6),(708,'uploadfile','SYS.Advance','上传文件',70,'三级节点：功能-文件-上传文件',7),(90,'wizard','SYS.Base','向导',4,'二级节点：功能-向导',4),(901,'wcreatedb','DB.Advance','创建数据库连接',90,'三级节点：功能-向导-创建数据库连接',0),(902,'wsinglecopy','DB.Advance','单表复制向导',90,'三级节点：功能-向导-单表复制向导',1),(903,'wmulticopy','DB.Advance','多表复制向导',90,'三级节点：功能-向导-多表复制向导',2),(100,'help','SYS.Base','帮助',4,'二级节点：功能-帮助',5),(1001,'showstepplugin','DI.Trans.Editor','显示步骤插件',100,'三级节点：功能-帮助-显示步骤插件',0),(1002,'showjobplugin','DI.Job.Editor','显示作业插件',100,'三级节点：功能-帮助-显示作业插件',1),(1010,'about','SYS.Base','关于',100,'三级节点：功能-帮助-关于',3),(3,'domain','DN.Base','语义模型',0,'一级节点：语义层',1),(5,'fs','FS.Base','文件系统',0,'一级节点：文件系统',4),(6,'report','BA.Base','商业分析',0,'一级节点：报表',2),(1003,'showdbplugin','DB.Advance','显示数据库插件',100,'三级节点：功能-帮助-显示数据库插件',2),(801,'timedexecution','SYS.Advance','定时执行',80,'三级节点：功能-操作-定时执行',NULL),(802,'automatingjobs','DI.Job.Editor','自动执行作业',80,'三级节点：功能-操作-自动执行作业',NULL);
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
  `CLS` varchar(255) DEFAULT NULL,
  `METHOD` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_PORTAL_ACTION`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_portal_action`
--

LOCK TABLES `bi_portal_action` WRITE;
/*!40000 ALTER TABLE `bi_portal_action` DISABLE KEYS */;
INSERT INTO `bi_portal_action` VALUES (1,'预测产品-月预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredict'),(2,'预测产品-延伸期预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredict'),(3,'预测评分-月预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictScore'),(4,'预测评分-延伸期预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictScore'),(5,'预测产品-当月预测填报-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','predictSetting'),(6,'预测产品-当月延伸期预测填报-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendSetting'),(7,'预测评分-上月预测评分填报-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','scoreSetting'),(8,'方法评估-月预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictEvaluate'),(9,'方法评估-延伸期降水过程预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictPrecipitation'),(10,'分析工具-海温月预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','sstMonthPredict'),(11,'分析工具-海温季预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','sstQuarterPredict'),(12,'制作评分数据-月预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictData'),(13,'制作评分数据-延伸期预测-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictData'),(14,'分析工具-数据更新-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','dataUpdate'),(15,'制作评分数据-上报数据-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','dataUpload'),(16,'业务定时期-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','buzTimed'),(17,'业务规范-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','buzNorms'),(18,'相关网站链接-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','links'),(19,'预测产品-月预测-按月份更新','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictUpdate'),(20,'预测产品-延伸期预测-按旬更新','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictUpdate'),(21,'预测产品-当月预测填报-更新','com.flywet.cust.p001.portal.ForecastAdaptorImpl','predictSettingUpdate'),(22,'分析工具-数据更新-更新文件','com.flywet.cust.p001.portal.ForecastAdaptorImpl','dataUpdateFiles'),(23,'分析工具-海温月预测-执行','com.flywet.cust.p001.portal.ForecastAdaptorImpl','sstMonthPredictRun'),(24,'分析工具-海温季预测-执行','com.flywet.cust.p001.portal.ForecastAdaptorImpl','sstQuarterPredictRun'),(25,'制作评分数据-月预测-执行','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictDataRun'),(26,'制作评分数据-延伸期预测-执行','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictDataRun'),(27,'预测产品-延伸期预测-更新','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendSettingUpdate'),(28,'业务规范-更新','com.flywet.cust.p001.portal.ForecastAdaptorImpl','buzNormsUpdate'),(29,'预测评分-上月预测评分填报-更新','com.flywet.cust.p001.portal.ForecastAdaptorImpl','scoreSettingUpdate'),(30,'方法评估-上月预测评估填报-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictEvaluateSetting'),(31,'方法评估-上月预测评估填报-提交','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictEvaluateSettingSubmit'),(32,'方法评估-上月延伸期降水过程预测评估填报-打开页面','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictPrecipitationSetting'),(33,'方法评估-上月延伸期降水过程预测评估填报-提交','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictPrecipitationSettingSubmit'),(34,'制作评分数据-上报数据-月预测评分数据上传','com.flywet.cust.p001.portal.ForecastAdaptorImpl','monthPredictDataUpload'),(35,'制作评分数据-上报数据-延伸期预测评分数据上传','com.flywet.cust.p001.portal.ForecastAdaptorImpl','extendPredictDataUpload');
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
INSERT INTO `bi_portal_menu` VALUES (0,'root','Anomyous','根',-1,'Portal的根节点',NULL,'N','Y'),(10,'predictProduct','Anomyous','预测产品',0,'',0,'N','Y'),(11,'predictScore','Anomyous','预测评分',0,NULL,1,'N','Y'),(12,'methodEvaluate','Anomyous','方法评估',0,NULL,2,'N','Y'),(100,'monthPredict','Anomyous','月预测',10,NULL,0,'N','Y'),(101,'extendPredict','Anomyous','延伸期预测',10,NULL,1,'N','Y'),(102,'monthPredictScore','Anomyous','月预测',11,NULL,0,'N','Y'),(103,'extendPredictScore','Anomyous','延伸期预测',11,NULL,1,'N','N'),(20,'setting','Yuce','设置',0,NULL,10,'Y','N'),(104,'extendSetting','Yuce','当月延伸期预测填报',10,NULL,3,'Y','Y'),(105,'predictSetting','Yuce','当月预测填报',10,NULL,2,'Y','Y'),(106,'scoreSetting','Yuce','上月预测评分填报',11,NULL,2,'Y','Y'),(107,'monthPredictEvaluate','Anomyous','月预测',12,NULL,0,'N','Y'),(108,'extendPredictPrecipitation','Anomyous','延伸期降水过程预测',12,NULL,1,'N','Y'),(113,'dataUpdate','Yuce','数据更新',14,NULL,2,'Y','Y'),(14,'analyseTools','Yuce','分析工具',0,NULL,4,'Y','Y'),(109,'sstMonthPredict','Yuce','海温月预测',14,NULL,0,'Y','Y'),(110,'sstQuarterPredict','Yuce','海温季预测',14,NULL,1,'Y','Y'),(15,'makeScoreData','Yuce','制作评分数据',0,NULL,5,'Y','Y'),(111,'monthPredictData','Yuce','月预测',15,NULL,0,'Y','Y'),(112,'extendPredictData','Yuce','延伸期预测',15,NULL,1,'Y','Y'),(114,'dataUpload','Yuce','上报数据',15,NULL,2,'Y','Y'),(115,'buzTimed','Anomyous','业务定时期',20,NULL,2,'N','N'),(16,'buzNorms','Anomyous','业务规范',0,NULL,6,'N','Y'),(17,'links','Anomyous','相关网站链接',0,NULL,7,'N','Y'),(116,'monthPredictEvaluateSetting','Yuce','上月预测评估填报',12,NULL,2,'Y','Y'),(117,'extendPredictPrecipitationSetting','Yuce','上月延伸期降水过程预测评估填报',12,NULL,3,'Y','Y');
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
INSERT INTO `bi_portal_menu_attribute` VALUES (1,100,'action','1'),(3,101,'action','2'),(4,102,'action','3'),(5,103,'action','4'),(2,100,'autoMaximized','true'),(10,105,'action','5'),(12,104,'action','6'),(15,104,'btn','[{\"label\":\"确定\",\"title\":\"确定\",\"action\":\"27\"},{\"type\":\"cancel\"}]'),(16,106,'action','7'),(18,107,'action','8'),(20,108,'action','9'),(22,109,'action','10'),(24,110,'action','11'),(26,111,'action','12'),(28,112,'action','13'),(30,113,'action','14'),(32,114,'action','15'),(34,115,'action','16'),(36,16,'action','17'),(38,17,'action','18'),(31,113,'btn','[{\"label\":\"确定\",\"title\":\"确定\",\"click\":{\"func\":\"Flywet.PortalAction.uploadFiles(dialogId,{formAction:\'rest/portalet/actionFileForm/22\'})\"}},{\"type\":\"cancel\"}]'),(23,109,'btn','[{\"label\":\"海温预测分析计算\",\"title\":\"海温预测分析计算\",\"action\":\"23\",\"ajaxType\":\"get\"},{\"type\":\"cancel\"}]'),(25,110,'btn','[{\"label\":\" 编辑输入文件namelist\",\"title\":\"编辑输入文件namelist\",\"click\":{\"func\":\"Flywet.PortalAction.openEditFileDialog(\'custom.portal.seasonForecast.file.rootPath\',\'custom.portal.seasonForecast.file.category\',\'custom.portal.seasonForecast.file.fileName\',\'namelist\');\"}},{\"label\":\"海温预测分析计算\",\"title\":\"海温预测分析计算\",\"action\":\"24\",\"ajaxType\":\"get\"},{\"type\":\"cancel\"}]'),(33,114,'btn','[{\"label\":\"月预测评分数据上传\",\"title\":\"月预测评分数据上传\",\"action\":\"34\",\"ajaxType\":\"get\"},{\"label\":\"延伸期预测评分数据上传\",\"title\":\"延伸期预测评分数据上传\",\"action\":\"35\",\"ajaxType\":\"get\"},{\"type\":\"cancel\"}]'),(27,111,'btn','[{\"label\":\"编辑输入文件cityforecast.txt\",\"title\":\"编辑输入文件cityforecast.txt\",\"click\":{\"func\":\"Flywet.PortalAction.openEditFileDialog(\'custom.portal.evaluation.file.rootPath\',\'custom.portal.evaluation.file.category\',\'custom.portal.evaluation.file.fileName\',\'cityforecast.txt\');\"}},{\"label\":\"统计将尺度分析计算\",\"title\":\"统计将尺度分析计算\",\"action\":\"25\",\"ajaxType\":\"get\"},{\"type\":\"cancel\"}]'),(29,112,'btn','[{\"label\":\"编辑输入文件namelist\",\"title\":\"编辑输入文件namelist\",\"click\":{\"func\":\"Flywet.PortalAction.openEditFileDialog(\'custom.portal.processForecast.file.rootPath\',\'custom.portal.processForecast.file.category\',\'custom.portal.processForecast.file.fileName\',\'namelist\');\"}},{\"label\":\"制作142站预测上传文件\",\"title\":\"制作142站预测上传文件\",\"action\":\"26\",\"ajaxType\":\"get\"},{\"type\":\"cancel\"}]'),(46,109,'height','100'),(47,110,'height','100'),(48,111,'height','100'),(49,112,'height','100'),(50,113,'height','200'),(51,114,'height','100'),(39,17,'width','300'),(6,102,'width','800'),(17,106,'btn','[{\"label\":\"确定\",\"title\":\"确定\",\"action\":\"29\"},{\"type\":\"cancel\"}]'),(52,116,'action','30'),(53,116,'btn','[{\"label\":\"确定\",\"title\":\"确定\",\"action\":\"31\"},{\"type\":\"cancel\"}]'),(54,117,'action','32'),(55,117,'btn','[{\"label\":\"确定\",\"title\":\"确定\",\"action\":\"33\"},{\"type\":\"cancel\"}]'),(56,116,'autoMaximized','true');
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
INSERT INTO `bi_report` VALUES (0,1,'<fly:composition width=\"600\" height=\"400\">\r\n	<fly:verticalLayout>\r\n		<fly:fieldSet title=\"标识符\">\r\n			<fly:gridLayout column=\"4\" itemWidth=\"35%,15%,35%,15%\">\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" text=\"支持布尔数据类型\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" name=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:QUOTE_ALL_FIELDS\" text=\"标识符使用引号括起来\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:QUOTE_ALL_FIELDS\" name=\"${formId}:QUOTE_ALL_FIELDS\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" text=\"强制标识符使用小写字母\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" text=\"强制标识符使用大写字母\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n			</fly:gridLayout>\r\n		</fly:fieldSet>\r\n		\r\n		<fly:labelObject buddy=\"${formId}:preferredSchemaName\" text=\"默认模式名称（在没有其他模式名时使用）\" />\r\n		\r\n		<fly:inputText id=\"${formId}:preferredSchemaName\" name=\"${formId}:preferredSchemaName\" type=\"text\" value=\"\" />\r\n		\r\n		<fly:labelObject buddy=\"${formId}:connectionType\" text=\"请输入连接成功后要执行的SQL语句，用分号(;)隔开\" />\r\n		\r\n		<textarea id=\"${formId}:connectSQL\" name=\"${formId}:connectSQL\" style=\"width:100%;\" rows=\"6\">\r\n			\r\n		</textarea>\r\n		\r\n	</fly:verticalLayout>\r\n\r\n	<fly:actions>\r\n\r\n		<fly:action sender=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" signal=\"change\" accepter=\"${formId}:preferredSchemaName\" slot=\"disable\" />\r\n\r\n	</fly:actions>\r\n\r\n</fly:composition>',1,'N',NULL,'Dashboard例子',NULL,NULL,NULL,NULL,NULL,NULL),(1,1,NULL,2,'N',NULL,'报告例子',NULL,NULL,NULL,NULL,NULL,NULL),(2,1,NULL,3,'N',NULL,'自由报表例子',NULL,NULL,NULL,NULL,NULL,NULL),(3,1,NULL,4,'N',NULL,'透视报表例子',NULL,NULL,NULL,NULL,NULL,NULL),(4,10,NULL,1,'Y',0,'表单例子的引用',NULL,NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `bi_report_type` VALUES (1,'dashboard','仪表板'),(3,'report','报表'),(4,'pivotreport','多维报表'),(2,'wordreport','报告');
/*!40000 ALTER TABLE `bi_report_type` ENABLE KEYS */;
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
-- Table structure for table `c_extend_predict`
--

DROP TABLE IF EXISTS `c_extend_predict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_extend_predict` (
  `ID_EXTEND_PREDICT` bigint(20) NOT NULL,
  `YEAR` int(11) DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `XUN` int(11) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  PRIMARY KEY (`ID_EXTEND_PREDICT`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_extend_predict`
--

LOCK TABLES `c_extend_predict` WRITE;
/*!40000 ALTER TABLE `c_extend_predict` DISABLE KEYS */;
INSERT INTO `c_extend_predict` VALUES (0,2013,6,0,'2～5日，全省大部分地区有阵雨或雷雨，东北部地区有中到大雨；\r\n10～12日，全省有中到大雨，局部暴雨；\r\n14～15日，全省有小到中雨；\r\n22～24日，全省有小到中雨，局部大雨；\r\n29～31日，全省大部分地区有小到中雨'),(1,2013,6,2,'2～5日，全省大部分地区有阵雨或雷雨；\r\n10～12日，全省大部分地区有小到中雨；\r\n14～15日，全省有小到中雨；\r\n22～24日，全省有小到中雨，局部大雨；\r\n29～31日，全省有中到大雨，局部暴雨'),(2,2013,10,1,'dfsfsdfsdf\r\n\r\nczczxcxzc\r\ndfsfsdfseeeee');
/*!40000 ALTER TABLE `c_extend_predict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_extend_predict_eva`
--

DROP TABLE IF EXISTS `c_extend_predict_eva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_extend_predict_eva` (
  `ID_EXTEND_PREDICT_EVA` bigint(20) NOT NULL,
  `YEAR` int(11) DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `S1` int(11) DEFAULT NULL,
  `S2` int(11) DEFAULT NULL,
  `S3` int(11) DEFAULT NULL,
  `S4` int(11) DEFAULT NULL,
  `S5` int(11) DEFAULT NULL,
  `S6` int(11) DEFAULT NULL,
  `S7` int(11) DEFAULT NULL,
  `S8` int(11) DEFAULT NULL,
  `S9` int(11) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  PRIMARY KEY (`ID_EXTEND_PREDICT_EVA`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_extend_predict_eva`
--

LOCK TABLES `c_extend_predict_eva` WRITE;
/*!40000 ALTER TABLE `c_extend_predict_eva` DISABLE KEYS */;
INSERT INTO `c_extend_predict_eva` VALUES (1,2013,7,3,2,1,3,2,1,3,3,0,NULL),(2,2013,8,3,3,0,3,1,2,3,2,1,NULL),(3,2013,9,3,2,1,4,3,1,0,0,0,'fsdfsf\r\ndfdfs\r\n劳动法斯蒂芬\r\n地方的说法');
/*!40000 ALTER TABLE `c_extend_predict_eva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_month_predict_eva`
--

DROP TABLE IF EXISTS `c_month_predict_eva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_month_predict_eva` (
  `ID_MONTH_PREDICT_EVA` bigint(20) NOT NULL,
  `YEAR` int(11) DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `S1` int(11) DEFAULT NULL,
  `S2` int(11) DEFAULT NULL,
  `S3` int(11) DEFAULT NULL,
  `S4` int(11) DEFAULT NULL,
  `S5` int(11) DEFAULT NULL,
  `S6` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_MONTH_PREDICT_EVA`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_month_predict_eva`
--

LOCK TABLES `c_month_predict_eva` WRITE;
/*!40000 ALTER TABLE `c_month_predict_eva` DISABLE KEYS */;
INSERT INTO `c_month_predict_eva` VALUES (1,2013,7,55,85,77,78,65,73),(2,2013,8,65,55,63,78,45,77),(3,2013,9,87,92,85,96,100,100),(4,2013,10,96,100,100,100,100,100);
/*!40000 ALTER TABLE `c_month_predict_eva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_month_predict_eva_d`
--

DROP TABLE IF EXISTS `c_month_predict_eva_d`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_month_predict_eva_d` (
  `YEAR` int(11) DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `S1` int(11) DEFAULT NULL,
  `S2` int(11) DEFAULT NULL,
  `S3` int(11) DEFAULT NULL,
  `S4` int(11) DEFAULT NULL,
  `S5` int(11) DEFAULT NULL,
  `S6` int(11) DEFAULT NULL,
  `S7` int(11) DEFAULT NULL,
  `S8` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_month_predict_eva_d`
--

LOCK TABLES `c_month_predict_eva_d` WRITE;
/*!40000 ALTER TABLE `c_month_predict_eva_d` DISABLE KEYS */;
INSERT INTO `c_month_predict_eva_d` VALUES (2013,9,'xt',0,0,0,0,0,0,0,0),(2013,9,'sjz',0,0,0,0,0,0,0,0),(2013,9,'bd',0,0,0,0,-1,0,0,0),(2013,9,'hs',0,0,0,-1,0,0,0,0),(2013,9,'cz',0,0,1,0,0,0,0,0),(2013,9,'lf',0,0,-1,0,0,0,0,0),(2013,9,'ts',-1,0,0,1,0,0,0,0),(2013,9,'qhd',0,0,0,0,0,0,0,0),(2013,9,'cd',0,0,0,0,0,0,0,0),(2013,9,'zjk',0,0,0,0,0,0,0,0),(2013,9,'hd',0,0,0,0,0,0,0,0),(2013,10,'zjk',0,0,0,0,0,0,0,0),(2013,10,'cd',0,0,0,0,0,0,0,0),(2013,10,'qhd',0,0,0,0,0,0,0,0),(2013,10,'ts',1,0,0,0,0,0,0,0),(2013,10,'lf',0,0,0,0,0,0,0,0),(2013,10,'cz',0,0,0,0,0,0,0,0),(2013,10,'hs',0,0,0,0,0,0,0,0),(2013,10,'bd',0,0,0,0,0,0,0,0),(2013,10,'sjz',0,0,0,0,0,0,0,0),(2013,10,'xt',0,0,0,0,0,0,0,0),(2013,10,'hd',0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `c_month_predict_eva_d` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_month_predict_score`
--

DROP TABLE IF EXISTS `c_month_predict_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_month_predict_score` (
  `ID_MONTH_PREDICT_SCORE` bigint(20) NOT NULL,
  `YEAR` int(11) DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `S1` varchar(255) DEFAULT NULL,
  `S2` varchar(255) DEFAULT NULL,
  `S3` varchar(255) DEFAULT NULL,
  `S4` varchar(255) DEFAULT NULL,
  `S5` varchar(255) DEFAULT NULL,
  `S6` varchar(255) DEFAULT NULL,
  `S7` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_MONTH_PREDICT_SCORE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_month_predict_score`
--

LOCK TABLES `c_month_predict_score` WRITE;
/*!40000 ALTER TABLE `c_month_predict_score` DISABLE KEYS */;
INSERT INTO `c_month_predict_score` VALUES (1,2013,6,'100 / 67','32 / 23','12 / 5',' 67.1 / 55.2','80.1 / 67.0','67.3 / 55.8',' 0.32 / 0.33'),(2,2013,8,'90 / 67','32 / 23','12 / 5','67.1 / 55.2','80.1 / 67.0','67.3 / 55.8','0.32 / 0.33'),(3,2013,9,'10 / ','d / ',' / ',' / ',' / ',' / ',' / ');
/*!40000 ALTER TABLE `c_month_predict_score` ENABLE KEYS */;
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
INSERT INTO `r_authorization` VALUES (100,20,2,7),(100,104,2,7),(100,106,2,7),(100,14,2,7),(100,15,2,7),(100,105,2,7),(100,109,2,7),(100,110,2,7),(100,111,2,7),(100,112,2,7),(100,113,2,7),(100,114,2,7),(0,14,2,4),(0,15,2,4),(0,20,2,4),(0,104,2,4),(0,105,2,4),(0,106,2,4),(0,109,2,4),(0,110,2,4),(0,111,2,4),(0,112,2,4),(0,113,2,4),(0,114,2,4),(100,116,2,7),(100,117,2,7),(0,116,2,4),(0,117,2,4);
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
INSERT INTO `r_database` VALUES (1,'imeta',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL),(2,'imeta1',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL),(3,'imeta11',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL),(4,'ddds',33,1,NULL,NULL,1521,NULL,'Encrypted ',NULL,NULL,NULL);
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
INSERT INTO `r_database_attribute` VALUES (155,1,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(156,1,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(157,1,'PREFERRED_SCHEMA_NAME',NULL),(158,1,'QUOTE_ALL_FIELDS','false'),(159,1,'SQL_CONNECT',NULL),(153,1,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(151,1,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(152,1,'STREAM_RESULTS','N'),(148,1,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(149,1,'USE_POOLING','N'),(59,2,'QUOTE_ALL_FIELDS','false'),(57,2,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(58,2,'PREFERRED_SCHEMA_NAME',NULL),(56,2,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(55,2,'PORT_NUMBER','3306'),(54,2,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(52,2,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(150,1,'IS_CLUSTERED','N'),(81,3,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(80,3,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(79,3,'PORT_NUMBER','3306'),(78,3,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(77,3,'STREAM_RESULTS','N'),(76,3,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(75,3,'IS_CLUSTERED','N'),(74,3,'USE_POOLING','N'),(73,3,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(53,2,'STREAM_RESULTS','N'),(51,2,'IS_CLUSTERED','N'),(50,2,'USE_POOLING','N'),(49,2,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(60,2,'SQL_CONNECT',NULL),(154,1,'PORT_NUMBER','3306'),(82,3,'PREFERRED_SCHEMA_NAME',NULL),(83,3,'QUOTE_ALL_FIELDS','false'),(84,3,'SQL_CONNECT',NULL),(160,4,'USE_POOLING','N'),(161,4,'IS_CLUSTERED','N'),(162,4,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(163,4,'PORT_NUMBER','1521'),(164,4,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(112,5,'USE_POOLING','N'),(113,5,'IS_CLUSTERED','N'),(114,5,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(115,5,'PORT_NUMBER','1521'),(116,5,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(117,5,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(165,4,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(118,5,'PREFERRED_SCHEMA_NAME',NULL),(119,5,'QUOTE_ALL_FIELDS','false'),(120,5,'SQL_CONNECT',NULL),(121,6,'USE_POOLING','N'),(122,6,'IS_CLUSTERED','N'),(123,6,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(124,6,'PORT_NUMBER','1521'),(125,6,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(126,6,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(127,6,'PREFERRED_SCHEMA_NAME',NULL),(128,6,'QUOTE_ALL_FIELDS','false'),(129,6,'SQL_CONNECT',NULL),(130,7,'USE_POOLING','N'),(131,7,'IS_CLUSTERED','N'),(132,7,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(133,7,'PORT_NUMBER','1521'),(134,7,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(135,7,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(136,7,'PREFERRED_SCHEMA_NAME',NULL),(137,7,'QUOTE_ALL_FIELDS','false'),(138,7,'SQL_CONNECT',NULL),(139,8,'USE_POOLING','N'),(140,8,'IS_CLUSTERED','N'),(141,8,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(142,8,'PORT_NUMBER','1521'),(143,8,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(144,8,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(145,8,'PREFERRED_SCHEMA_NAME',NULL),(146,8,'QUOTE_ALL_FIELDS','false'),(147,8,'SQL_CONNECT',NULL),(166,4,'PREFERRED_SCHEMA_NAME',NULL),(167,4,'QUOTE_ALL_FIELDS','false'),(168,4,'SQL_CONNECT',NULL);
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

-- Dump completed on 2013-11-04 21:38:39
