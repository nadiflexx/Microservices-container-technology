#El uso de un Dockerfile permite crear una imagen de Docker que contiene lo necesario para ejecutar la aplicación de
#Spring Boot en un contenedor. Esto facilita el despliegue y la ejecución de la aplicación en cualquier entorno que
#admita Docker
FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]