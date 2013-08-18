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
INSERT INTO `r_condition` VALUES (1,NULL,'N','-      ',NULL,'=',NULL,NULL);
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
INSERT INTO `r_database` VALUES (1,'imeta',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL),(2,'imeta1',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL),(3,'imeta11',28,1,'localhost','imeta',3306,'root','Encrypted 2be98afc86aa7f2e4cb79ce10cc9da0ce',NULL,NULL,NULL);
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
INSERT INTO `r_database_attribute` VALUES (155,1,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(156,1,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(157,1,'PREFERRED_SCHEMA_NAME',NULL),(158,1,'QUOTE_ALL_FIELDS','false'),(159,1,'SQL_CONNECT',NULL),(153,1,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(151,1,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(152,1,'STREAM_RESULTS','N'),(148,1,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(149,1,'USE_POOLING','N'),(59,2,'QUOTE_ALL_FIELDS','false'),(57,2,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(58,2,'PREFERRED_SCHEMA_NAME',NULL),(56,2,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(55,2,'PORT_NUMBER','3306'),(54,2,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(52,2,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(150,1,'IS_CLUSTERED','N'),(81,3,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(80,3,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(79,3,'PORT_NUMBER','3306'),(78,3,'EXTRA_OPTION_MYSQL.defaultFetchSize',NULL),(77,3,'STREAM_RESULTS','N'),(76,3,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(75,3,'IS_CLUSTERED','N'),(74,3,'USE_POOLING','N'),(73,3,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(53,2,'STREAM_RESULTS','N'),(51,2,'IS_CLUSTERED','N'),(50,2,'USE_POOLING','N'),(49,2,'EXTRA_OPTION_MYSQL.useCursorFetch',NULL),(60,2,'SQL_CONNECT',NULL),(154,1,'PORT_NUMBER','3306'),(82,3,'PREFERRED_SCHEMA_NAME',NULL),(83,3,'QUOTE_ALL_FIELDS','false'),(84,3,'SQL_CONNECT',NULL),(103,4,'USE_POOLING','N'),(104,4,'IS_CLUSTERED','N'),(105,4,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(106,4,'PORT_NUMBER','1521'),(107,4,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(108,4,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(112,5,'USE_POOLING','N'),(113,5,'IS_CLUSTERED','N'),(114,5,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(115,5,'PORT_NUMBER','1521'),(116,5,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(117,5,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(109,4,'PREFERRED_SCHEMA_NAME',NULL),(110,4,'QUOTE_ALL_FIELDS','false'),(111,4,'SQL_CONNECT',NULL),(118,5,'PREFERRED_SCHEMA_NAME',NULL),(119,5,'QUOTE_ALL_FIELDS','false'),(120,5,'SQL_CONNECT',NULL),(121,6,'USE_POOLING','N'),(122,6,'IS_CLUSTERED','N'),(123,6,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(124,6,'PORT_NUMBER','1521'),(125,6,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(126,6,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(127,6,'PREFERRED_SCHEMA_NAME',NULL),(128,6,'QUOTE_ALL_FIELDS','false'),(129,6,'SQL_CONNECT',NULL),(130,7,'USE_POOLING','N'),(131,7,'IS_CLUSTERED','N'),(132,7,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(133,7,'PORT_NUMBER','1521'),(134,7,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(135,7,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(136,7,'PREFERRED_SCHEMA_NAME',NULL),(137,7,'QUOTE_ALL_FIELDS','false'),(138,7,'SQL_CONNECT',NULL),(139,8,'USE_POOLING','N'),(140,8,'IS_CLUSTERED','N'),(141,8,'SUPPORTS_BOOLEAN_DATA_TYPE','false'),(142,8,'PORT_NUMBER','1521'),(143,8,'FORCE_IDENTIFIERS_TO_UPPERCASE','false'),(144,8,'FORCE_IDENTIFIERS_TO_LOWERCASE','false'),(145,8,'PREFERRED_SCHEMA_NAME',NULL),(146,8,'QUOTE_ALL_FIELDS','false'),(147,8,'SQL_CONNECT',NULL);
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
INSERT INTO `r_directory` VALUES (10,0,'_ϵͳԤ��'),(0,NULL,'��������'),(1,NULL,'����'),(21,1,'�ͻ�ʹ�÷���'),(20,1,'_ϵͳԤ��'),(2,NULL,'ģ��'),(30,2,'_ϵͳԤ��'),(11,0,'�ͻ�ʹ�÷���'),(31,2,'�ͻ�ʹ�÷���');
/*!40000 ALTER TABLE `r_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_domain`
--

DROP TABLE IF EXISTS `r_domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_domain` (
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
-- Dumping data for table `r_domain`
--

LOCK TABLES `r_domain` WRITE;
/*!40000 ALTER TABLE `r_domain` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_domain` ENABLE KEYS */;
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
INSERT INTO `r_filesys_directory` VALUES (1,1,'C:\\','���ݴ洢��Ŀ¼','���ڴ�������ļ��ĸ�Ŀ¼'),(2,1,'C:\\_D\\_bak','���ݴ洢��ʱĿ¼','���ڴ�������ļ�����ʱĿ¼'),(3,2,'10.11.46.181','FTP���Խӿڻ�_181','FTP���Խӿڻ�_181'),(4,3,'10.11.46.181','SFTP���Խӿڻ�_181','SFTP���Խӿڻ�_181'),(5,4,'https://ciastudypattern.googlecode.com/svn/trunk/','����ĵ���SVN','����ĵ���SVN'),(6,5,'','����ĵ���GIT','����ĵ���GIT');
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
INSERT INTO `r_filesys_type` VALUES (1,'local','�������ļ�ϵͳ'),(2,'ftp','FTP�ļ�ϵͳ'),(3,'sftp','SFTP�ļ�ϵͳ'),(4,'svn','SVN������'),(5,'git','GIT������');
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
INSERT INTO `r_func_type` VALUES (0,'root','SYS.Base','��',NULL,'���ܵĸ��ڵ�',0),(1,'di','DI.Base','��������',0,'һ���ڵ㣺ת��/��ҵ',3),(2,'db','DB.Base','����Դ',0,'һ���ڵ㣺����Դ',0),(4,'systools','SYS.Base','����',0,'һ���ڵ㣺����',5),(50,'privilege','SYS.Base','�û�Ȩ��',4,'�����ڵ㣺����-�û�Ȩ��',0),(501,'createuser','SYS.Base','�����û�',50,'�����ڵ㣺����-�û�Ȩ��-�����û�',0),(502,'deluser','SYS.Base','ɾ���û�',50,'�����ڵ㣺����-�û�Ȩ��-ɾ���û�',1),(503,'edituser','SYS.Base','�༭��ǰ�û�',50,'�����ڵ㣺����-�û�Ȩ��-�༭��ǰ�û�',2),(60,'repository','SYS.Base','��Դ��',4,'�����ڵ㣺����-��Դ��',1),(601,'connrep','SYS.Base','������Դ��',60,'�����ڵ㣺����-��Դ��-������Դ��',0),(602,'findrep','SYS.Base','̽����Դ��',60,'�����ڵ㣺����-��Դ��-̽����Դ��',1),(603,'createrep','SYS.Base','������Դ��',60,'�����ڵ㣺����-��Դ��-������Դ��',2),(70,'file','SYS.Base','�ļ�',4,'�����ڵ㣺����-�ļ�',2),(701,'newtrans','DI.Trans.Editor','�½�ת��',70,'�����ڵ㣺����-�ļ�-�½�ת��',0),(702,'newjob','DI.Job.Editor','�½���ҵ',70,'�����ڵ㣺����-�ļ�-�½���ҵ',1),(703,'newdb','DB.Advance','������ݿ�',70,'�����ڵ㣺����-�ļ�-������ݿ�',2),(704,'newfs','FS.Local,FS.FTP','����ļ�ϵͳ',70,'�����ڵ㣺����-�ļ�-����ļ�ϵͳ',3),(80,'operate','SYS.Base','����',4,'�����ڵ㣺����-����',3),(801,'open','SYS.Advance','��',80,'�����ڵ㣺����-����-��',0),(802,'fileopen','SYS.Advance','���ļ���',80,'�����ڵ㣺����-����-���ļ���',1),(803,'downloadzip','SYS.Advance','�������',80,'�����ڵ㣺����-����-�������',2),(804,'uploadfile','SYS.Advance','�ϴ��ļ�',80,'�����ڵ㣺����-����-�ϴ��ļ�',3),(90,'wizard','SYS.Base','��',4,'�����ڵ㣺����-��',4),(901,'wcreatedb','DB.Advance','�������ݿ�����',90,'�����ڵ㣺����-��-�������ݿ�����',0),(902,'wsinglecopy','DB.Advance','��������',90,'�����ڵ㣺����-��-��������',1),(903,'wmulticopy','DB.Advance','�������',90,'�����ڵ㣺����-��-�������',2),(100,'help','SYS.Base','����',4,'�����ڵ㣺����-����',5),(1001,'showstepplugin','DI.Trans.Editor','��ʾ������',100,'�����ڵ㣺����-����-��ʾ������',0),(1002,'showjobplugin','DI.Job.Editor','��ʾ��ҵ���',100,'�����ڵ㣺����-����-��ʾ��ҵ���',1),(1010,'about','SYS.Base','����',100,'�����ڵ㣺����-����-����',3),(3,'domain','DN.Base','����ģ��',0,'һ���ڵ㣺�����',1),(5,'fs','FS.Base','�ļ�ϵͳ',0,'һ���ڵ㣺�ļ�ϵͳ',4),(6,'report','BA.Base','��ҵ����',0,'һ���ڵ㣺����',2),(1003,'showdbplugin','DB.Advance','��ʾ���ݿ���',100,'�����ڵ㣺����-����-��ʾ���ݿ���',2);
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
INSERT INTO `r_func_type_attribute` VALUES (0,1010,'url','rest/systools/about'),(1,1010,'header','����'),(2,1010,'width','420'),(3,1010,'height','480'),(4,501,'header','�����û�'),(5,501,'width','400'),(6,501,'height','300'),(7,501,'url','rest/user/create'),(8,501,'btn','[{\"label\":\"ȷ��\",\"title\":\"ȷ��\",\"url\":\"rest/user/create/save\"},{\"type\":\"cancel\"}]'),(9,1001,'url','rest/systools/stepplugin'),(10,1001,'header','������'),(11,1001,'height','480'),(12,1001,'width','620'),(13,1002,'url','rest/systools/jobplugin'),(14,1002,'header','��ҵ���'),(15,1002,'height','480'),(16,1002,'width','620'),(17,1003,'url','rest/systools/dbplugin'),(18,1003,'header','���ݿ���'),(19,1003,'height','480'),(20,1003,'width','620');
/*!40000 ALTER TABLE `r_func_type_attribute` ENABLE KEYS */;
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
INSERT INTO `r_job` VALUES (1,0,'�ҵĵ�һ����ҵ',NULL,NULL,NULL,0,-1,NULL,'-','2012-12-09 12:36:02','admin','2012-12-09 12:36:43','Y','N','Y',NULL);
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
INSERT INTO `r_jobentry` VALUES (1,1,65,'START',NULL),(2,1,48,'�ɹ�',NULL);
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
INSERT INTO `r_jobentry_type` VALUES (1,'DOS_UNIX_CONVERTER','DOS��UNIX֮����ı�ת��'),(2,'DTD_VALIDATOR','DTD ��֤'),(3,'DummyJob','Example plugin'),(4,'DataCleanerJobEntry','Execute DataCleaner job'),(5,'FTP_PUT','FTP �ϴ�'),(6,'FTP','FTP ����'),(7,'FTP_DELETE','FTP ɾ��'),(8,'FTPS_PUT','FTPS �ϴ�'),(9,'FTPS_GET','FTPS ����'),(10,'HL7MLLPAcknowledge','HL7 MLLP Acknowledge'),(11,'HL7MLLPInput','HL7 MLLP Input'),(12,'HTTP','HTTP'),(13,'MS_ACCESS_BULK_LOAD','MS Access ��������'),(14,'MYSQL_BULK_LOAD','Mysql ��������'),(15,'PALO_CUBE_CREATE','Palo Cube Create'),(16,'PALO_CUBE_DELETE','Palo Cube Delete'),(17,'PING','Ping һ̨����'),(18,'GET_POP','POP ����'),(19,'SFTPPUT','SFTP �ϴ�'),(20,'SFTP','SFTP ����'),(21,'SHELL','Shell'),(22,'SQL','SQL'),(23,'MSSQL_BULK_LOAD','SQLServer ��������'),(24,'SSH2_PUT','SSH2 �ϴ�'),(25,'SSH2_GET','SSH2 ����'),(26,'TALEND_JOB_EXEC','Talend ��ҵִ��'),(27,'XSD_VALIDATOR','XSD ��֤��'),(28,'XSLT','XSL ת��'),(29,'ZIP_FILE','Zip ѹ���ļ�'),(30,'ABORT','��ֹ��ҵ'),(31,'MYSQL_BULK_FILE','�� Mysql �����������ļ�'),(32,'DELETE_RESULT_FILENAMES','�ӽ���ļ���ɾ���ļ�'),(33,'JOB','��ҵ'),(34,'EVAL','ʹ�� JavaScript �ű���֤'),(35,'WRITE_TO_FILE','д���ļ�'),(36,'WRITE_TO_LOG','д��־'),(37,'CREATE_FOLDER','����һ��Ŀ¼'),(38,'CREATE_FILE','�����ļ�'),(39,'DELETE_FILE','ɾ��һ���ļ�'),(40,'DELETE_FILES','ɾ������ļ�'),(41,'DELETE_FOLDERS','ɾ��Ŀ¼'),(42,'SNMP_TRAP','���� SNMP ����'),(43,'SEND_NAGIOS_PASSIVE_CHECK','����Nagios �������'),(44,'MAIL','�����ʼ�'),(45,'COPY_MOVE_RESULT_FILENAMES','����/�ƶ�����ļ�'),(46,'COPY_FILES','�����ļ�'),(47,'EXPORT_REPOSITORY','������Դ�⵽XML�ļ�'),(48,'SUCCESS','�ɹ�'),(49,'MSGBOX_INFO','��ʾ��Ϣ�Ի���'),(50,'XML_WELL_FORMED','��� XML �ļ���ʽ'),(51,'WEBSERVICE_AVAILABLE','���web�����Ƿ����'),(52,'FILE_EXISTS','���һ���ļ��Ƿ����'),(53,'COLUMNS_EXIST','������Ƿ����'),(54,'FILES_EXIST','������ļ��Ƿ����'),(55,'CHECK_DB_CONNECTIONS','������ݿ�����'),(56,'CHECK_FILES_LOCKED','����ļ��Ƿ���'),(57,'CONNECTED_TO_REPOSITORY','����Ƿ����ӵ���Դ��'),(58,'FOLDER_IS_EMPTY','���Ŀ¼�Ƿ�Ϊ��'),(59,'TABLE_EXISTS','�����Ƿ����'),(60,'SIMPLE_EVAL','�����ֶε�ֵ'),(61,'FILE_COMPARE','�Ƚ��ļ�'),(62,'FOLDERS_COMPARE','�Ƚ�Ŀ¼'),(63,'ADD_RESULT_FILENAMES','����ļ�������ļ���'),(64,'TRUNCATE_TABLES','��ձ�'),(65,'SPECIAL','������ҵ��'),(66,'SYSLOG','�� Syslog ������Ϣ'),(67,'PGP_ENCRYPT_FILES','��PGP�����ļ�'),(68,'PGP_DECRYPT_FILES','��PGP�����ļ�'),(69,'PGP_VERIFY_FILES','��PGP��֤�ļ�ǩ��'),(70,'MOVE_FILES','�ƶ��ļ�'),(71,'DELAY','�ȴ�'),(72,'WAIT_FOR_SQL','�ȴ�SQL'),(73,'WAIT_FOR_FILE','�ȴ��ļ�'),(74,'UNZIP','��ѹ���ļ�'),(75,'EVAL_FILES_METRICS','�����ļ���С�����'),(76,'EVAL_TABLE_CONTENT','������еļ�¼��'),(77,'SET_VARIABLES','���ñ���'),(78,'TRANS','ת��'),(79,'TELNET','Զ�̵�¼һ̨����'),(80,'MAIL_VALIDATOR','�ʼ���֤');
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
INSERT INTO `r_loglevel` VALUES (1,'Error','������־'),(2,'Minimal','��С��־'),(3,'Basic','������־'),(4,'Detailed','��ϸ��־'),(5,'Debug','����'),(6,'Rowlevel','�м���־(�ǳ���ϸ)');
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
INSERT INTO `r_report` VALUES (0,1,'<fly:composition width=\"600\" height=\"400\">\r\n	<fly:verticalLayout>\r\n		<fly:fieldSet title=\"��ʶ��\">\r\n			<fly:gridLayout column=\"4\" itemWidth=\"35%,15%,35%,15%\">\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" text=\"֧�ֲ�����������\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" name=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:QUOTE_ALL_FIELDS\" text=\"��ʶ��ʹ������������\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:QUOTE_ALL_FIELDS\" name=\"${formId}:QUOTE_ALL_FIELDS\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" text=\"ǿ�Ʊ�ʶ��ʹ��Сд��ĸ\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_LOWERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n				\r\n				<fly:gridLayoutItem>\r\n					<fly:labelObject buddy=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" text=\"ǿ�Ʊ�ʶ��ʹ�ô�д��ĸ\" />\r\n				</fly:gridLayoutItem>\r\n				<fly:gridLayoutItem>\r\n					<fly:inputText id=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" name=\"${formId}:FORCE_IDENTIFIERS_TO_UPPERCASE\" type=\"checkbox\" value=\"\" />\r\n				</fly:gridLayoutItem>\r\n			</fly:gridLayout>\r\n		</fly:fieldSet>\r\n		\r\n		<fly:labelObject buddy=\"${formId}:preferredSchemaName\" text=\"Ĭ��ģʽ���ƣ���û������ģʽ��ʱʹ�ã�\" />\r\n		\r\n		<fly:inputText id=\"${formId}:preferredSchemaName\" name=\"${formId}:preferredSchemaName\" type=\"text\" value=\"\" />\r\n		\r\n		<fly:labelObject buddy=\"${formId}:connectionType\" text=\"���������ӳɹ���Ҫִ�е�SQL��䣬�÷ֺ�(;)����\" />\r\n		\r\n		<textarea id=\"${formId}:connectSQL\" name=\"${formId}:connectSQL\" style=\"width:100%;\" rows=\"6\">\r\n			\r\n		</textarea>\r\n		\r\n	</fly:verticalLayout>\r\n\r\n	<fly:actions>\r\n\r\n		<fly:action sender=\"${formId}:SUPPORTS_BOOLEAN_DATA_TYPE\" signal=\"change\" accepter=\"${formId}:preferredSchemaName\" slot=\"disable\" />\r\n\r\n	</fly:actions>\r\n\r\n</fly:composition>',1,'N',NULL,'Dashboard����',NULL,NULL,NULL,NULL,NULL,NULL),(1,1,NULL,2,'N',NULL,'��������',NULL,NULL,NULL,NULL,NULL,NULL),(2,1,NULL,3,'N',NULL,'���ɱ�������',NULL,NULL,NULL,NULL,NULL,NULL),(3,1,NULL,4,'N',NULL,'͸�ӱ�������',NULL,NULL,NULL,NULL,NULL,NULL),(4,10,NULL,1,'Y',0,'�����ӵ�����',NULL,NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `r_report_type` VALUES (1,'dashboard','�Ǳ��'),(3,'report','����'),(4,'pivotreport','��ά����'),(2,'wordreport','����');
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
INSERT INTO `r_repository_log` VALUES (1,'4.0','2012-10-27 14:01:29','admin','save transformation \'ת�� 1\''),(2,'4.0','2012-10-27 14:02:05','admin','save transformation \'ת�� 1\''),(3,'5.0','2012-11-21 11:33:02','admin','Creation of initial version'),(4,'5.0','2012-11-21 13:28:48','admin','save transformation \'ת�� 1\''),(5,'5.0','2012-12-06 14:36:27','admin','save transformation \'ת�� 1\''),(6,'5.0','2012-12-06 14:37:48','admin','save transformation \'�����õ�ת��\''),(7,'5.0','2012-12-09 12:36:43','admin','save job \'������ҵ 1\''),(8,'5.0','2013-01-10 15:30:58','admin','save transformation \'�����õ�ת��\''),(9,'5.0','2013-01-10 15:37:00','admin','save transformation \'�����õ�ת��\''),(10,'5.0','2013-01-10 16:55:17','admin','save transformation \'�����õ�ת��\''),(11,'5.0','2013-01-10 16:55:44','admin','save transformation \'�����õ�ת��\''),(12,'5.0','2013-01-10 17:00:51','admin','save transformation \'�����õ�ת��\''),(13,'5.0','2013-01-10 17:01:15','admin','save transformation \'�����õ�ת��\''),(14,'5.0','2013-01-10 17:01:43','admin','save transformation \'�����õ�ת��\''),(15,'5.0','2013-01-10 17:01:50','admin','save transformation \'�����õ�ת��\''),(16,'5.0','2013-01-10 21:26:39','admin','save transformation \'�����õ�ת��\''),(17,'5.0','2013-01-10 21:49:17','admin','save transformation \'�����õ�ת��\''),(18,'5.0','2013-01-10 21:49:59','admin','save transformation \'�����õ�ת��\''),(19,'5.0','2013-01-10 23:14:15','admin','save transformation \'�����õ�ת��\''),(20,'5.0','2013-01-10 23:17:46','admin','save transformation \'�����õ�ת��\''),(21,'5.0','2013-01-10 23:35:49','admin','save transformation \'�����õ�ת��\''),(22,'5.0','2013-01-10 23:36:08','admin','save transformation \'�����õ�ת��\''),(23,'5.0','2013-01-10 23:37:40','admin','save transformation \'ת�� 1\''),(24,'5.0','2013-01-10 23:38:10','admin','save transformation \'ת�� 1\''),(25,'5.0','2013-01-10 23:40:24','admin','save transformation \'ת�� 1\''),(26,'5.0','2013-01-10 23:41:14','admin','save transformation \'�����õ�ת��\''),(27,'5.0','2013-01-10 23:41:32','admin','save transformation \'ת�� 1\''),(28,'5.0','2013-01-10 23:41:59','admin','save transformation \'�����õ�ת��\''),(29,'5.0','2013-01-10 23:43:08','admin','save transformation \'�����õ�ת��\''),(30,'5.0','2013-01-10 23:44:54','admin','save transformation \'�����õ�ת��\''),(31,'5.0','2013-01-10 23:58:20','admin','save transformation \'�����õ�ת��\''),(32,'5.0','2013-01-11 07:13:45','admin','save transformation \'�����õ�ת��\''),(33,'5.0','2013-01-11 07:18:07','admin','save transformation \'�����õ�ת��\''),(34,'5.0','2013-01-11 07:18:18','admin','save transformation \'�����õ�ת��\''),(35,'5.0','2013-01-11 07:20:31','admin','save transformation \'�����õ�ת��\''),(36,'5.0','2013-01-11 09:22:06','admin','save transformation \'�����õ�ת��\''),(37,'5.0','2013-01-11 09:22:44','admin','save transformation \'�����õ�ת��\''),(38,'5.0','2013-01-11 09:22:59','admin','save transformation \'�����õ�ת��\''),(39,'5.0','2013-01-11 09:23:12','admin','save transformation \'�����õ�ת��\''),(40,'5.0','2013-01-11 09:23:44','admin','save transformation \'�����õ�ת��\''),(41,'5.0','2013-01-11 13:44:15','admin','save transformation \'�����õ�ת��\''),(42,'5.0','2013-01-11 13:44:22','admin','save transformation \'�����õ�ת��\''),(43,'5.0','2013-01-12 06:32:13','admin','save transformation \'�����õ�ת��\''),(44,'5.0','2013-01-12 06:33:08','admin','save transformation \'�����õ�ת��\''),(45,'5.0','2013-01-20 09:27:22','admin','save transformation \'�����õ�ת��\''),(46,'5.0','2013-01-20 09:28:01','admin','save transformation \'�����õ�ת��\''),(47,'5.0','2013-07-07 11:59:13','admin','save transformation \'�����õ�ת��\''),(48,'5.0','2013-07-07 12:01:48','admin','save transformation \'�����õ�ת��\''),(49,'5.0','2013-07-07 12:03:27','admin','save transformation \'�����õ�ת��\''),(50,'5.0','2013-07-07 12:07:42','admin','save transformation \'�����õ�ת��\''),(51,'5.0','2013-07-07 12:32:49','admin','save transformation \'�����õ�ת��\''),(52,'5.0','2013-07-24 00:29:27','admin','save transformation \'�ҵĵ�һ��ת��\''),(53,'5.0','2013-07-24 00:31:11','admin','save transformation \'�ҵĵ�һ��ת��\'');
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
  `is_sys` char(1) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_role`
--

LOCK TABLES `r_role` WRITE;
/*!40000 ALTER TABLE `r_role` DISABLE KEYS */;
INSERT INTO `r_role` VALUES (0,'admin','����Ա','Y'),(1,'guest','�ο�','Y'),(10,'di_editor','DI�༭Ա','Y'),(11,'di_operator','DI����Ա','Y');
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
INSERT INTO `r_step` VALUES (4,1,'�����',NULL,182,'Y',1,467,368,'N'),(3,1,'���˼�¼',NULL,193,'Y',1,295,191,'N'),(2,1,'ִ�г���',NULL,104,'Y',1,625,202,'Y'),(1,1,'Access ����',NULL,1,'Y',1,150,365,'Y');
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
INSERT INTO `r_step_attribute` VALUES (52,1,4,0,'partitioning_monthly',0,'Y'),(51,1,4,0,'partitioning_daily',0,'N'),(50,1,4,0,'partitioning_field',0,NULL),(49,1,4,0,'partitioning_enabled',0,'N'),(48,1,4,0,'specify_fields',0,'N'),(47,1,4,0,'use_batch',0,'Y'),(45,1,4,0,'truncate',0,'N'),(46,1,4,0,'ignore_errors',0,'N'),(42,1,4,0,'schema',0,NULL),(43,1,4,0,'table',0,NULL),(44,1,4,0,'commit',0,'1000'),(41,1,4,0,'PARTITIONING_METHOD',0,'none'),(39,1,3,0,'loadbalance',0,'N'),(40,1,4,0,'PARTITIONING_SCHEMA',0,NULL),(38,1,3,0,'cluster_schema',0,NULL),(36,1,3,0,'send_true_to',0,NULL),(37,1,3,0,'send_false_to',0,NULL),(35,1,3,0,'id_condition',1,NULL),(34,1,3,0,'PARTITIONING_METHOD',0,'none'),(32,1,2,0,'loadbalance',0,'N'),(33,1,3,0,'PARTITIONING_SCHEMA',0,NULL),(31,1,2,0,'cluster_schema',0,NULL),(30,1,2,0,'failwhennotsuccess',0,'N'),(29,1,2,0,'exitvaluefieldname',0,'Exit value'),(28,1,2,0,'errorfieldname',0,'Error output'),(27,1,2,0,'resultfieldname',0,'Result output'),(26,1,2,0,'processfield',0,NULL),(25,1,2,0,'PARTITIONING_METHOD',0,'none'),(23,1,1,0,'loadbalance',0,'N'),(24,1,2,0,'PARTITIONING_SCHEMA',0,NULL),(22,1,1,0,'cluster_schema',0,NULL),(21,1,1,0,'extensionFieldName',0,NULL),(20,1,1,0,'rootUriNameFieldName',0,NULL),(19,1,1,0,'uriNameFieldName',0,NULL),(18,1,1,0,'lastModificationTimeFieldName',0,NULL),(17,1,1,0,'hiddenFieldName',0,NULL),(16,1,1,0,'pathFieldName',0,NULL),(15,1,1,0,'shortFileFieldName',0,NULL),(14,1,1,0,'reset_rownumber',0,'N'),(13,1,1,0,'table_name',0,NULL),(12,1,1,0,'limit',0,NULL),(11,1,1,0,'rownum_field',0,NULL),(10,1,1,0,'filename_Field',0,NULL),(9,1,1,0,'filefield',0,'N'),(8,1,1,0,'isaddresult',0,'Y'),(7,1,1,0,'rownum',0,'N'),(6,1,1,0,'tablename_field',0,NULL),(5,1,1,0,'tablename',0,'N'),(4,1,1,0,'include_field',0,NULL),(3,1,1,0,'include',0,'N'),(2,1,1,0,'PARTITIONING_METHOD',0,'none'),(1,1,1,0,'PARTITIONING_SCHEMA',0,NULL),(53,1,4,0,'tablename_in_field',0,'N'),(54,1,4,0,'tablename_field',0,NULL),(55,1,4,0,'tablename_in_table',0,'Y'),(56,1,4,0,'return_keys',0,'N'),(57,1,4,0,'return_field',0,NULL),(58,1,4,0,'cluster_schema',0,NULL),(59,1,4,0,'loadbalance',0,'N');
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
INSERT INTO `r_step_type` VALUES (1,'AccessInput','Access ����','Read data from a Microsoft Access file'),(2,'AccessOutput','Access ���','Stores records into an MS-Access database table.'),(3,'BIRTOutput','BIRT Output','Executes a BIRT Report Design (.rptdesign)'),(4,'ClosureGenerator','Closure Generator','This step allows you to generates a closure table using parent-child relationships.'),(5,'CsvInput','CSV�ļ�����','Simple CSV file input'),(6,'CubeInput','Cube �ļ�����','��һ��cube��ȡ��¼.'),(7,'CubeOutput','Cube���','������д��һ��cube'),(8,'TypeExitEdi2XmlStep','Edi to XML','Converts Edi text to generic XML'),(9,'ElasticSearchBulk','ElasticSearch ��������','Performs bulk inserts into ElasticSearch'),(10,'ShapeFileReader','ESRI Shapefile Reader','Reads shape file data from an ESRI shape file and linked DBF file'),(11,'MetaInject','ETLԪ����ע��','This step allows you to inject metadata into an existing transformation prior to execution.  This allows for the creation of dynamic and highly flexible data integration solutions.'),(12,'DummyPlugin','Example plugin','This is an example for a plugin test step'),(13,'ExcelInput','Excel����','��һ��΢���Excel�ļ����ȡ����. ����Excel 95, 97 and 2000.'),(14,'ExcelOutput','Excel���','Stores records into an Excel (XLS) document with formatting information.'),(15,'GetSlaveSequence','Get ID from slave server','Retrieves unique IDs in blocks from a slave server.  The referenced sequence needs to be configured on the slave server in the XML configuration file.'),(16,'TypeExitGoogleAnalyticsInputStep','Google Analytics ����','Fetches data from google analytics account'),(17,'GPLoad','Greenplum Load','Greenplum Load'),(18,'GPBulkLoader','Greenplum ��������','Greenplum Bulk Loader'),(19,'ParallelGzipCsvInput','GZIP CSV Input','Parallel GZIP CSV file input reader'),(20,'HL7Input','HL7 Input','Reads and parses HL7 messages and outputs a series of values from the messages'),(21,'HTTP','HTTP client','Call a web service over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(22,'HTTPPOST','HTTP Post','Call a web service request over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(23,'InfobrightOutput','Infobright ��������','Load data to an Infobright database table'),(24,'VectorWiseBulkLoader','Ingres VectorWise ��������','This step interfaces with the Ingres VectorWise Bulk Loader \"COPY TABLE\" command.'),(25,'UserDefinedJavaClass','Java ����','This step allows you to program a step using Java code'),(26,'ScriptValueMod','JavaScript����','This is a modified plugin for the Scripting Values with improved interface and performance.\nWritten & donated to open source by Martin Lange, Proconis : http://www.proconis.de'),(27,'JsonInput','Json ����','Extract relevant portions out of JSON structures (file or incoming field) and output rows'),(28,'JsonOutput','Json ���','Create Json bloc and output it in a field ou a file.'),(29,'LDAPInput','LDAP ����','Read data from LDAP host'),(30,'LDAPOutput','LDAP ���','Perform Insert, upsert, update, add or delete operations on records based on their DN (Distinguished  Name).'),(31,'LDIFInput','LDIF ����','Read data from LDIF files'),(32,'LucidDBBulkLoader','LucidDB ��������','Load data into LucidDB by using their bulk load command in streaming mode. (Doesnt work on Windows!)'),(33,'LucidDBStreamingLoader','LucidDB ������','Load data into LucidDB by using Remote Rows UDX.'),(34,'TypeExitExcelWriterStep','Microsoft Excel ���','Writes or appends data to an Excel file'),(35,'MondrianInput','Mondrian ����','Execute and retrieve data using an MDX query against a Pentaho Analyses OLAP server (Mondrian)'),(36,'MonetDBBulkLoader','MonetDB ��������','Load data into MonetDB by using their bulk load command in streaming mode.'),(37,'MultiwayMergeJoin','Multiway Merge Join','Multiway Merge Join'),(38,'MySQLBulkLoader','MySQL ��������','MySQL bulk loader step, loading data over a named pipe (not available on MS Windows)'),(39,'OlapInput','OLAP ����','Execute and retrieve data using an MDX query against any XML/A OLAP datasource using olap4j'),(40,'OpenERPObjectDelete','OpenERP Object Delete','Deletes OpenERP objects'),(41,'OpenERPObjectInput','OpenERP Object Input','Reads data from OpenERP objects'),(42,'OpenERPObjectOutputImport','OpenERP Object Output','Writes data into OpenERP objects using the object import procedure'),(43,'OraBulkLoader','Oracle ��������','Use Oracle Bulk Loader to load data'),(44,'PaloCellInput','Palo Cell Input','Reads data from a defined Palo Cube '),(45,'PaloCellOutput','Palo Cell Output','Writes data to a defined Palo Cube'),(46,'PaloDimInput','Palo Dim Input','Reads data from a defined Palo Dimension'),(47,'PaloDimOutput','Palo Dim Output','Writes data to defined Palo Dimension'),(48,'PentahoReportingOutput','Pentaho �������','Executes an existing report (PRPT)'),(49,'PGPDecryptStream','PGP Decrypt stream','Decrypt data stream with PGP'),(50,'PGPEncryptStream','PGP Encrypt stream','Encrypt data stream with PGP'),(51,'PGBulkLoader','PostgreSQL ��������','PostgreSQL Bulk Loader'),(52,'Rest','REST Client','Consume RESTfull services.\nREpresentational State Transfer (REST) is a key design idiom that embraces a stateless client-server\narchitecture in which the web services are viewed as resources and can be identified by their URLs'),(53,'RssInput','RSS ����','Read RSS feeds'),(54,'RssOutput','RSS ���','Read RSS stream.'),(55,'RuleAccumulator','Rule Accumulator','Execute a rule against a set of all incoming rows'),(56,'RuleExecutor','Rule Executor','Execute a rule against each row passing through'),(57,'S3CSVINPUT','S3 CSV ����','S3 CSV ����'),(58,'SalesforceUpsert','Salesforce Upsert','Insert or update records in Salesforce module.'),(59,'SalesforceDelete','Salesforce ɾ��','Delete records in Salesforce module.'),(60,'SalesforceInsert','Salesforce ����','Insert records in Salesforce module.'),(61,'SalesforceUpdate','Salesforce ����','Update records in Salesforce module.'),(62,'SalesforceInput','Salesforce ����','!BaseStep.TypeTooltipDesc.SalesforceInput!'),(63,'SapInput','SAP ����','Read data from SAP ERP, optionally with parameters'),(64,'SASInput','SAS ����','This step reads files in sas7bdat (SAS) native format'),(65,'SFTPPut','SFTP Put','Upload a file or a stream file to remote host via SFTP'),(66,'SingleThreader','Single Threader','Executes a transformation snippet in a single thread.  You need a standard mapping or a transformation with an Injector step where data from the parent transformation will arive in blocks.'),(67,'SocketWriter','Socket д','Socket writer.  A socket server that can send rows of data to a socket reader.'),(68,'SocketReader','Socket ��','Socket reader.  A socket client that connects to a server (Socket Writer step).'),(69,'SQLFileOutput','SQL �ļ����','Output SQL INSERT statements to file'),(70,'SwitchCase','Switch Case','Switch a row to a certain target step based on the case value in a field.'),(71,'TeraFast','Teradata Fastload ��������','The Teradata Fastload Bulk loader'),(72,'WebServiceLookup','Web �����ѯ','ʹ�� Web �����ѯ��Ϣ'),(73,'WMIInput','WMI ����','Query WMI repository (Management Instrumentation) for class and instance information.\nThis step runs the WMI Query Language (WQL) which is a subset of ANSI SQL.>>>>>>> .r15867'),(74,'XBaseInput','XBase����','��һ��XBase���͵��ļ�(DBF)��ȡ��¼'),(75,'getXMLData','XML �ļ�����','Get data from XML file by using XPath.\n This step also allows you to parse XML defined in a previous field.'),(76,'XMLInputStream','XML �ļ����� (StAX����)','This step is capable of processing very large and complex XML files very fast.'),(77,'XMLInputSax','XML ������','Read data from an XML file in a streaming fashing, working faster and consuming less memory'),(78,'XMLJoin','XML ����','Joins a stream of XML-Tags into a target XML string'),(79,'XMLInput','XML����','��һ��XML��ȡ����'),(80,'XMLOutput','XML���','д���ݵ�һ��XML�ļ�'),(81,'XSLT','XSL ת��','Transform XML stream using XSL (eXtensible Stylesheet Language).'),(82,'YamlInput','Yaml ����','Read YAML source (file or stream) parse them and convert them to rows and writes these to one or more output. '),(83,'ZipFile','Zip �ļ�','Zip a file.\nFilename will be extracted from incoming stream.'),(84,'Abort','��ֹ','Abort a transformation'),(85,'FilesFromResult','�ӽ����ȡ�ļ�','This step allows you to read filenames used or generated in a previous entry in a job.'),(86,'RowsFromResult','�ӽ����ȡ��¼','����������ͬһ�������ǰһ����Ŀ���ȡ��¼.'),(87,'XSDValidator','ʹ�� XSD ���� XML �ļ�','Validate XML source (files or streams) against XML Schema Definition.'),(88,'ValueMapper','ֵӳ��','Maps values of a certain field from one value to another'),(89,'CloneRow','��¡��','Clone a row as many times as needed'),(90,'Formula','��ʽ','ʹ�� Pentaho �Ĺ�ʽ�������㹫ʽ'),(91,'WriteToLog','д��־','Write data to log'),(92,'AnalyticQuery','������ѯ','Execute analytic queries over a sorted dataset (LEAD/LAG/FIRST/LAST)'),(93,'GroupBy','����','�Է������ʽ�����ۺ�.{0}���������һ���Ѿ��ź����������Ч.{1}�������û������, �������������ļ�¼�б���ȷ����.'),(94,'SplitFieldToRows3','�в��Ϊ����','Splits a single string field by delimiter and creates a new row for each split term'),(95,'Denormaliser','��ת��','Denormalises rows by looking up key-value pairs and by assigning them to new fields in the��� rows.{0}This method aggregates and needs the���� rows to be sorted on the grouping fields'),(96,'Delete','ɾ��','���ڹؼ���ɾ����¼'),(97,'Janino','����Janino����Java���ʽ','Calculate the result of a Java Expression using Janino'),(98,'StringCut','�����ַ���','Strings cut (substring).'),(99,'UnivariateStats','������ͳ��','This step computes some simple stats based on a single input field'),(100,'Unique','ȥ���ظ���¼','ȥ���ظ��ļ�¼�У����ּ�¼Ψһ{0}�����������һ���Ѿ��ź��������.{1}�������û������, �������������ļ�¼�б���ȷ����.'),(101,'SyslogMessage','������Ϣ��Syslog','Send message to Syslog server'),(102,'Mail','�����ʼ�','Send eMail.'),(103,'MergeRows','�ϲ���¼','�ϲ�����������, ������ĳ���ؼ�������.  ���������������Ƚϣ��Ա�ʶ��ȵġ�����ġ�ɾ���ĺ��½��ļ�¼.'),(104,'ExecProcess','����һ������','Execute a process and return the result'),(105,'UniqueRowsByHashSet','Ψһ�� (��ϣֵ)','Remove double rows and leave only unique occurrences by using a HashSet.'),(106,'FixedInput','�̶�����ļ�����','Fixed file input'),(107,'MemoryGroupBy','���ڴ��з���','Builds aggregates in a group by fashion.\nThis step doesn\'t require sorted input.'),(108,'AddXML','����XML��','Encode several fields into an XML fragment'),(109,'Constant','���ӳ���','����¼����һ���������'),(110,'Sequence','��������','�����л�ȡ��һ��ֵ'),(111,'CheckSum','����У����','Add a checksum column for each input row'),(112,'ProcessFiles','�����ļ�','Process one file per row (copy or move or delete).\nThis step only accept filename in input.'),(113,'FilesToResult','�����ļ������','This step allows you to set filenames in the result of this transformation.\nSubsequent job entries can then use this information.'),(114,'RowsToResult','���Ƽ�¼�����','ʹ���������Ѽ�¼д������ִ�е�����.{0}��Ϣ���ᱻ���ݸ�ͬһ�����������һ����Ŀ.'),(115,'SelectValues','�ֶ�ѡ��','ѡ����Ƴ���¼����֡�{0}���⣬���������ֶε�Ԫ����: ����, ���Ⱥ;���.'),(116,'StringOperations','�ַ�������','Apply certain operations like trimming, padding and others to string value.'),(117,'ReplaceString','�ַ����滻','Replace all occurences a word in a string with another word.'),(118,'SymmetricCryptoTrans','�ԳƼ���','Encrypt or decrypt a string using symmetric encryption.\nAvailable algorithms are DES, AEC, TripleDES.'),(119,'SetValueConstant','���ֶ�ֵ����Ϊ����','Set value of a field to a constant'),(120,'Delay','�ӳ���','Output each input row after a delay'),(121,'DynamicSQLRow','ִ��Dynamic SQL','Execute dynamic SQL statement build in a previous field'),(122,'ExecSQL','ִ��SQL�ű�','ִ��һ��SQL�ű�, ���⣬����ʹ������ļ�¼��Ϊ����'),(123,'ExecSQLRow','ִ��SQL�ű�(�ֶ����滻)','Execute SQL script extracted from a field\ncreated in a previous step.'),(124,'JobExecutor','ִ����ҵ','This step executes a Pentaho Data Integration job, sets parameters and passes rows.'),(125,'FieldSplitter','����ֶ�','�������һ���ֶβ�ֳɶ��ʱ��ʹ���������.'),(126,'SortedMerge','����ϲ�','Sorted Merge'),(127,'SortRows','�����¼','�����ֶ�ֵ�Ѽ�¼����(�������)'),(128,'InsertUpdate','�������','���ڹؼ��ָ��»�����¼�����ݿ�.'),(129,'ChangeFileEncoding','�ı��ļ�����','Change file encoding and create a new file'),(130,'NumberRange','��ֵ��Χ','Create ranges based on numeric field'),(131,'SynchronizeAfterMerge','����ͬ��','This step perform insert/update/delete in one go based on the value of a field. '),(132,'DBLookup','���ݿ��ѯ','ʹ���ֶ�ֵ�����ݿ����ѯֵ'),(133,'DBJoin','���ݿ�����','ʹ�����������ֵ��Ϊ����ִ��һ�����ݿ��ѯ'),(134,'Validator','���ݼ���','Validates passing data based on a set of rules'),(135,'PrioritizeStreams','���������ȼ�����','Prioritize streams in an order way.'),(136,'ReservoirSampling','���ݲ���','[Transform] Samples a fixed number of rows from the incoming stream'),(137,'LoadFileInput','�ļ����ݼ������ڴ�','Load file content in memory'),(138,'TextFileInput','�ı��ļ�����','��һ���ı��ļ������ָ�ʽ�����ȡ����{0}��Щ���ݿ��Ա����ݵ���һ��������...'),(139,'TextFileOutput','�ı��ļ����','д��¼��һ���ı��ļ�.'),(140,'Mapping','ӳ�� (��ת��)','����һ��ӳ�� (��ת��), ʹ��MappingInput��MappingOutput��ָ���ӿڵ��ֶ�'),(141,'MappingInput','ӳ������淶','ָ��һ��ӳ����ֶ�����'),(142,'MappingOutput','ӳ������淶','ָ��һ��ӳ����ֶ����'),(143,'Update','����','���ڹؼ��ָ��¼�¼�����ݿ�'),(144,'IfNull','�滻NULLֵ','Sets a field value to a constant if it is null.'),(145,'SampleRows','������','Filter rows based on the line number.'),(146,'JavaFilter','����Java������˼�¼','Filter rows using java code'),(147,'FieldsChangeSequence','�����ֶ�ֵ���ı�����','Add sequence depending of fields value change.\nEach time value of at least one field change, PDI will reset sequence. '),(148,'WebServiceAvailable','���web�����Ƿ����','Check if a webservice is available'),(149,'FileExists','����ļ��Ƿ����','Check if a file exists'),(150,'FileLocked','����ļ��Ƿ��ѱ�����','Check if a file is locked by another process'),(151,'TableExists','�����Ƿ����','Check if a table exists on a specified connection'),(152,'ColumnExists','����������Ƿ����','Check if a column exists in a table on a specified connection.'),(153,'DetectEmptyStream','������','This step will output one empty row if input stream is empty\n(ie when input stream does not contain any row)'),(154,'CreditCardValidator','�������ÿ������Ƿ���Ч','The Credit card validator step will help you tell:\n(1) if a credit card number is valid (uses LUHN10 (MOD-10) algorithm)\n(2) which credit card vendor handles that number\n(VISA, MasterCard, Diners Club, EnRoute, American Express (AMEX),...)'),(155,'MailValidator','�����ʼ���ַ','Check if an email address is valid.'),(156,'FuzzyMatch','ģ��ƥ��','Finding approximate matches to a string using matching algorithms.\nRead a field from a main stream and output approximative value from lookup stream.'),(157,'RegexEval','������ʽ','Regular expression Evaluation\nThis step uses a regular expression to evaluate a field. It can also extract new fields out of an existing field with capturing groups.'),(158,'TableCompare','�Ƚϱ�','Compares 2 tables and gives back a list of differences'),(159,'StreamLookup','����ѯ','��ת���е����������ѯֵ.'),(160,'StepMetastructure','����Ԫ����','This is a step to read the metadata of the incoming stream.'),(161,'SecretKeyGenerator','������Կ','Generate secrete key for algorithms such as DES, AEC, TripleDES.'),(162,'RowGenerator','���ɼ�¼','����һЩ�ռ�¼����ȵ���.'),(163,'RandomValue','���������','Generate random value'),(164,'RandomCCNumberGenerator','������������ÿ���','Generate random valide (luhn check) credit card numbers'),(165,'Dummy','�ղ��� (ʲôҲ����)','�����������ʲô������.{0} ��������Ի�����������ʱ������.'),(166,'DimensionLookup','ά�Ȳ�ѯ/����','��һ�����ݲֿ������һ������ά {0} ���������ά���ѯ��Ϣ.'),(167,'CombinationLookup','���ϲ�ѯ/����','�������ݲֿ����һ��junkά {0} ��ѡ��, ���в�ѯά�����Ϣ.{1}junkά�����������е��ֶ�.'),(168,'AggregateRows','�ۺϼ�¼','�����������������ۺϼ�¼.{0}������ʹ���ڷ�������.'),(169,'AutoDoc','�Զ��ĵ����','This step automatically generates documentation based on input in the form of a list of transformations and jobs'),(170,'DataGrid','�Զ��峣������','Enter rows of static data in a grid, usually for testing, reference or demo purpose'),(171,'GetPreviousRowField','��ȡ��һ�εļ�¼','Get fields value of previous row.'),(172,'GetVariable','��ȡ����','Determine the values of certain (environment or Kettle) variables and put them in field values.'),(173,'GetSubFolders','��ȡ��Ŀ¼��','Read a parent folder and return all subfolders'),(174,'GetFileNames','��ȡ�ļ���','Get file names from the operating system and send them to the next step.'),(175,'GetFilesRowsCount','��ȡ�ļ�����','Returns rows count for text files.'),(176,'SystemInfo','��ȡϵͳ��Ϣ','��ȡϵͳ��Ϣ������ʱ�䡢����.'),(177,'GetTableNames','��ȡ����','Get table names from database connection and send them to the next step'),(178,'GetRepositoryNames','��ȡ��Դ������','Lists detailed information about transformations and/or jobs in a repository'),(179,'Flattener','�б�ƽ��','Flattens consequetive rows based on the order in which they appear in the���� stream'),(180,'Normaliser','��ת��','De-normalised information can be normalised using this step type.'),(181,'TableInput','������','�����ݿ�����ȡ��Ϣ.'),(182,'TableOutput','�����','д��Ϣ��һ�����ݿ��'),(183,'Calculator','������','ͨ��ִ�м򵥵ļ��㴴��һ�����ֶ�'),(184,'JoinRows','��¼���� (�ѿ������)','��������������������ĵѿ����Ľ��.{0} �������ļ�¼������������¼֮��ĳ˻�.'),(185,'Injector','��¼ע��','Injector step to allow to inject rows into the transformation through the java API'),(186,'MergeJoin','��¼������','Joins two streams on a given key and outputs a joined set. The input streams must be sorted on the join key'),(187,'NullIf','����ֵΪNULL','���һ���ֶ�ֵ����ĳ���̶�ֵ����ô������ֶ�ֵ���ó�null'),(188,'SetVariable','���ñ���','Set environment variables based on a single input row.'),(189,'SetValueField','�����ֶ�ֵ','Set value of a field with another value field'),(190,'DetectLastRow','ʶ���������һ��','Last row will be marked'),(191,'DBProc','����DB�洢����','ͨ���������ݿ�洢���̻�÷���ֵ.'),(192,'StepsMetrics','ת��������Ϣͳ��','Return metrics for one or several steps'),(193,'FilterRows','���˼�¼','ʹ�ü򵥵���������˼�¼'),(194,'SSH','����SSH����','Run SSH commands and returns result.'),(195,'Append','׷����','Append 2 streams in an ordered way'),(196,'MailInput','�ʼ���Ϣ����','Read POP3/IMAP server and retrieve messages'),(197,'PropertyInput','�����ļ�����','Read data (key, value) from properties files.'),(198,'PropertyOutput','�����ļ����','Write data to properties file'),(199,'BlockingStep','��������','This step blocks until all incoming rows have been processed.  Subsequent steps only recieve the last input row to this step.'),(200,'BlockUntilStepsFinish','��������ֱ�����趼���','Block this step until selected steps finish.'),(201,'ReportFileOutput','�����ļ����','ͨ��������������һ��������ʽ���趨����.');
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
INSERT INTO `r_trans_attribute` VALUES (219,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(218,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME11',0,'ROOT_CHANNEL_ID'),(217,1,0,'CHANNEL_LOG_TABLE_FIELD_ID11',0,'ROOT_CHANNEL_ID'),(216,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(215,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME10',0,'PARENT_CHANNEL_ID'),(214,1,0,'CHANNEL_LOG_TABLE_FIELD_ID10',0,'PARENT_CHANNEL_ID'),(209,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME8',0,'OBJECT_ID'),(213,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(212,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME9',0,'OBJECT_REVISION'),(211,1,0,'CHANNEL_LOG_TABLE_FIELD_ID9',0,'OBJECT_REVISION'),(210,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(208,1,0,'CHANNEL_LOG_TABLE_FIELD_ID8',0,'OBJECT_ID'),(207,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(206,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME7',0,'FILENAME'),(205,1,0,'CHANNEL_LOG_TABLE_FIELD_ID7',0,'FILENAME'),(204,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(203,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME6',0,'REPOSITORY_DIRECTORY'),(202,1,0,'CHANNEL_LOG_TABLE_FIELD_ID6',0,'REPOSITORY_DIRECTORY'),(201,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(200,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME5',0,'OBJECT_COPY'),(199,1,0,'CHANNEL_LOG_TABLE_FIELD_ID5',0,'OBJECT_COPY'),(198,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(197,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME4',0,'OBJECT_NAME'),(195,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(196,1,0,'CHANNEL_LOG_TABLE_FIELD_ID4',0,'OBJECT_NAME'),(193,1,0,'CHANNEL_LOG_TABLE_FIELD_ID3',0,'LOGGING_OBJECT_TYPE'),(194,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME3',0,'LOGGING_OBJECT_TYPE'),(192,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(191,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(188,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(189,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(190,1,0,'CHANNEL_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(185,1,0,'CHANNEL_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(186,1,0,'CHANNEL_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(187,1,0,'CHANNEL_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(184,1,0,'CHANNEL_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(183,1,0,'CHANNEL_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(182,1,0,'CHANNEL_LOG_TABLE_TABLE_NAME',0,NULL),(181,1,0,'CHANNEL_LOG_TABLE_SCHEMA_NAME',0,NULL),(180,1,0,'CHANNEL_LOG_TABLE_CONNECTION_NAME',0,NULL),(179,1,0,'PERFORMANCELOG_TABLE_INTERVAL',0,NULL),(178,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(177,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME14',0,'OUTPUT_BUFFER_ROWS'),(176,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID14',0,'OUTPUT_BUFFER_ROWS'),(175,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(174,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME13',0,'INPUT_BUFFER_ROWS'),(173,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID13',0,'INPUT_BUFFER_ROWS'),(172,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(171,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(170,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(169,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(167,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(168,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(166,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(165,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(164,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(163,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(162,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(161,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(160,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(159,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(157,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(158,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(156,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(155,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(154,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(153,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(150,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(151,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(152,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(149,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(148,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(147,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(146,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(145,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(144,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(143,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(142,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(141,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME2',0,'LOGDATE'),(140,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID2',0,'LOGDATE'),(138,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME1',0,'SEQ_NR'),(139,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(136,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(137,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID1',0,'SEQ_NR'),(135,1,0,'PERFORMANCE_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(134,1,0,'PERFORMANCE_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(128,1,0,'STEP_LOG_TABLE_FIELD_NAME13',0,'LOG_FIELD'),(129,1,0,'STEP_LOG_TABLE_FIELD_ENABLED13',0,'N'),(130,1,0,'PERFORMANCE_LOG_TABLE_CONNECTION_NAME',0,NULL),(131,1,0,'PERFORMANCE_LOG_TABLE_SCHEMA_NAME',0,NULL),(132,1,0,'PERFORMANCE_LOG_TABLE_TABLE_NAME',0,NULL),(133,1,0,'PERFORMANCE_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(127,1,0,'STEP_LOG_TABLE_FIELD_ID13',0,'LOG_FIELD'),(126,1,0,'STEP_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(125,1,0,'STEP_LOG_TABLE_FIELD_NAME12',0,'ERRORS'),(124,1,0,'STEP_LOG_TABLE_FIELD_ID12',0,'ERRORS'),(123,1,0,'STEP_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(122,1,0,'STEP_LOG_TABLE_FIELD_NAME11',0,'LINES_REJECTED'),(121,1,0,'STEP_LOG_TABLE_FIELD_ID11',0,'LINES_REJECTED'),(120,1,0,'STEP_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(119,1,0,'STEP_LOG_TABLE_FIELD_NAME10',0,'LINES_OUTPUT'),(118,1,0,'STEP_LOG_TABLE_FIELD_ID10',0,'LINES_OUTPUT'),(117,1,0,'STEP_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(116,1,0,'STEP_LOG_TABLE_FIELD_NAME9',0,'LINES_INPUT'),(115,1,0,'STEP_LOG_TABLE_FIELD_ID9',0,'LINES_INPUT'),(114,1,0,'STEP_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(113,1,0,'STEP_LOG_TABLE_FIELD_NAME8',0,'LINES_UPDATED'),(112,1,0,'STEP_LOG_TABLE_FIELD_ID8',0,'LINES_UPDATED'),(111,1,0,'STEP_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(110,1,0,'STEP_LOG_TABLE_FIELD_NAME7',0,'LINES_WRITTEN'),(109,1,0,'STEP_LOG_TABLE_FIELD_ID7',0,'LINES_WRITTEN'),(108,1,0,'STEP_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(107,1,0,'STEP_LOG_TABLE_FIELD_NAME6',0,'LINES_READ'),(106,1,0,'STEP_LOG_TABLE_FIELD_ID6',0,'LINES_READ'),(105,1,0,'STEP_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(104,1,0,'STEP_LOG_TABLE_FIELD_NAME5',0,'STEP_COPY'),(103,1,0,'STEP_LOG_TABLE_FIELD_ID5',0,'STEP_COPY'),(102,1,0,'STEP_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(101,1,0,'STEP_LOG_TABLE_FIELD_NAME4',0,'STEPNAME'),(100,1,0,'STEP_LOG_TABLE_FIELD_ID4',0,'STEPNAME'),(99,1,0,'STEP_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(98,1,0,'STEP_LOG_TABLE_FIELD_NAME3',0,'TRANSNAME'),(97,1,0,'STEP_LOG_TABLE_FIELD_ID3',0,'TRANSNAME'),(96,1,0,'STEP_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(95,1,0,'STEP_LOG_TABLE_FIELD_NAME2',0,'LOG_DATE'),(94,1,0,'STEP_LOG_TABLE_FIELD_ID2',0,'LOG_DATE'),(93,1,0,'STEP_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(92,1,0,'STEP_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(91,1,0,'STEP_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(90,1,0,'STEP_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(89,1,0,'STEP_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(88,1,0,'STEP_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(87,1,0,'STEP_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(86,1,0,'STEP_LOG_TABLE_TABLE_NAME',0,NULL),(85,1,0,'STEP_LOG_TABLE_SCHEMA_NAME',0,NULL),(84,1,0,'STEP_LOG_TABLE_CONNECTION_NAME',0,NULL),(83,1,0,'TRANSLOG_TABLE_SIZE_LIMIT',0,NULL),(82,1,0,'TRANSLOG_TABLE_INTERVAL',0,NULL),(81,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED18',0,'Y'),(80,1,0,'TRANS_LOG_TABLE_FIELD_NAME18',0,'EXECUTING_USER'),(79,1,0,'TRANS_LOG_TABLE_FIELD_ID18',0,'EXECUTING_USER'),(78,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED17',0,'Y'),(77,1,0,'TRANS_LOG_TABLE_FIELD_NAME17',0,'EXECUTING_SERVER'),(76,1,0,'TRANS_LOG_TABLE_FIELD_ID17',0,'EXECUTING_SERVER'),(75,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED16',0,'Y'),(60,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED11',0,'Y'),(61,1,0,'TRANS_LOG_TABLE_FIELD_ID12',0,'ENDDATE'),(62,1,0,'TRANS_LOG_TABLE_FIELD_NAME12',0,'ENDDATE'),(63,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED12',0,'Y'),(64,1,0,'TRANS_LOG_TABLE_FIELD_ID13',0,'LOGDATE'),(65,1,0,'TRANS_LOG_TABLE_FIELD_NAME13',0,'LOGDATE'),(66,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED13',0,'Y'),(67,1,0,'TRANS_LOG_TABLE_FIELD_ID14',0,'DEPDATE'),(68,1,0,'TRANS_LOG_TABLE_FIELD_NAME14',0,'DEPDATE'),(69,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED14',0,'Y'),(70,1,0,'TRANS_LOG_TABLE_FIELD_ID15',0,'REPLAYDATE'),(71,1,0,'TRANS_LOG_TABLE_FIELD_NAME15',0,'REPLAYDATE'),(72,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED15',0,'Y'),(73,1,0,'TRANS_LOG_TABLE_FIELD_ID16',0,'LOG_FIELD'),(74,1,0,'TRANS_LOG_TABLE_FIELD_NAME16',0,'LOG_FIELD'),(59,1,0,'TRANS_LOG_TABLE_FIELD_NAME11',0,'STARTDATE'),(58,1,0,'TRANS_LOG_TABLE_FIELD_ID11',0,'STARTDATE'),(57,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED10',0,'Y'),(56,1,0,'TRANS_LOG_TABLE_FIELD_NAME10',0,'ERRORS'),(55,1,0,'TRANS_LOG_TABLE_FIELD_ID10',0,'ERRORS'),(54,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT9',0,NULL),(53,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED9',0,'Y'),(52,1,0,'TRANS_LOG_TABLE_FIELD_NAME9',0,'LINES_REJECTED'),(51,1,0,'TRANS_LOG_TABLE_FIELD_ID9',0,'LINES_REJECTED'),(50,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT8',0,NULL),(49,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED8',0,'Y'),(48,1,0,'TRANS_LOG_TABLE_FIELD_NAME8',0,'LINES_OUTPUT'),(47,1,0,'TRANS_LOG_TABLE_FIELD_ID8',0,'LINES_OUTPUT'),(46,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT7',0,NULL),(45,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED7',0,'Y'),(44,1,0,'TRANS_LOG_TABLE_FIELD_NAME7',0,'LINES_INPUT'),(43,1,0,'TRANS_LOG_TABLE_FIELD_ID7',0,'LINES_INPUT'),(42,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT6',0,NULL),(41,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED6',0,'Y'),(39,1,0,'TRANS_LOG_TABLE_FIELD_ID6',0,'LINES_UPDATED'),(40,1,0,'TRANS_LOG_TABLE_FIELD_NAME6',0,'LINES_UPDATED'),(37,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED5',0,'Y'),(38,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT5',0,NULL),(36,1,0,'TRANS_LOG_TABLE_FIELD_NAME5',0,'LINES_WRITTEN'),(35,1,0,'TRANS_LOG_TABLE_FIELD_ID5',0,'LINES_WRITTEN'),(34,1,0,'TRANS_LOG_TABLE_FIELD_SUBJECT4',0,NULL),(33,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED4',0,'Y'),(30,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED3',0,'Y'),(31,1,0,'TRANS_LOG_TABLE_FIELD_ID4',0,'LINES_READ'),(32,1,0,'TRANS_LOG_TABLE_FIELD_NAME4',0,'LINES_READ'),(29,1,0,'TRANS_LOG_TABLE_FIELD_NAME3',0,'STATUS'),(28,1,0,'TRANS_LOG_TABLE_FIELD_ID3',0,'STATUS'),(27,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED2',0,'Y'),(26,1,0,'TRANS_LOG_TABLE_FIELD_NAME2',0,'TRANSNAME'),(25,1,0,'TRANS_LOG_TABLE_FIELD_ID2',0,'TRANSNAME'),(24,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED1',0,'Y'),(23,1,0,'TRANS_LOG_TABLE_FIELD_NAME1',0,'CHANNEL_ID'),(22,1,0,'TRANS_LOG_TABLE_FIELD_ID1',0,'CHANNEL_ID'),(21,1,0,'TRANS_LOG_TABLE_FIELD_ENABLED0',0,'Y'),(20,1,0,'TRANS_LOG_TABLE_FIELD_NAME0',0,'ID_BATCH'),(19,1,0,'TRANS_LOG_TABLE_FIELD_ID0',0,'ID_BATCH'),(18,1,0,'TRANS_LOG_TABLE_TIMEOUT_IN_DAYS',0,NULL),(16,1,0,'TRANS_LOG_TABLE_SCHEMA_NAME',0,NULL),(17,1,0,'TRANS_LOG_TABLE_TABLE_NAME',0,NULL),(15,1,0,'TRANS_LOG_TABLE_CONNECTION_NAME',0,NULL),(14,1,0,'SLEEP_TIME_FULL',50,NULL),(12,1,0,'TRANSFORMATION_TYPE',0,'Normal'),(13,1,0,'SLEEP_TIME_EMPTY',50,NULL),(11,1,0,'LOG_INTERVAL',0,NULL),(10,1,0,'LOG_SIZE_LIMIT',0,NULL),(8,1,0,'STEP_PERFORMANCE_CAPTURING_SIZE_LIMIT',0,'100'),(9,1,0,'STEP_PERFORMANCE_LOG_TABLE',0,NULL),(6,1,0,'CAPTURE_STEP_PERFORMANCE',0,'N'),(7,1,0,'STEP_PERFORMANCE_CAPTURING_DELAY',1000,NULL),(5,1,0,'SHARED_FILE',0,NULL),(4,1,0,'USING_THREAD_PRIORITIES',0,'Y'),(3,1,0,'FEEDBACK_SIZE',50000,NULL),(2,1,0,'FEEDBACK_SHOWN',0,'Y'),(1,1,0,'UNIQUE_CONNECTIONS',0,'N');
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
INSERT INTO `r_trans_hop` VALUES (3,1,4,2,'Y'),(2,1,3,4,'Y'),(1,1,1,3,'Y');
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
INSERT INTO `r_trans_step_condition` VALUES (1,3,1);
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
INSERT INTO `r_transformation` VALUES (1,0,'�ҵĵ�һ��ת��',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,-1,NULL,'Y','Y',-1,NULL,NULL,0,0,'-','2012-10-27 14:01:42','admin','2012-12-06 14:37:48',10000);
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
INSERT INTO `r_user` VALUES (1,'admin','2be98afc86aa7f2e4cb79ce61d68bacd1','ϵͳ����Ա','ϵͳ�����˻�','Y'),(2,'guest','2be98afc86aa7f2e4cb79ce77cb97bcce','�ο�','ֻ��Ȩ���˻�','Y'),(3,'yuce','2be98afc86aa7f2e4cb79ce10c787acdf','Ԥ��','Ԥ������˻�','Y');
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

-- Dump completed on 2013-08-18 23:36:57
