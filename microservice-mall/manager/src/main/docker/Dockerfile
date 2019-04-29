FROM java:8
VOLUME /tmp
ADD manager-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENV HOST_NAME=127.0.0.1
ENV PORT=7778
ENV EUREK_HOST=eureka.yimiaohome.top
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE ${PORT}