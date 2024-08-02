FROM tomcat:9.0-jdk8

WORKDIR /usr/local/tomcat

RUN rm -rf webapp/*

COPY build/Assignment_2.war webapp/Assignment_2.war

EXPOSE 8080

CMD ["catalina.sh", "run"]