FROM java:8-jre
MAINTAINER Equipo de Anzen Soluciones <mcoronel@anzen.com.mx>

ADD ./target/poc-p-auth.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/poc-p-auth.jar"]

EXPOSE 5000
