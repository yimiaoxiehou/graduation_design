FROM java:8
VOLUME /tmp
ADD eureka-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENV HOST_NAME=eureka.yimiao.tk
ENV PORT=8761
ENV PROFILE=release
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","/app.jar"]
EXPOSE ${PORT}