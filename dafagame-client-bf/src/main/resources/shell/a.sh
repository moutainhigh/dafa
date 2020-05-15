#!/usr/bin/env bash

APP_NAME=dafagame-client-bf.jar
is_exist(){
  pid=`ps -ef|grep ${APP_NAME}|grep -v grep|awk '{print $2}'`
  #如果不存在返回1，存在返回0
  if [[ -z "${pid}" ]]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [[ $? -eq 0 ]]; then
    echo "${APP_NAME} is already running. pid=${pid}"
  else
    #nohup java -jar ${APP_NAME}  >robotcenter.out 2>&1 &
    echo "${APP_NAME} not running"
  fi
}