#!/usr/bin/env bash

version=1.0-SNAPSHOT
APP_NAME=dafacloud-game-1.0-SNAPSHOT.jar
project_name=dafacloud-game
out=out-dafacloud-game.txt

#is_pull_success(){
#    cd /usr/duke/github
#    git pull git@github.com:dukepython/dafa.git
#    if [[ $? -eq 0 ]]; then
#        return 1
#    else
#        return 0
#    fi
#}

git_pull(){
    echo "拉取最新代码"
    cd /usr/duke/github
    git pull git@github.com:dukepython/dafa.git
}

package(){
    echo "开始打包"
    cd /usr/duke/github/dafacloud
    mvn clean install -Dmaven.test.skip=true -pl ../${project_name} -am
}

is_pid_exist(){
  pid=`ps -ef|grep ${APP_NAME}|grep -v grep|awk '{print $2}'`
  #如果不存在返回1，存在返回0
  if [[ -z "${pid}" ]]; then
   return 1
  else
    return 0
  fi
}

stop(){
    is_pid_exist
    if [[ $? -eq 0 ]]; then
        #echo "${APP_NAME} is already running. pid=${pid}"
        kill -9 ${pid}
        if [[ $? -eq 0 ]]; then
            echo "${APP_NAME}， pid=${pid} kill 成功"
        else
            echo "${APP_NAME}， pid=${pid} kill 失败   "
        fi
    else
        #nohup java -jar ${APP_NAME}  >robotcenter.out 2>&1 &
        echo "${APP_NAME} is not running"
    fi
}

start(){
  is_pid_exist
  if [[ $? -eq 0 ]]; then
    echo "${APP_NAME} is already running. pid=${pid}"
  else
    #cd /usr/duke/github/${project_name}/target/lib
    cd /root/.m2/repository/pers/dafacloud/${project_name}/1.0-SNAPSHOT
    rm -rf /usr/duke/${out}
    nohup java -jar ${APP_NAME}  > /usr/duke/${out}
    echo "${APP_NAME} 启动成功"
  fi
}

#备份文件
backups_file(){
    cd /usr/duke
    if [[ ! -f ${out} ]]; then
        echo "备份${out}不存在，跳过备份"
    else
        cp ${out} "webapiDir/${out%.*}`date +%Y-%m-%d-%H%M%S`.txt"
        echo "备份${out}"
        rm -rf ${out}
        echo "删除原文件：${out}"
    fi
}

restart(){
  stop
  sleep 5
  start
}

pull_package(){
    git_pull
    package
}

pull_package_restart(){
    git_pull
    package
    restart
}

#输出运行状态
status(){
  is_exist
  if [[ $? -eq "0" ]]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
   "pullPackage")
    pull_package
    ;;
  "pullPackageRestart")
    pull_package_restart
    ;;
  "backups")
    backups_file
    ;;
  *)
    usage
    ;;
esac

#java -jar /usr/duke/github/dafagame-client-bf/target/dafagame-client-bf-1.0.0.jar
#nohup ./runjar.sh > hzqout.txt