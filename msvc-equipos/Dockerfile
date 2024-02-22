#El uso de un Dockerfile permite crear una imagen de Docker que contiene lo necesario para ejecutar la aplicación de
#Spring Boot en un contenedor. Esto facilita el despliegue y la ejecución de la aplicación en cualquier entorno que
#admita Docker
FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-equipos

#Copiamos ejecutables .mvn de msvc-equipos en la carpeta declarada en workdir para agilizar el proceso del build
COPY ./msvc-equipos/.mvn ./.mvn
COPY ./msvc-equipos/mvnw .
COPY ./msvc-equipos/pom.xml .

#Hacer que se salte el test y el main porque no lo necesitamos. Empaquetamos el proyecto sin el código fuente.
#Eliminamos la carpeta target
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-equipos/src ./src

#Generamos el jar en target del WorkDir evitando los tests
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/msvc-equipos/target/msvc-equipos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "msvc-equipos-0.0.1-SNAPSHOT.jar"]