FROM openjdk:18.0.2.1-jdk AS JDK_18

LABEL authors="upersuser" title="testvkbot"

ENTRYPOINT ["sh", "/opt/app/run.sh"]

ADD build/libs/testvkbot.jar /opt/app/testvkbot.jar
ADD docker/app/run.sh /opt/app