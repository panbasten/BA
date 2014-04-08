
rem example script for MondrianFoodMartLoader

@echo off
set CP=../lib/commons-dbcp.jar;../lib/commons-collections.jar;../lib/commons-pool.jar
set CP=%CP%;../lib/eigenbase-properties.jar;../lib/eigenbase-resgen.jar;../lib/eigenbase-xom.jar
set CP=%CP%;../lib/javacup.jar;../lib/mondrian.jar
set CP=%CP%;../lib/log4j.jar
set CP=%CP%;../dev-lib/mysql-connector-java-5.1.13-bin.jar



java -Xms100m -Xmx500m -cp "%CP%" -Dlog4j.configuration=file:///C:/Temp/runnerLog4j.xml mondrian.test.loader.MondrianFoodMartLoader -tables -data -indexes -inputFile="C:/_D/_code/Pentaho/git/_trunk/mondrian/demo/FoodMartCreateData.sql" -jdbcDrivers=com.mysql.jdbc.Driver -outputJdbcURL="jdbc:mysql://localhost/foodmart" -outputJdbcUser=root -outputJdbcPassword=root