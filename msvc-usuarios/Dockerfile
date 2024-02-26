#El uso de un Dockerfile permite crear una imagen de Docker que contiene lo necesario para ejecutar la aplicación de
#Spring Boot en un contenedor. Esto facilita el despliegue y la ejecución de la aplicación en cualquier entorno que
#admita Docker
FROM openjdk:17-jdk-alpine as builder
ARG MSVC_NAME=msvc-usuarios
WORKDIR /app/$MSVC_NAME

#Copiamos ejecutables .mvn de msvc-usuarios en la carpeta declarada en workdir para agilizar el proceso del build
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

#Hacer que se salte el test y el main porque no lo necesitamos. Empaquetamos el proyecto sin el código fuente.
#Eliminamos la carpeta target
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MSVC_NAME/src ./src

#Generamos el jar en target del WorkDir evitando los tests
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar .
ENV PORT 8001
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]