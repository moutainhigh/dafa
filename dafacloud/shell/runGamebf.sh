#!/usr/bin/env bash

APP_NAME=dafagame-client-bf-1.0.0.jar
out=dafagamebf.txt
pokerdata=pokerdata.txt

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
    mvn clean install -Dmaven.test.skip=true -pl ../dafagame-client-bf -am
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
    cd /usr/duke/github/dafagame-client-bf/target/lib
    nohup java -jar ${APP_NAME} "dev"  > /usr/duke/dafagamebf.txt
    echo "${APP_NAME} 启动成功"
  fi
}

backups_file(){
    cd /usr/duke
    if [[ ! -f ${out} ]]; then
        echo "备份${out}不存在，跳过备份"
    else
        cp ${out} "gamebf/${out%.*}`date +%Y-%m-%d-%H%M%S`.txt"
        echo "备份${out}"
        rm -rf ${out}
        echo "删除原文件：${out}"
    fi
    #
    if [[ ! -f ${pokerdata} ]]; then
        echo "备份${pokerdata}不存在，创建文件"
        mkdir ${pokerdata}
    else
        if [[ -s ${pokerdata} ]]; then
            cp ${pokerdata} "gamebf/${pokerdata%.*}`date +%Y-%m-%d-%H%M%S`.txt"
            echo "备份${pokerdata}成功"
            cat /dev/null > ${pokerdata}
            echo "清空原文件：${pokerdata}成功  "
        else
            echo "空文件：${pokerdata} 调过执行"
        fi
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