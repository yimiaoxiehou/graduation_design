FROM java:8
VOLUME /tmp
ADD gateway-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENV HOST_NAME=127.0.0.1
ENV PORT=8640
ENV EUREK_HOST=eureka.yimiaohome.top
ENV PROFILE=release
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","/app.jar"]
EXPOSE ${PORT}
