package com.flywet.platform.bi.core.db;

public class BIDatabaseRepositoryBase {

	public static final String TABLE_BI_AUTO_START_JOB = "BI_AUTO_START_JOB";
	public static final String FIELD_AUTO_START_ID_AUTO_START_JOB = "ID_AUTO_START_JOB";
	public static final String FIELD_AUTO_START_ARG_LOG_LEVEL = "ARG_LOG_LEVEL";
	public static final String FIELD_AUTO_START_STATUS = "STATUS";
	public static final String FIELD_AUTO_START_CREATED_USER = "CREATED_USER";
	public static final String FIELD_AUTO_START_CREATED_DATE = "CREATED_DATE";
	public static final String FIELD_AUTO_START_MODIFIED_USER = "MODIFIED_USER";
	public static final String FIELD_AUTO_START_MODIFIED_DATE = "MODIFIED_DATE";

	public static final String TABLE_BI_AUTO_START_JOB_ATTR = "BI_AUTO_START_JOB_ATTR";
	public static final String FIELD_AUTO_START_JOB_ATTR_ID_ATTRIBUTE = "ID_ATTRIBUTE";
	public static final String FIELD_AUTO_START_JOB_ATTR_ID_JOB = "ID_JOB";
	public static final String FIELD_AUTO_START_JOB_ATTR_ATTR_TYPE = "ATTR_TYPE";
	public static final String FIELD_AUTO_START_JOB_ATTR_ATTR_KEY = "ATTR_KEY";
	public static final String FIELD_AUTO_START_JOB_ATTR_ATTR_VALUE = "ATTR_VALUE";

	public static final String TABLE_BI_TIMED_EXE = "BI_TIMED_EXE";
	public static final String FIELD_TIMED_EXE_ID_TIMED_EXE = "ID_TIMED_EXE";
	public static final String FIELD_TIMED_EXE_NAME = "NAME";
	public static final String FIELD_TIMED_EXE_TYPE = "TYPE";
	public static final String FIELD_TIMED_EXE_REPEAT = "REPEAT";
	public static final String FIELD_TIMED_EXE_STATUS = "STATUS";
	public static final String FIELD_TIMED_EXE_CREATED_USER = "CREATED_USER";
	public static final String FIELD_TIMED_EXE_CREATED_DATE = "CREATED_DATE";
	public static final String FIELD_TIMED_EXE_MODIFIED_USER = "MODIFIED_USER";
	public static final String FIELD_TIMED_EXE_MODIFIED_DATE = "MODIFIED_DATE";

	public static final String TABLE_BI_TIMED_EXE_ATTR = "BI_TIMED_EXE_ATTR";
	public static final String FIELD_TIMED_EXE_ATTR_ID_TIMED_EXE_ATTR = "ID_TIMED_EXE_ATTR";
	public static final String FIELD_TIMED_EXE_ATTR_ID_TIMED_EXE = "ID_TIMED_EXT";
	public static final String FIELD_TIMED_EXE_ATTR_NR = "NR";
	public static final String FIELD_TIMED_EXE_ATTR_CODE = "CODE";
	public static final String FIELD_TIMED_EXE_ATTR_VALUE_NUM = "VALUE_NUM";
	public static final String FIELD_TIMED_EXE_ATTR_VALUE_STR = "VALUE_STR";
	
	

	public static final String TABLE_BI_MSG_CHANNEL = "BI_MSG_CHANNEL";
	public static final String FIELD_MSG_CHANNEL_ID_MSG_CHANNEL = "ID_MSG_CHANNEL";
	public static final String FIELD_MSG_CHANNEL_NAME = "NAME";
	public static final String FIELD_MSG_CHANNEL_DESCRIPTION = "DESCRIPTION";
	public static final String FIELD_MSG_CHANNEL_AUTHENTICATE = "AUTHENTICATE";
	public static final String FIELD_MSG_CHANNEL_STATUS = "STATUS";
	public static final String FIELD_MSG_VALUE_TEMPLATE = "VALUE_TEMPLATE";
	public static final String FIELD_MSG_URL_TEMPLATE = "URL_TEMPLATE";
	public static final String FIELD_MSG_CHANNEL_CREATED_USER = "CREATED_USER";
	public static final String FIELD_MSG_CHANNEL_CREATED_DATE = "CREATED_DATE";
	public static final String FIELD_MSG_CHANNEL_MODIFIED_USER = "MODIFIED_USER";
	public static final String FIELD_MSG_CHANNEL_MODIFIED_DATE = "MODIFIED_DATE";

