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
-- Table structure for table `r_authorization`
--

DROP TABLE IF EXISTS `r_authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_authorization` (
  `rid` bigint(20) NOT NULL,
  `fid` bigint(20) NOT NULL,
  `permission` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_authorization`
--

LOCK TABLES `r_authorization` WRITE;
/*!40000 ALTER TABLE `r_authorization` DISABLE KEYS */;
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
INSERT INTO `r_database` VALUES (1,'imeta',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL);
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
INSERT INTO `r_database_attribute` VALUES (9,1,'PREFERRED_SCHEMA_NAME',NULL),(8,1,'FORCE_IDENTIFIERS_TO_UPPERCASE','N'),(7,1,'PORT_NUMBER','3306'),(6,1,'EXTRA_OPTION_MYSQL.defaultFetchSize','500'),(5,1,'STREAM_RESULTS','Y'),(4,1,'SUPPORTS_BOOLEAN_DATA_TYPE','N'),(3,1,'IS_CLUSTERED','N'),(2,1,'USE_POOLING','N'),(1,1,'EXTRA_OPTION_MYSQL.useCursorFetch','true'),(10,1,'FORCE_IDENTIFIERS_TO_LOWERCASE','N'),(11,1,'SQL_CONNECT',NULL),(12,1,'QUOTE_ALL_FIELDS','N');
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
INSERT INTO `r_directory` VALUES (10,0,'新目录新目录新目录新目录新目录新目录'),(0,NULL,'转换/作业'),(1,NULL,'报表'),(2,1,'系统目录'),(3,1,'个人目录');
/*!40000 ALTER TABLE `r_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_filesys_attribute`
--

DROP TABLE IF EXISTS `r_filesys_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_filesys_attribute` (
  `ID_FILESYS_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_FILESYS_DIRECTORY` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_FILESYS_ATTRIBUTE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_filesys_attribute`
--

LOCK TABLES `r_filesys_attribute` WRITE;
/*!40000 ALTER TABLE `r_filesys_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_filesys_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_filesys_directory`
--

DROP TABLE IF EXISTS `r_filesys_directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_filesys_directory` (
  `ID_FS_DIRECTORY` bigint(20) NOT NULL,
  `FS_TYPE` int(11) DEFAULT NULL,
  `PATH` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `NOTES` mediumtext,
  PRIMARY KEY (`ID_FS_DIRECTORY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_filesys_directory`
--

LOCK TABLES `r_filesys_directory` WRITE;
/*!40000 ALTER TABLE `r_filesys_directory` DISABLE KEYS */;
INSERT INTO `r_filesys_directory` VALUES (1,1,'C:\\Qt','数据存储根目录','用于存放数据文件的根目录'),(2,1,'C:\\_D\\_bak','数据存储临时目录','用于存放数据文件的临时目录'),(3,2,'10.11.46.181','FTP测试接口机_181','FTP测试接口机_181'),(4,3,'10.11.46.181','SFTP测试接口机_181','SFTP测试接口机_181'),(5,4,'https://ciastudypattern.googlecode.com/svn/trunk/','存放文档的SVN','存放文档的SVN'),(6,5,'','存放文档的GIT','存放文档的GIT');
/*!40000 ALTER TABLE `r_filesys_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_filesys_type`
--

DROP TABLE IF EXISTS `r_filesys_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_filesys_type` (
  `ID_FS_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) NOT NULL,
  `DESCRIPTION` mediumtext NOT NULL,
  PRIMARY KEY (`ID_FS_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_filesys_type`
--

LOCK TABLES `r_filesys_type` WRITE;
/*!40000 ALTER TABLE `r_filesys_type` DISABLE KEYS */;
INSERT INTO `r_filesys_type` VALUES (1,'local','服务器文件系统'),(2,'ftp','FTP文件系统'),(3,'sftp','SFTP文件系统'),(4,'svn','SVN服务器'),(5,'git','GIT服务器');
/*!40000 ALTER TABLE `r_filesys_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_func_type`
--

DROP TABLE IF EXISTS `r_func_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_func_type` (
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
-- Dumping data for table `r_func_type`
--

LOCK TABLES `r_func_type` WRITE;
/*!40000 ALTER TABLE `r_func_type` DISABLE KEYS */;
INSERT INTO `r_func_type` VALUES (0,'root','SYS.Base','根',NULL,'功能的根节点',0),(1,'transjob','DI.Base','数据整合',0,'一级节点：转换/作业',3),(2,'db','DB.Base','数据源',0,'一级节点：数据源',0),(4,'systools','SYS.Base','功能',0,'一级节点：功能',5),(50,'privilege','SYS.Base','用户权限',4,'二级节点：功能-用户权限',0),(501,'createuser','SYS.Base','创建用户',50,'三级节点：功能-用户权限-创建用户',0),(502,'deluser','SYS.Base','删除用户',50,'三级节点：功能-用户权限-删除用户',1),(503,'edituser','SYS.Base','编辑当前用户',50,'三级节点：功能-用户权限-编辑当前用户',2),(60,'repository','SYS.Base','资源库',4,'二级节点：功能-资源库',1),(601,'connrep','SYS.Base','连接资源库',60,'三级节点：功能-资源库-连接资源库',0),(602,'findrep','SYS.Base','探测资源库',60,'三级节点：功能-资源库-探测资源库',1),(603,'createrep','SYS.Base','创建资源库',60,'三级节点：功能-资源库-创建资源库',2),(70,'file','SYS.Base','文件',4,'二级节点：功能-文件',2),(701,'newtrans','DI.Trans.Editor','新建转换',70,'三级节点：功能-文件-新建转换',0),(702,'newjob','DI.Job.Editor','新建作业',70,'三级节点：功能-文件-新建作业',1),(703,'newdb','DB.Advance','添加数据库',70,'三级节点：功能-文件-添加数据库',2),(704,'newfs','FS.Local,FS.FTP','添加文件系统',70,'三级节点：功能-文件-添加文件系统',3),(80,'operate','SYS.Base','操作',4,'二级节点：功能-操作',3),(801,'open','SYS.Advance','打开',80,'三级节点：功能-操作-打开',0),(802,'fileopen','SYS.Advance','从文件打开',80,'三级节点：功能-操作-从文件打开',1),(803,'downloadzip','SYS.Advance','打包下载',80,'三级节点：功能-操作-打包下载',2),(804,'uploadfile','SYS.Advance','上传文件',80,'三级节点：功能-操作-上传文件',3),(90,'wizard','SYS.Base','向导',4,'二级节点：功能-向导',4),(901,'wcreatedb','DB.Advance','创建数据库连接',90,'三级节点：功能-向导-创建数据库连接',0),(902,'wsinglecopy','DB.Advance','单表复制向导',90,'三级节点：功能-向导-单表复制向导',1),(903,'wmulticopy','DB.Advance','多表复制向导',90,'三级节点：功能-向导-多表复制向导',2),(100,'help','SYS.Base','帮助',4,'二级节点：功能-帮助',5),(1001,'showstepplugin','DI.Trans.Editor','显示步骤插件',100,'三级节点：功能-帮助-显示步骤插件',0),(1002,'showjobplugin','DI.Job.Editor','显示作业插件',100,'三级节点：功能-帮助-显示作业插件',1),(1003,'about','SYS.Base','关于',100,'三级节点：功能-帮助-关于',2),(3,'domain','DN.Base','语义模型',0,'一级节点：语义层',1),(5,'fs','FS.Base','文件系统',0,'一级节点：文件系统',4),(6,'report','BA.Base','商业分析',0,'一级节点：报表',2);
/*!40000 ALTER TABLE `r_func_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_func_type_attribute`
--

DROP TABLE IF EXISTS `r_func_type_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_func_type_attribute` (
  `ID_FUNC_TYPE_ATTR` bigint(20) NOT NULL,
  `ID_FUNC_TYPE` int(11) NOT NULL,
  `CODE` varchar(255) NOT NULL,
  `VALUE_STR` mediumtext,
  PRIMARY KEY (`ID_FUNC_TYPE_ATTR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_func_type_attribute`
--

LOCK TABLES `r_func_type_attribute` WRITE;
/*!40000 ALTER TABLE `r_func_type_attribute` DISABLE KEYS */;
INSERT INTO `r_func_type_attribute` VALUES (0,1003,'url','rest/systools/about'),(1,1003,'title','关于'),(2,1003,'width','420'),(3,1003,'height','480');
/*!40000 ALTER TABLE `r_func_type_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_host`
--

DROP TABLE IF EXISTS `r_host`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_host` (
  `id_host` bigint(20) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `ip` varchar(32) NOT NULL,
  `port` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `mode` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id_host`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_host`
--

LOCK TABLES `r_host` WRITE;
/*!40000 ALTER TABLE `r_host` DISABLE KEYS */;
INSERT INTO `r_host` VALUES (1,'ftp_01','FTP测试接口机_181','10.11.46.181',2222,'admin','1234','ftp','测试接口机_181','ACTIVE'),(2,'ftp_02','SFTP测试接口机_181','10.11.46.181',22,'root','123456','sftp','nnnnn','active');
/*!40000 ALTER TABLE `r_host` ENABLE KEYS */;
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
INSERT INTO `r_job` VALUES (1,0,'测试作业 1',NULL,NULL,NULL,0,-1,NULL,'-','2012-12-09 12:36:02','admin','2012-12-09 12:36:43','Y','N','Y',NULL);
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
-- Table structure for table `r_report`
--

DROP TABLE IF EXISTS `r_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_report` (
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
-- Dumping data for table `r_report`
--

LOCK TABLES `r_report` WRITE;
/*!40000 ALTER TABLE `r_report` DISABLE KEYS */;
INSERT INTO `r_report` VALUES (0,1,'<fly:composition width=\"600\" height=\"400\">\r\n	<fly:verticalLayout>\r\n		<fly:fieldSet title=\"标识符\">\r\n			<fly:gridLayout column=\"4\" itemWidth=\"35%,15%,35%,15%\">\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" text=\"支持布尔数据类型\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" name=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:QUOTE_ALL_FIELDS\" text=\"标识符使用引号括起来\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:QUOTE_ALL_FIELDS\" name=\"${formId}:QUOTE_ALL_FIELDS\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" text=\"强制标识符使用小写字母\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" text=\"强制标识符使用大写字母\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n			</fly:gridLayout>\r\n		</fly:fieldSet>\r\n		\r\n		<fly:labelObject buddy=\"${formId}:preferredSchemaName\" text=\"默认模式名称（在没有其他模式名时使用）\" />\r\n		\r\n		<fly:inputText id=\"${formId}:preferredSchemaName\" name=\"${formId}:preferredSchemaName\" type=\"text\" value=\"\" />\r\n		\r\n		<fly:labelObject buddy=\"${formId}:connectionType\" text=\"请输入连接成功后要执行的SQL语句，用分号(;)隔开\" />\r\n		\r\n		<textarea id=\"${formId}:connectSQL\" name=\"${formId}:connectSQL\" style=\"width:100%;\" rows=\"6\">\r\n			\r\n		</textarea>\r\n		\r\n	</fly:verticalLayout>\r\n\r\n	<fly:actions>\r\n\r\n		<fly:action sender=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" signal=\"change\" accepter=\"${formId}:preferredSchemaName\" slot=\"disable\" />\r\n\r\n	</fly:actions>\r\n\r\n</fly:composition>',1,'N',NULL,'Dashboard例子',NULL,NULL,NULL,NULL,NULL,NULL),(1,1,NULL,2,'N',NULL,'报告例子',NULL,NULL,NULL,NULL,NULL,NULL),(2,1,NULL,3,'N',NULL,'自由报表例子',NULL,NULL,NULL,NULL,NULL,NULL),(3,1,NULL,4,'N',NULL,'透视报表例子',NULL,NULL,NULL,NULL,NULL,NULL),(4,2,NULL,1,'Y',0,'表单例子的引用',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `r_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_report_type`
--

DROP TABLE IF EXISTS `r_report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_report_type` (
  `ID_REPORT_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext,
  PRIMARY KEY (`ID_REPORT_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_report_type`
--

LOCK TABLES `r_report_type` WRITE;
/*!40000 ALTER TABLE `r_report_type` DISABLE KEYS */;
INSERT INTO `r_report_type` VALUES (1,'dashboard','仪表板'),(3,'report','报表'),(4,'pivotreport','多维报表'),(2,'wordreport','报告');
/*!40000 ALTER TABLE `r_report_type` ENABLE KEYS */;
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
INSERT INTO `r_repository_log` VALUES (1,'4.0','2012-10-27 14:01:29','admin','save transformation \'转换 1\''),(2,'4.0','2012-10-27 14:02:05','admin','save transformation \'转换 1\''),(3,'5.0','2012-11-21 11:33:02','admin','Creation of initial version'),(4,'5.0','2012-11-21 13:28:48','admin','save transformation \'转换 1\''),(5,'5.0','2012-12-06 14:36:27','admin','save transformation \'转换 1\''),(6,'5.0','2012-12-06 14:37:48','admin','save transformation \'测试用的转换\''),(7,'5.0','2012-12-09 12:36:43','admin','save job \'测试作业 1\''),(8,'5.0','2013-01-10 15:30:58','admin','save transformation \'测试用的转换\''),(9,'5.0','2013-01-10 15:37:00','admin','save transformation \'测试用的转换\''),(10,'5.0','2013-01-10 16:55:17','admin','save transformation \'测试用的转换\''),(11,'5.0','2013-01-10 16:55:44','admin','save transformation \'测试用的转换\''),(12,'5.0','2013-01-10 17:00:51','admin','save transformation \'测试用的转换\''),(13,'5.0','2013-01-10 17:01:15','admin','save transformation \'测试用的转换\''),(14,'5.0','2013-01-10 17:01:43','admin','save transformation \'测试用的转换\''),(15,'5.0','2013-01-10 17:01:50','admin','save transformation \'测试用的转换\''),(16,'5.0','2013-01-10 21:26:39','admin','save transformation \'测试用的转换\''),(17,'5.0','2013-01-10 21:49:17','admin','save transformation \'测试用的转换\''),(18,'5.0','2013-01-10 21:49:59','admin','save transformation \'测试用的转换\''),(19,'5.0','2013-01-10 23:14:15','admin','save transformation \'测试用的转换\''),(20,'5.0','2013-01-10 23:17:46','admin','save transformation \'测试用的转换\''),(21,'5.0','2013-01-10 23:35:49','admin','save transformation \'测试用的转换\''),(22,'5.0','2013-01-10 23:36:08','admin','save transformation \'测试用的转换\''),(23,'5.0','2013-01-10 23:37:40','admin','save transformation \'转换 1\''),(24,'5.0','2013-01-10 23:38:10','admin','save transformation \'转换 1\''),(25,'5.0','2013-01-10 23:40:24','admin','save transformation \'转换 1\''),(26,'5.0','2013-01-10 23:41:14','admin','save transformation \'测试用的转换\''),(27,'5.0','2013-01-10 23:41:32','admin','save transformation \'转换 1\''),(28,'5.0','2013-01-10 23:41:59','admin','save transformation \'测试用的转换\''),(29,'5.0','2013-01-10 23:43:08','admin','save transformation \'测试用的转换\''),(30,'5.0','2013-01-10 23:44:54','admin','save transformation \'测试用的转换\''),(31,'5.0','2013-01-10 23:58:20','admin','save transformation \'测试用的转换\''),(32,'5.0','2013-01-11 07:13:45','admin','save transformation \'测试用的转换\''),(33,'5.0','2013-01-11 07:18:07','admin','save transformation \'测试用的转换\''),(34,'5.0','2013-01-11 07:18:18','admin','save transformation \'测试用的转换\''),(35,'5.0','2013-01-11 07:20:31','admin','save transformation \'测试用的转换\''),(36,'5.0','2013-01-11 09:22:06','admin','save transformation \'测试用的转换\''),(37,'5.0','2013-01-11 09:22:44','admin','save transformation \'测试用的转换\''),(38,'5.0','2013-01-11 09:22:59','admin','save transformation \'测试用的转换\''),(39,'5.0','2013-01-11 09:23:12','admin','save transformation \'测试用的转换\''),(40,'5.0','2013-01-11 09:23:44','admin','save transformation \'测试用的转换\''),(41,'5.0','2013-01-11 13:44:15','admin','save transformation \'测试用的转换\''),(42,'5.0','2013-01-11 13:44:22','admin','save transformation \'测试用的转换\''),(43,'5.0','2013-01-12 06:32:13','admin','save transformation \'测试用的转换\''),(44,'5.0','2013-01-12 06:33:08','admin','save transformation \'测试用的转换\''),(45,'5.0','2013-01-20 09:27:22','admin','save transformation \'测试用的转换\''),(46,'5.0','2013-01-20 09:28:01','admin','save transformation \'测试用的转换\'');
/*!40000 ALTER TABLE `r_repository_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_role`
--

DROP TABLE IF EXISTS `r_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_role` (
  `id_role` bigint(20) NOT NULL,
  `role_name` varchar(64) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_role`
--

LOCK TABLES `r_role` WRITE;
/*!40000 ALTER TABLE `r_role` DISABLE KEYS */;
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
INSERT INTO `r_step` VALUES (10,2,'XBase输入',NULL,74,'Y',1,312,104,'Y'),(9,2,'Access 输入',NULL,1,'Y',1,410,104,'Y'),(12,1,'XBase输入',NULL,74,'Y',1,269,120,'Y'),(11,1,'Access 输入',NULL,1,'Y',1,156,268,'Y');
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
INSERT INTO `r_step_attribute` VALUES (214,2,10,0,'loadbalance',0,'N'),(213,2,10,0,'cluster_schema',0,NULL),(212,2,10,0,'accept_stepname',0,NULL),(211,2,10,0,'accept_field',0,NULL),(210,2,10,0,'accept_filenames',0,'N'),(209,2,10,0,'charset_name',0,NULL),(208,2,10,0,'include_field',0,NULL),(207,2,10,0,'include',0,'N'),(206,2,10,0,'field_rownr',0,NULL),(205,2,10,0,'add_rownr',0,'N'),(204,2,10,0,'limit',0,NULL),(203,2,10,0,'file_dbf',0,NULL),(202,2,10,0,'PARTITIONING_METHOD',0,'none'),(201,2,10,0,'PARTITIONING_SCHEMA',0,NULL),(200,2,9,0,'loadbalance',0,'N'),(199,2,9,0,'cluster_schema',0,NULL),(198,2,9,0,'extensionFieldName',0,NULL),(197,2,9,0,'rootUriNameFieldName',0,NULL),(196,2,9,0,'uriNameFieldName',0,NULL),(195,2,9,0,'lastModificationTimeFieldName',0,NULL),(194,2,9,0,'hiddenFieldName',0,NULL),(193,2,9,0,'pathFieldName',0,NULL),(192,2,9,0,'shortFileFieldName',0,NULL),(191,2,9,0,'reset_rownumber',0,'N'),(190,2,9,0,'table_name',0,NULL),(189,2,9,0,'limit',0,NULL),(188,2,9,0,'rownum_field',0,NULL),(187,2,9,0,'filename_Field',0,NULL),(186,2,9,0,'filefield',0,'N'),(185,2,9,0,'isaddresult',0,'Y'),(184,2,9,0,'rownum',0,'N'),(183,2,9,0,'tablename_field',0,NULL),(182,2,9,0,'tablename',0,'N'),(181,2,9,0,'include_field',0,NULL),(180,2,9,0,'include',0,'N'),(179,2,9,0,'PARTITIONING_METHOD',0,'none'),(178,2,9,0,'PARTITIONING_SCHEMA',0,NULL),(251,1,12,0,'loadbalance',0,'N'),(250,1,12,0,'cluster_schema',0,NULL),(249,1,12,0,'accept_stepname',0,NULL),(248,1,12,0,'accept_field',0,NULL),(247,1,12,0,'accept_filenames',0,'N'),(246,1,12,0,'charset_name',0,NULL),(245,1,12,0,'include_field',0,NULL),(244,1,12,0,'include',0,'N'),(243,1,12,0,'field_rownr',0,NULL),(242,1,12,0,'add_rownr',0,'N'),(241,1,12,0,'limit',0,NULL),(240,1,12,0,'file_dbf',0,NULL),(239,1,12,0,'PARTITIONING_METHOD',0,'none'),(238,1,12,0,'PARTITIONING_SCHEMA',0,NULL),(237,1,11,0,'loadbalance',0,'N'),(235,1,11,0,'extensionFieldName',0,NULL),(236,1,11,0,'cluster_schema',0,NULL),(234,1,11,0,'rootUriNameFieldName',0,NULL),(233,1,11,0,'uriNameFieldName',0,NULL),(232,1,11,0,'lastModificationTimeFieldName',0,NULL),(229,1,11,0,'shortFileFieldName',0,NULL),(231,1,11,0,'hiddenFieldName',0,NULL),(230,1,11,0,'pathFieldName',0,NULL),(228,1,11,0,'reset_rownumber',0,'N'),(227,1,11,0,'table_name',0,NULL),(226,1,11,0,'limit',0,NULL),(225,1,11,0,'rownum_field',0,NULL),(224,1,11,0,'filename_Field',0,NULL),(223,1,11,0,'filefield',0,'N'),(222,1,11,0,'isaddresult',0,'Y'),(221,1,11,0,'rownum',0,'N'),(220,1,11,0,'tablename_field',0,NULL),(219,1,11,0,'tablename',0,'N'),(218,1,11,0,'include_field',0,NULL),(217,1,11,0,'include',0,'N'),(216,1,11,0,'PARTITIONING_METHOD',0,'none'),(215,1,11,0,'PARTITIONING_SCHEMA',0,NULL);
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
INSERT INTO `r_step_type` VALUES (1,'AccessInput','Access 输入','Read data from a Microsoft Access file'),(2,'AccessOutput','Access 输出','Stores records into an MS-Access database table.'),(3,'BIRTOutput','BIRT Output','Executes a BIRT Report Design (.rptdesign)'),(4,'ClosureGenerator','Closure Generator','This step allows you to generates a closure table using parent-child relationships.'),(5,'CsvInput','CSV文件输入','Simple CSV file input'),(6,'CubeInput','Cube 文件输入','从一个cube读取记录.'),(7,'CubeOutput','Cube输出','把数据写入一个cube'),(8,'TypeExitEdi2XmlStep','Edi to XML','Converts Edi text to generic XML'),(9,'ElasticSearchBulk','ElasticSearch 批量加载','Performs bulk inserts into ElasticSearch'),(10,'ShapeFileReader','ESRI Shapefile Reader','Reads shape file data from an ESRI shape file and linked DBF file'),(11,'MetaInject','ETL元数据注入','This step allows you to inject metadata into an existing transformation prior to execution.  This allows for the creation of dynamic and highly flexible data integration solutions.'),(12,'DummyPlugin','Example plugin','This is an example for a plugin test step'),(13,'ExcelInput','Excel输入','从一个微软的Excel文件里读取数据. 兼容Excel 95, 97 and 2000.'),(14,'ExcelOutput','Excel输出','Stores records into an Excel (XLS) document with formatting information.'),(15,'GetSlaveSequence','Get ID from slave server','Retrieves unique IDs in blocks from a slave server.  The referenced sequence needs to be configured on the slave server in the XML configuration file.'),(16,'TypeExitGoogleAnalyticsInputStep','Google Analytics 输入','Fetches data from google analytics account'),(17,'GPLoad','Greenplum Load','Greenplum Load'),(18,'GPBulkLoader','Greenplum 批量加载','Greenplum Bulk Loader'),(19,'ParallelGzipCsvInput','GZIP CSV Input','Parallel GZIP CSV file input reader'),(20,'HL7Input','HL7 Input','Reads and parses HL7 messages and outputs a series of values from the messages'),(21,'HTTP','HTTP client','Call a web service over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(22,'HTTPPOST','HTTP Post','Call a web service request over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(23,'InfobrightOutput','Infobright 批量加载','Load data to an Infobright database table'),(24,'VectorWiseBulkLoader','Ingres VectorWise 批量加载','This step interfaces with the Ingres VectorWise Bulk Loader \"COPY TABLE\" command.'),(25,'UserDefinedJavaClass','Java 代码','This step allows you to program a step using Java code'),(26,'ScriptValueMod','JavaScript代码','This is a modified plugin for the Scripting Values with improved interface and performance.\nWritten & donated to open source by Martin Lange, Proconis : http://www.proconis.de'),(27,'JsonInput','Json 输入','Extract relevant portions out of JSON structures (file or incoming field) and output rows'),(28,'JsonOutput','Json 输出','Create Json bloc and output it in a field ou a file.'),(29,'LDAPInput','LDAP 输入','Read data from LDAP host'),(30,'LDAPOutput','LDAP 输出','Perform Insert, upsert, update, add or delete operations on records based on their DN (Distinguished  Name).'),(31,'LDIFInput','LDIF 输入','Read data from LDIF files'),(32,'LucidDBBulkLoader','LucidDB 批量加载','Load data into LucidDB by using their bulk load command in streaming mode. (Doesnt work on Windows!)'),(33,'LucidDBStreamingLoader','LucidDB 流加载','Load data into LucidDB by using Remote Rows UDX.'),(34,'TypeExitExcelWriterStep','Microsoft Excel 输出','Writes or appends data to an Excel file'),(35,'MondrianInput','Mondrian 输入','Execute and retrieve data using an MDX query against a Pentaho Analyses OLAP server (Mondrian)'),(36,'MonetDBBulkLoader','MonetDB 批量加载','Load data into MonetDB by using their bulk load command in streaming mode.'),(37,'MultiwayMergeJoin','Multiway Merge Join','Multiway Merge Join'),(38,'MySQLBulkLoader','MySQL 批量加载','MySQL bulk loader step, loading data over a named pipe (not available on MS Windows)'),(39,'OlapInput','OLAP 输入','Execute and retrieve data using an MDX query against any XML/A OLAP datasource using olap4j'),(40,'OpenERPObjectDelete','OpenERP Object Delete','Deletes OpenERP objects'),(41,'OpenERPObjectInput','OpenERP Object Input','Reads data from OpenERP objects'),(42,'OpenERPObjectOutputImport','OpenERP Object Output','Writes data into OpenERP objects using the object import procedure'),(43,'OraBulkLoader','Oracle 批量加载','Use Oracle Bulk Loader to load data'),(44,'PaloCellInput','Palo Cell Input','Reads data from a defined Palo Cube '),(45,'PaloCellOutput','Palo Cell Output','Writes data to a defined Palo Cube'),(46,'PaloDimInput','Palo Dim Input','Reads data from a defined Palo Dimension'),(47,'PaloDimOutput','Palo Dim Output','Writes data to defined Palo Dimension'),(48,'PentahoReportingOutput','Pentaho 报表输出','Executes an existing report (PRPT)'),(49,'PGPDecryptStream','PGP Decrypt stream','Decrypt data stream with PGP'),(50,'PGPEncryptStream','PGP Encrypt stream','Encrypt data stream with PGP'),(51,'PGBulkLoader','PostgreSQL 批量加载','PostgreSQL Bulk Loader'),(52,'Rest','REST Client','Consume RESTfull services.\nREpresentational State Transfer (REST) is a key design idiom that embraces a stateless client-server\narchitecture in which the web services are viewed as resources and can be identified by their URLs'),(53,'RssInput','RSS 输入','Read RSS feeds'),(54,'RssOutput','RSS 输出','Read RSS stream.'),(55,'RuleAccumulator','Rule Accumulator','Execute a rule against a set of all incoming rows'),(56,'RuleExecutor','Rule Executor','Execute a rule against each row passing through'),(57,'S3CSVINPUT','S3 CSV 输入','S3 CSV 输入'),(58,'SalesforceUpsert','Salesforce Upsert','Insert or update records in Salesforce module.'),(59,'SalesforceDelete','Salesforce 删除','Delete records in Salesforce module.'),(60,'SalesforceInsert','Salesforce 插入','Insert records in Salesforce module.'),(61,'SalesforceUpdate','Salesforce 更新','Update records in Salesforce module.'),(62,'SalesforceInput','Salesforce 输入','!BaseStep.TypeTooltipDesc.SalesforceInput!'),(63,'SapInput','SAP 输入','Read data from SAP ERP, optionally with parameters'),(64,'SASInput','SAS 输入','This step reads files in sas7bdat (SAS) native format'),(65,'SFTPPut','SFTP Put','Upload a file or a stream file to remote host via SFTP'),(66,'SingleThreader','Single Threader','Executes a transformation snippet in a single thread.  You need a standard mapping or a transformation with an Injector step where data from the parent transformation will arive in blocks.'),(67,'SocketWriter','Socket 写','Socket writer.  A socket server that can send rows of data to a socket reader.'),(68,'SocketReader','Socket 读','Socket reader.  A socket client that connects to a server (Socket Writer step).'),(69,'SQLFileOutput','SQL 文件输出','Output SQL INSERT statements to file'),(70,'SwitchCase','Switch / Case','Switch a row to a certain target step based on the case value in a field.'),(71,'TeraFast','Teradata Fastload 批量加载','The Teradata Fastload Bulk loader'),(72,'WebServiceLookup','Web 服务查询','使用 Web 服务查询信息'),(73,'WMIInput','WMI 输入','Query WMI repository (Management Instrumentation) for class and instance information.\nThis step runs the WMI Query Language (WQL) which is a subset of ANSI SQL.>>>>>>> .r15867'),(74,'XBaseInput','XBase输入','从一个XBase类型的文件(DBF)读取记录'),(75,'getXMLData','XML 文件输入','Get data from XML file by using XPath.\n This step also allows you to parse XML defined in a previous field.'),(76,'XMLInputStream','XML 文件输入 (StAX解析)','This step is capable of processing very large and complex XML files very fast.'),(77,'XMLInputSax','XML 流输入','Read data from an XML file in a streaming fashing, working faster and consuming less memory'),(78,'XMLJoin','XML 连接','Joins a stream of XML-Tags into a target XML string'),(79,'XMLInput','XML输入','从一个XML读取数据'),(80,'XMLOutput','XML输出','写数据到一个XML文件'),(81,'XSLT','XSL 转换','Transform XML stream using XSL (eXtensible Stylesheet Language).'),(82,'YamlInput','Yaml 输入','Read YAML source (file or stream) parse them and convert them to rows and writes these to one or more output. '),(83,'ZipFile','Zip 文件','Zip a file.\nFilename will be extracted from incoming stream.'),(84,'Abort','中止','Abort a transformation'),(85,'FilesFromResult','从结果获取文件','This step allows you to read filenames used or generated in a previous entry in a job.'),(86,'RowsFromResult','从结果获取记录','这个允许你从同一个任务的前一个条目里读取记录.'),(87,'XSDValidator','使用 XSD 检验 XML 文件','Validate XML source (files or streams) against XML Schema Definition.'),(88,'ValueMapper','值映射','Maps values of a certain field from one value to another'),(89,'CloneRow','克隆行','Clone a row as many times as needed'),(90,'Formula','公式','使用 Pentaho 的公式库来计算公式'),(91,'WriteToLog','写日志','Write data to log'),(92,'AnalyticQuery','分析查询','Execute analytic queries over a sorted dataset (LEAD/LAG/FIRST/LAST)'),(93,'GroupBy','分组','以分组的形式创建聚合.{0}这个仅仅在一个已经排好序的输入有效.{1}如果输入没有排序, 仅仅两个连续的记录行被正确处理.'),(94,'SplitFieldToRows3','列拆分为多行','Splits a single string field by delimiter and creates a new row for each split term'),(95,'Denormaliser','列转行','Denormalises rows by looking up key-value pairs and by assigning them to new fields in the输出 rows.{0}This method aggregates and needs the输入 rows to be sorted on the grouping fields'),(96,'Delete','删除','基于关键字删除记录'),(97,'Janino','利用Janino计算Java表达式','Calculate the result of a Java Expression using Janino'),(98,'StringCut','剪切字符串','Strings cut (substring).'),(99,'UnivariateStats','单变量统计','This step computes some simple stats based on a single input field'),(100,'Unique','去除重复记录','去除重复的记录行，保持记录唯一{0}这个仅仅基于一个已经排好序的输入.{1}如果输入没有排序, 仅仅两个连续的记录行被正确处理.'),(101,'SyslogMessage','发送信息至Syslog','Send message to Syslog server'),(102,'Mail','发送邮件','Send eMail.'),(103,'MergeRows','合并记录','合并两个数据流, 并根据某个关键字排序.  这两个数据流被比较，以标识相等的、变更的、删除的和新建的记录.'),(104,'ExecProcess','启动一个进程','Execute a process and return the result'),(105,'UniqueRowsByHashSet','唯一行 (哈希值)','Remove double rows and leave only unique occurrences by using a HashSet.'),(106,'FixedInput','固定宽度文件输入','Fixed file input'),(107,'MemoryGroupBy','在内存中分组','Builds aggregates in a group by fashion.\nThis step doesn\'t require sorted input.'),(108,'AddXML','增加XML列','Encode several fields into an XML fragment'),(109,'Constant','增加常量','给记录增加一到多个常量'),(110,'Sequence','增加序列','从序列获取下一个值'),(111,'CheckSum','增加校验列','Add a checksum column for each input row'),(112,'ProcessFiles','处理文件','Process one file per row (copy or move or delete).\nThis step only accept filename in input.'),(113,'FilesToResult','复制文件到结果','This step allows you to set filenames in the result of this transformation.\nSubsequent job entries can then use this information.'),(114,'RowsToResult','复制记录到结果','使用这个步骤把记录写到正在执行的任务.{0}信息将会被传递给同一个任务里的下一个条目.'),(115,'SelectValues','字段选择','选择或移除记录里的字。{0}此外，可以设置字段的元数据: 类型, 长度和精度.'),(116,'StringOperations','字符串操作','Apply certain operations like trimming, padding and others to string value.'),(117,'ReplaceString','字符串替换','Replace all occurences a word in a string with another word.'),(118,'SymmetricCryptoTrans','对称加密','Encrypt or decrypt a string using symmetric encryption.\nAvailable algorithms are DES, AEC, TripleDES.'),(119,'SetValueConstant','将字段值设置为常量','Set value of a field to a constant'),(120,'Delay','延迟行','Output each input row after a delay'),(121,'DynamicSQLRow','执行Dynamic SQL','Execute dynamic SQL statement build in a previous field'),(122,'ExecSQL','执行SQL脚本','执行一个SQL脚本, 另外，可以使用输入的记录作为参数'),(123,'ExecSQLRow','执行SQL脚本(字段流替换)','Execute SQL script extracted from a field\ncreated in a previous step.'),(124,'JobExecutor','执行作业','This step executes a Pentaho Data Integration job, sets parameters and passes rows.'),(125,'FieldSplitter','拆分字段','当你想把一个字段拆分成多个时，使用这个类型.'),(126,'SortedMerge','排序合并','Sorted Merge'),(127,'SortRows','排序记录','基于字段值把记录排序(升序或降序)'),(128,'InsertUpdate','插入 / 更新','基于关键字更新或插入记录到数据库.'),(129,'ChangeFileEncoding','改变文件编码','Change file encoding and create a new file'),(130,'NumberRange','数值范围','Create ranges based on numeric field'),(131,'SynchronizeAfterMerge','数据同步','This step perform insert/update/delete in one go based on the value of a field. '),(132,'DBLookup','数据库查询','使用字段值在数据库里查询值'),(133,'DBJoin','数据库连接','使用数据流里的值作为参数执行一个数据库查询'),(134,'Validator','数据检验','Validates passing data based on a set of rules'),(135,'PrioritizeStreams','数据流优先级排序','Prioritize streams in an order way.'),(136,'ReservoirSampling','数据采样','[Transform] Samples a fixed number of rows from the incoming stream'),(137,'LoadFileInput','文件内容加载至内存','Load file content in memory'),(138,'TextFileInput','文本文件输入','从一个文本文件（几种格式）里读取数据{0}这些数据可以被传递到下一个步骤里...'),(139,'TextFileOutput','文本文件输出','写记录到一个文本文件.'),(140,'Mapping','映射 (子转换)','运行一个映射 (子转换), 使用MappingInput和MappingOutput来指定接口的字段'),(141,'MappingInput','映射输入规范','指定一个映射的字段输入'),(142,'MappingOutput','映射输出规范','指定一个映射的字段输出'),(143,'Update','更新','基于关键字更新记录到数据库'),(144,'IfNull','替换NULL值','Sets a field value to a constant if it is null.'),(145,'SampleRows','样本行','Filter rows based on the line number.'),(146,'JavaFilter','根据Java代码过滤记录','Filter rows using java code'),(147,'FieldsChangeSequence','根据字段值来改变序列','Add sequence depending of fields value change.\nEach time value of at least one field change, PDI will reset sequence. '),(148,'WebServiceAvailable','检查web服务是否可用','Check if a webservice is available'),(149,'FileExists','检查文件是否存在','Check if a file exists'),(150,'FileLocked','检查文件是否已被锁定','Check if a file is locked by another process'),(151,'TableExists','检查表是否存在','Check if a table exists on a specified connection'),(152,'ColumnExists','检查表里的列是否存在','Check if a column exists in a table on a specified connection.'),(153,'DetectEmptyStream','检测空流','This step will output one empty row if input stream is empty\n(ie when input stream does not contain any row)'),(154,'CreditCardValidator','检验信用卡号码是否有效','The Credit card validator step will help you tell:\n(1) if a credit card number is valid (uses LUHN10 (MOD-10) algorithm)\n(2) which credit card vendor handles that number\n(VISA, MasterCard, Diners Club, EnRoute, American Express (AMEX),...)'),(155,'MailValidator','检验邮件地址','Check if an email address is valid.'),(156,'FuzzyMatch','模糊匹配','Finding approximate matches to a string using matching algorithms.\nRead a field from a main stream and output approximative value from lookup stream.'),(157,'RegexEval','正则表达式','Regular expression Evaluation\nThis step uses a regular expression to evaluate a field. It can also extract new fields out of an existing field with capturing groups.'),(158,'TableCompare','比较表','Compares 2 tables and gives back a list of differences'),(159,'StreamLookup','流查询','从转换中的其它流里查询值.'),(160,'StepMetastructure','流的元数据','This is a step to read the metadata of the incoming stream.'),(161,'SecretKeyGenerator','生成密钥','Generate secrete key for algorithms such as DES, AEC, TripleDES.'),(162,'RowGenerator','生成记录','产生一些空记录或相等的行.'),(163,'RandomValue','生成随机数','Generate random value'),(164,'RandomCCNumberGenerator','生成随机的信用卡号','Generate random valide (luhn check) credit card numbers'),(165,'Dummy','空操作 (什么也不做)','这个步骤类型什么都不作.{0} 当你想测试或拆分数据流的时候有用.'),(166,'DimensionLookup','维度查询/更新','在一个数据仓库里更新一个渐变维 {0} 或者在这个维里查询信息.'),(167,'CombinationLookup','联合查询/更新','更新数据仓库里的一个junk维 {0} 可选的, 科研查询维里的信息.{1}junk维的主键是所有的字段.'),(168,'AggregateRows','聚合记录','这个步骤类型允许你聚合记录.{0}它不能使用在分组的情况.'),(169,'AutoDoc','自动文档输出','This step automatically generates documentation based on input in the form of a list of transformations and jobs'),(170,'DataGrid','自定义常量数据','Enter rows of static data in a grid, usually for testing, reference or demo purpose'),(171,'GetPreviousRowField','获取上一次的记录','Get fields value of previous row.'),(172,'GetVariable','获取变量','Determine the values of certain (environment or Kettle) variables and put them in field values.'),(173,'GetSubFolders','获取子目录名','Read a parent folder and return all subfolders'),(174,'GetFileNames','获取文件名','Get file names from the operating system and send them to the next step.'),(175,'GetFilesRowsCount','获取文件行数','Returns rows count for text files.'),(176,'SystemInfo','获取系统信息','获取系统信息，例如时间、日期.'),(177,'GetTableNames','获取表名','Get table names from database connection and send them to the next step'),(178,'GetRepositoryNames','获取资源库配置','Lists detailed information about transformations and/or jobs in a repository'),(179,'Flattener','行扁平化','Flattens consequetive rows based on the order in which they appear in the输入 stream'),(180,'Normaliser','行转列','De-normalised information can be normalised using this step type.'),(181,'TableInput','表输入','从数据库表里读取信息.'),(182,'TableOutput','表输出','写信息到一个数据库表'),(183,'Calculator','计算器','通过执行简单的计算创建一个新字段'),(184,'JoinRows','记录关联 (笛卡尔输出)','这个步骤的输出是输入流的笛卡尔的结果.{0} 输出结果的记录数是输入流记录之间的乘积.'),(185,'Injector','记录注射','Injector step to allow to inject rows into the transformation through the java API'),(186,'MergeJoin','记录集连接','Joins two streams on a given key and outputs a joined set. The input streams must be sorted on the join key'),(187,'NullIf','设置值为NULL','如果一个字段值等于某个固定值，那么把这个字段值设置成null'),(188,'SetVariable','设置变量','Set environment variables based on a single input row.'),(189,'SetValueField','设置字段值','Set value of a field with another value field'),(190,'DetectLastRow','识别流的最后一行','Last row will be marked'),(191,'DBProc','调用DB存储过程','通过调用数据库存储过程获得返回值.'),(192,'StepsMetrics','转换步骤信息统计','Return metrics for one or several steps'),(193,'FilterRows','过滤记录','使用简单的相等来过滤记录'),(194,'SSH','运行SSH命令','Run SSH commands and returns result.'),(195,'Append','追加流','Append 2 streams in an ordered way'),(196,'MailInput','邮件信息输入','Read POP3/IMAP server and retrieve messages'),(197,'PropertyInput','配置文件输入','Read data (key, value) from properties files.'),(198,'PropertyOutput','配置文件输出','Write data to properties file'),(199,'BlockingStep','阻塞数据','This step blocks until all incoming rows have been processed.  Subsequent steps only recieve the last input row to this step.'),(200,'BlockUntilStepsFinish','阻塞数据直到步骤都完成','Block this step until selected steps finish.'),(201,'ReportFileOutput','报表文件输出','通过报表设计器设计一个报表样式和设定参数.');
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
INSERT INTO `r_trans_attribute` VALUES (1314,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1313,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(1533,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1532,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(1530,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1531,1,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(1529,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(1528,1,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(1527,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1526,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(1525,1,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(1524,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1522,1,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(1523,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(1521,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1520,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(1519,1,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(1518,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1517,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(1516,1,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(1515,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1514,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(1513,1,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(1512,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1511,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(1510,1,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(1509,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1508,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(1507,1,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(1506,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1505,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(1504,1,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(1503,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1502,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1501,1,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1500,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1499,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1498,1,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1497,1,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1496,1,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(1494,1,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(1495,1,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(1493,1,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(1492,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(1491,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(1490,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(1489,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(1488,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(1487,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(1486,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1482,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(1478,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(1479,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(1485,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1484,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1483,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1481,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(1480,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1477,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1476,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(1475,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(1474,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1473,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(1472,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(1471,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1470,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(1469,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(1468,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1467,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(1466,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(1465,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1464,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(1463,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(1462,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1461,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(1460,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(1459,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1458,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(1457,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(1456,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1455,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(1454,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(1453,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1452,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(1451,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(1450,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1449,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1448,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1447,1,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1446,1,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(1445,1,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(1444,1,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(1443,1,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(1442,1,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(1441,1,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(1440,1,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1438,1,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1439,1,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1437,1,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1436,1,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(1435,1,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(1434,1,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1433,1,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(1432,1,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(1431,1,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1430,1,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(1429,1,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(1428,1,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1427,1,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(1426,1,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(1425,1,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1424,1,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(1423,1,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(1422,1,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1421,1,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(1420,1,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(1419,1,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1418,1,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(1417,1,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(1416,1,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1415,1,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(1414,1,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(1413,1,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1412,1,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(1411,1,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(1410,1,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1409,1,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(1408,1,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(1407,1,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1406,1,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1405,1,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1404,1,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1403,1,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1402,1,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1401,1,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1400,1,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(1399,1,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(1398,1,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(1397,1,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(1396,1,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(1395,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(1394,1,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(1393,1,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(1392,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(1391,1,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(1390,1,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(1389,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'Y'),(1388,1,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(1387,1,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(1386,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(1385,1,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(1384,1,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(1383,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(1382,1,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(1381,1,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(1380,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(1379,1,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(1378,1,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(1377,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1376,1,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(1375,1,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(1374,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1373,1,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(1372,1,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(1371,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1370,1,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(1369,1,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(1367,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1368,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(1366,1,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(1365,1,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(1362,1,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(1363,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1364,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(1361,1,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(1360,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(1359,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1358,1,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(1357,1,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(1356,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(1355,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1354,1,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(1353,1,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(1352,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(1351,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1350,1,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(1349,1,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(1348,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(1347,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1346,1,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(1345,1,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(1344,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1343,1,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(1342,1,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(1339,1,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(1340,1,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(1341,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1338,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1337,1,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1336,1,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1335,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1334,1,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1333,1,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1332,1,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1331,1,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(1312,2,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(1311,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1310,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(1308,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1309,2,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(1307,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(1306,2,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(1303,2,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(1304,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(1305,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1300,2,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(1301,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(1302,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1298,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(1299,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1297,2,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(1296,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1295,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(1294,2,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(1293,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1292,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(1291,2,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(1290,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1289,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(1288,2,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(1287,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1286,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(1285,2,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(1284,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1283,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1280,2,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1281,2,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1282,2,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1279,2,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1276,2,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(1277,2,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(1278,2,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1274,2,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(1275,2,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(1273,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(1272,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(1269,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(1270,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(1271,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(1266,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1267,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1268,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(1264,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1265,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1262,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(1263,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(1261,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1259,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(1260,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(1258,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1257,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(1256,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(1254,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(1255,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1251,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(1253,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(1252,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1248,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(1249,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1250,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(1246,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1247,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(1245,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(1242,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(1243,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1244,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(1241,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(1239,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(1240,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1238,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(1235,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(1236,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(1237,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1233,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(1234,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1232,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(1230,2,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1231,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1227,2,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(1228,2,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1229,2,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1225,2,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(1226,2,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(1224,2,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(1223,2,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(1222,2,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(1221,2,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1220,2,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(1218,2,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1219,2,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(1216,2,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(1217,2,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(1215,2,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1214,2,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(1213,2,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(1212,2,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1211,2,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(1210,2,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(1209,2,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1208,2,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(1207,2,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(1206,2,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1205,2,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(1204,2,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(1203,2,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1202,2,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(1201,2,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(1200,2,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1199,2,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(1198,2,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(1197,2,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1196,2,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(1195,2,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(1194,2,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1193,2,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(1192,2,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(1191,2,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1190,2,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(1189,2,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(1188,2,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1187,2,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1186,2,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1185,2,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1184,2,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1183,2,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1182,2,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1181,2,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(1178,2,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(1179,2,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(1180,2,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(1174,2,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(1175,2,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(1177,2,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(1176,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(1173,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(1171,2,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(1172,2,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(1170,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'Y'),(1169,2,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(1168,2,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(1167,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(1165,2,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(1166,2,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(1164,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(1163,2,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(1162,2,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(1161,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(1160,2,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(1159,2,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(1158,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(1157,2,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(1156,2,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(1154,2,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(1155,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(1153,2,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(1152,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(1151,2,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(1150,2,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(1149,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(1148,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(1146,2,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(1147,2,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(1330,1,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(1328,1,0,'SLEEP_TIME_FULL',50,NULL),(1329,1,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(1327,1,0,'SLEEP_TIME_EMPTY',50,NULL),(1326,1,0,'TRANSFORMATION_TYPE',0,'Normal'),(1325,1,0,'LOG_INTERVAL',0,NULL),(1324,1,0,'LOG_SIZE_LIMIT',0,NULL),(1323,1,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(1322,1,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,'100'),(1321,1,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(1320,1,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(1319,1,0,'SHARED_FILE',0,NULL),(1318,1,0,'USING_THREAD_PRIORITIES',0,'Y'),(1317,1,0,'FEEDBACK_SIZE',50000,NULL),(1145,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(1144,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(1143,2,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(1142,2,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(1141,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(1140,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(1139,2,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(1138,2,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(1136,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(1137,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(1134,2,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(1135,2,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(1133,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(1131,2,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(1132,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(1130,2,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(1129,2,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(1128,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(1127,2,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(1126,2,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(1125,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(1124,2,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(1123,2,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(1122,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(1121,2,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(1120,2,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(1119,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(1118,2,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(1117,2,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(1116,2,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(1115,2,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(1114,2,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(1113,2,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(1112,2,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(1111,2,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(1110,2,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(1109,2,0,'SLEEP_TIME_FULL',50,NULL),(1108,2,0,'SLEEP_TIME_EMPTY',50,NULL),(1107,2,0,'TRANSFORMATION_TYPE',0,'Normal'),(1106,2,0,'LOG_INTERVAL',0,NULL),(1105,2,0,'LOG_SIZE_LIMIT',0,NULL),(1104,2,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(1102,2,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(1103,2,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,'100'),(1101,2,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(1100,2,0,'SHARED_FILE',0,NULL),(1099,2,0,'USING_THREAD_PRIORITIES',0,'Y'),(1098,2,0,'FEEDBACK_SIZE',50000,NULL),(1097,2,0,'FEEDBACK_SHOWN',0,'Y'),(1096,2,0,'UNIQUE_CONNECTIONS',0,'N'),(1316,1,0,'FEEDBACK_SHOWN',0,'Y'),(1315,1,0,'UNIQUE_CONNECTIONS',0,'N');
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
INSERT INTO `r_trans_hop` VALUES (4,2,9,10,'Y'),(5,1,11,12,'Y');
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
INSERT INTO `r_transformation` VALUES (2,10,'转换 1',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','Y',-1,NULL,NULL,0,0,'-','2012-11-21 11:31:56','admin','2012-11-21 13:28:47',10000),(1,0,'测试用的转换',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','Y',-1,NULL,NULL,0,0,'-','2012-10-27 14:01:42','admin','2012-12-06 14:37:48',10000);
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
INSERT INTO `r_user` VALUES (1,'admin','2be98afc86aa7f2e4cb79ce71da9fa6d4','Administrator','User manager','Y'),(2,'guest','2be98afc86aa7f2e4cb79ce77cb97bcce','Guest account','Read-only guest account','Y');
/*!40000 ALTER TABLE `r_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_role`
--

DROP TABLE IF EXISTS `r_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_user_role` (
  `rid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_role`
--

LOCK TABLES `r_user_role` WRITE;
/*!40000 ALTER TABLE `r_user_role` DISABLE KEYS */;
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

-- Dump completed on 2013-06-03  6:44:49
