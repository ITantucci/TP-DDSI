# Basado en la versión 17 (según tu pom.xml)
FROM eclipse-temurin:24-jdk

WORKDIR /app

# Copiamos el .jar que genera Maven. El nombre se deduce del artifactId de tu pom.xml (M-Agregador-Service-1.0.0.jar)
# Usamos un alias genérico app.jar
COPY target/*.jar app.jar

# Puerto de la app (9004) y el puerto de management/actuator (8080)
EXPOSE 9004 8080

ENTRYPOINT ["java", "--enable-native-access=ALL-UNNAMED", "-jar", "app.jar"]