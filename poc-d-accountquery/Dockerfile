FROM java:8-jre
MAINTAINER Equipo de Anzen Soluciones <mcoronel@anzen.com.mx>

ADD ./target/poc-d-accountquery.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/poc-d-accountquery.jar"]

EXPOSE 5000
