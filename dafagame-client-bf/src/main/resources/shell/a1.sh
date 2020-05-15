#!/usr/bin/env bash

APP_NAME=dafagame-client-bf-1.0.0.jar

echo "拉取最新代码"
cd /usr/duke/github
git pull git@github.com:dukepython/dafa.git
echo "开始打包"
cd /usr/duke/github/dafacloud
mvn clean install -Dmaven.test.skip=true -pl ../dafagame-client-bf -am
echo "开始检查程序是否在运行"

is_exist(){
  pid=`ps -ef|grep ${APP_NAME}|grep -v grep|awk '{print $2}'`
  #如果不存在返回1，存在返回0
  if [[ -z "${pid}" ]]; then
   return 1
  else
    return 0
  fi
}

is_exist
if [[ $? -eq 0 ]]; then
 echo "${APP_NAME} is already running. pid=${pid}"
else
#nohup java -jar ${APP_NAME}  >robotcenter.out 2>&1 &
 echo "${APP_NAME} is not running"
fi

#java -jar /usr/duke/github/dafagame-client-bf/target/dafagame-client-bf-1.0.0.jar
#nohup ./runjar.sh > hzqout.txt