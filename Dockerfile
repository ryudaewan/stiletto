FROM adoptopenjdk:11-jre-openj9
ENV LC_ALL=ko_KR.UTF-8
RUN mkdir /stiletto
COPY build/libs/stiletto-0.0.1-SNAPSHOT.jar /stiletto
WORKDIR /stiletto
CMD java -jar /stiletto/stiletto-0.0.1-SNAPSHOT.jar