	public static final String TABLE_BI_MSG = "BI_MSG";
	public static final String FIELD_MSG_ID_MSG = "ID_MSG";
	public static final String FIELD_MSG_ID_MSG_CHANNEL = "ID_MSG_CHANNEL";
	public static final String FIELD_MSG_STATUS = "STATUS";
	public static final String FIELD_MSG_VALUE_STR = "VALUE_STR";
	public static final String FIELD_MSG_URL = "URL";
	public static final String FIELD_MSG_START_TIME = "START_TIME";
	public static final String FIELD_MSG_EXTEND_TIME = "EXTEND_TIME";

	public static final String TABLE_BI_FUNC_TYPE = "BI_FUNC_TYPE";
	public static final String FIELD_FUNC_TYPE_ID_FUNC_TYPE = "ID_FUNC_TYPE";
	public static final String FIELD_FUNC_TYPE_CODE = "CODE";
	public static final String FIELD_FUNC_TYPE_MODULE_CODE = "MODULE_CODE";
	public static final String FIELD_FUNC_TYPE_DESCRIPTION = "DESCRIPTION";
	public static final String FIELD_FUNC_TYPE_ID_FUNC_TYPE_PARENT = "ID_FUNC_TYPE_PARENT";
	public static final String FIELD_FUNC_TYPE_HELPTEXT = "HELPTEXT";
	public static final String FIELD_FUNC_TYPE_TYPE_INDEX = "TYPE_INDEX";

	public static final String TABLE_BI_FUNC_TYPE_ATTRIBUTE = "BI_FUNC_TYPE_ATTRIBUTE";
	public static final String FIELD_FUNC_TYPE_ATTRIBUTE_ID_FUNC_TYPE_ATTR = "ID_FUNC_TYPE_ATTR";
	public static final String FIELD_FUNC_TYPE_ATTRIBUTE_ID_FUNC_TYPE = "ID_FUNC_TYPE";
	public static final String FIELD_FUNC_TYPE_ATTRIBUTE_CODE = "CODE";
	public static final String FIELD_FUNC_TYPE_ATTRIBUTE_VALUE_STR = "VALUE_STR";

	public static final String TABLE_BI_PORTAL_MENU = "BI_PORTAL_MENU";
	public static final String FIELD_PORTAL_MENU_ID_PORTAL_MENU = "ID_PORTAL_MENU";
	public static final String FIELD_PORTAL_MENU_CODE = "CODE";
	public static final String FIELD_PORTAL_MENU_MODULE_CODE = "MODULE_CODE";
	public static final String FIELD_PORTAL_MENU_DESCRIPTION = "DESCRIPTION";
	public static final String FIELD_PORTAL_MENU_ID_PORTAL_MENU_PARENT = "ID_PORTAL_MENU_PARENT";
	public static final String FIELD_PORTAL_MENU_HELPTEXT = "HELPTEXT";
	public static final String FIELD_PORTAL_MENU_MENU_INDEX = "MENU_INDEX";
	public static final String FIELD_PORTAL_MENU_AUTHENTICATE = "AUTHENTICATE";

	public static final String TABLE_BI_PORTAL_MENU_ATTRIBUTE = "BI_PORTAL_MENU_ATTRIBUTE";
	public static final String FIELD_PORTAL_MENU_ATTRIBUTE_ID_PORTAL_MENU_ATTR = "ID_PORTAL_MENU_ATTR";
	public static final String FIELD_PORTAL_MENU_ATTRIBUTE_ID_PORTAL_MENU = "ID_PORTAL_MENU";
	public static final String FIELD_PORTAL_MENU_ATTRIBUTE_CODE = "CODE";
	public static final String FIELD_PORTAL_MENU_ATTRIBUTE_VALUE_STR = "VALUE_STR";

