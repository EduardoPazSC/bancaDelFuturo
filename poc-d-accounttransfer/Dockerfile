FROM java:8-jre
MAINTAINER Equipo de Anzen Soluciones <mcoronel@anzen.com.mx>

ADD ./target/poc-d-accounttransfer.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/poc-d-accounttransfer.jar"]

EXPOSE 5000
