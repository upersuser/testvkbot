FROM openjdk:21-jdk as JDK21

LABEL authors="upersuser" title="testvkbot"

ENTRYPOINT ["sh", "/opt/app/run.sh"]

ADD build/libs/testvkbot.jar /opt/app/testvkbot.jar
ADD docker/app/run.sh /opt/app