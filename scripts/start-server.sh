#!/bin/bash

# 포트 번호 설정
export SOCKET_PORT_PROD=8082

# JAR 파일 위치로 이동
cd /home/ubuntu/woori-zip-BANK/build/libs

# Spring Boot 애플리케이션 시작 (main 브랜치 환경 설정)
nohup java -Dserver.port=${SOCKET_PORT_PROD} \
-jar bank-0.0.1-SNAPSHOT.jar \
--spring.config.location=file:/home/ec2-user/config/application.yml > /home/ubuntu/woori-zip-BANK/logs/app-prod.log 2>&1 &
