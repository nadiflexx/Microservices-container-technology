#El uso de un Dockerfile permite crear una imagen de Docker que contiene lo necesario para ejecutar la aplicación de
#Spring Boot en un contenedor. Esto facilita el despliegue y la ejecución de la aplicación en cualquier entorno que
#admita Docker
FROM openjdk:17.0.2

WORKDIR /app/msvc-usuarios

#Copiamos ejecutables .mvn de msvc-usuarios en la carpeta declarada en workdir para agilizar el proceso del build
COPY ./msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

#Hacer que se salte el test y el main porque no lo necesitamos. Empaquetamos el proyecto sin el código fuente.
#Eliminamos la carpeta target
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-usuarios/src ./src

#Generamos el jar en target del WorkDir evitando los tests
RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]