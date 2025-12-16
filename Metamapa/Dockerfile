# ---- build ----
FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /app
COPY . .

# IMPORTANTE: el pom principal está en Metamapa/
RUN mvn -f Metamapa/pom.xml -DskipTests package

# Copiamos jars a /out/<modulo>.jar
RUN set -eux; \
  mkdir -p /out; \
  for d in \
    M-Agregador-Service \
    M-Estadistica-Service \
    M-FuenteDemo-Service \
    M-FuenteDinamica-Service \
    M-FuenteEstatica-Service \
    M-FuenteMetamapa-Service \
    M-Usuarios-Service \
    metamapa-service \
  ; do \
    JAR="$(ls -1 "Metamapa/$d"/target/*.jar 2>/dev/null | grep -vE '(original|sources|javadoc)\.jar$' | head -n 1 || true)"; \
    [ -n "$JAR" ] && cp "$JAR" "/out/$d.jar" || true; \
  done

# ---- run ----
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /out /app

ENV APP=metamapa-service
EXPOSE 10000
CMD ["sh","-c","test -f /app/${APP}.jar || (echo \"Jar no encontrado: /app/${APP}.jar\" && ls -la /app && exit 1); java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar /app/${APP}.jar"]
