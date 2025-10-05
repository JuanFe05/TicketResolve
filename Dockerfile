# Imágen modelo
FROM eclipse-temurin:21.0.8_9-jdk

# Puerto donde se ejecuta el contenedor
EXPOSE 8080

# Directorio Raíz del Contenedor
WORKDIR /root

# Copiar y Pegar archivos dentro del contenedor
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

RUN chmod +x /root/mvnw

# Descargar dependecias del proyecto
RUN ./mvnw dependency:go-offline

# Copiar y Pegar el código fuente dentro del contenedor
COPY ./src /root/src

# Construir Aplicacion
RUN ./mvnw clean install -DskipTests

# Lenvantar Aplicación cuando el contendor inicie
ENTRYPOINT ["java", "-jar", "/root/target/TicketResolve-0.0.1-SNAPSHOT.jar"]