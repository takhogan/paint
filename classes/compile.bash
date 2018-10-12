#!/usr/bin/env bash
SAP="-cp .:$HOME/Documents/webProject/tomcat/lib/servlet-api.jar"
MBP=".:$HOME/Documents/webProject/mybatis-3.4.5/mybatis-3.4.5.jar"
MSC="$HOME/Documents/webProject/mysql-connector-java-8.0.11/mysql-connector-java-8.0.11.jar"
JSM="javac $SAP:$MBP:$MSC"
cd ~/Documents/webProject/tomcat/webapps/paint/WEB-INF/classes
$JSM PortfolioServlet.java
$JSM TradeExecute.java
$JSM TradeServlet.java
$JSM MarketServlet.java
$JSM MyBatisUtil.java