	public static final String TABLE_BI_FILESYS_TYPE = "BI_FILESYS_TYPE";
	public static final String FIELD_FILESYS_TYPE_ID_FS_TYPE = "ID_FS_TYPE";
	public static final String FIELD_FILESYS_TYPE_CODE = "CODE";
	public static final String FIELD_FILESYS_TYPE_DESCRIPTION = "DESCRIPTION";

	public static final String TABLE_BI_FILESYS_DIRECTORY = "BI_FILESYS_DIRECTORY";
	public static final String FIELD_FILESYS_DIRECTORY_ID_FS_DIRECTORY = "ID_FS_DIRECTORY";
	public static final String FIELD_FILESYS_DIRECTORY_FS_TYPE = "FS_TYPE";
	public static final String FIELD_FILESYS_DIRECTORY_PATH = "PATH";
	public static final String FIELD_FILESYS_DIRECTORY_DESCRIPTION = "DESCRIPTION";
	public static final String FIELD_FILESYS_DIRECTORY_NOTES = "NOTES";

	public static final String TABLE_BI_REPORT = "BI_REPORT";
	public static final String FIELD_REPORT_ID_REPORT = "ID_REPORT";
	public static final String FIELD_REPORT_ID_REPORT_DIRECTORY = "ID_REPORT_DIRECTORY";
	public static final String FIELD_REPORT_REPORT_OBJECT = "REPORT_OBJECT";
	public static final String FIELD_REPORT_REPORT_TYPE = "REPORT_TYPE";
	public static final String FIELD_REPORT_IS_REF = "IS_REF";
	public static final String FIELD_REPORT_ID_REF_REPORT = "ID_REF_REPORT";
	public static final String FIELD_REPORT_DESCRIPTION = "DESCRIPTION";
	public static final String FIELD_REPORT_REPORT_VERSION = "REPORT_VERSION";
	public static final String FIELD_REPORT_REPORT_STATUS = "REPORT_STATUS";
	public static final String FIELD_REPORT_CREATE_USER = "CREATE_USER";
	public static final String FIELD_REPORT_CREATE_DATE = "CREATE_DATE";
	public static final String FIELD_REPORT_MODIFIED_USER = "MODIFIED_USER";
	public static final String FIELD_REPORT_MODIFIED_DATE = "MODIFIED_DATE";

	public static final String TABLE_BI_DOMAIN = "BI_DOMAIN";
	public static final String FIELD_DOMAIN_ID_DOMAIN = "ID_DOMAIN";
	public static final String FIELD_DOMAIN_ID_DOMAIN_DIRECTORY = "ID_DOMAIN_DIRECTORY";
	public static final String FIELD_DOMAIN_DOMAIN_TYPE = "DOMAIN_TYPE";
	public static final String FIELD_DOMAIN_DESCRIPTION = "DESCRIPTION";
	public static final String FIELD_DOMAIN_DOMAIN_VERSION = "DOMAIN_VERSION";
	public static final String FIELD_DOMAIN_DOMAIN_STATUS = "DOMAIN_STATUS";
	public static final String FIELD_DOMAIN_CREATE_USER = "CREATE_USER";
	public static final String FIELD_DOMAIN_CREATE_DATE = "CREATE_DATE";
	public static final String FIELD_DOMAIN_MODIFIED_USER = "MODIFIED_USER";
	public static final String FIELD_DOMAIN_MODIFIED_DATE = "MODIFIED_DATE";

	public static final String repositoryTableNames[] = new String[] {
			TABLE_BI_AUTO_START_JOB, TABLE_BI_AUTO_START_JOB_ATTR,
			TABLE_BI_TIMED_EXE, TABLE_BI_TIMED_EXE_ATTR, TABLE_BI_MSG_CHANNEL,
			TABLE_BI_MSG, TABLE_BI_FUNC_TYPE, TABLE_BI_FUNC_TYPE_ATTRIBUTE,
			TABLE_BI_PORTAL_MENU, TABLE_BI_PORTAL_MENU_ATTRIBUTE,
			TABLE_BI_FILESYS_TYPE, TABLE_BI_FILESYS_DIRECTORY, TABLE_BI_REPORT,
			TABLE_BI_DOMAIN };
}
