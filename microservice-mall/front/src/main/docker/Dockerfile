FROM java:8
VOLUME /tmp
ADD front-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENV HOST_NAME=127.0.0.1
ENV PORT=7777
ENV PROFILE=release
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","/app.jar"]
EXPOSE ${PORT}
