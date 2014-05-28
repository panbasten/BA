@echo off

set HOME=.
set JAVA_EXEC=java

set CP=%HOME%
set CP=%CP%;%HOME%\lib\dom4j-1.6.1.jar
set CP=%CP%;%HOME%\lib\langtool.jar



%JAVA_EXEC% -cp %CP% uap.lang.application.MultiLangGenerator setting.cfg
pause
