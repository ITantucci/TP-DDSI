# ---- build ----
FROM maven:3.9.11-eclipse-temurin-24 AS build

WORKDIR /app
COPY . .

# El pom padre está en Metamapa/
WORKDIR /app/TP-DDSI/Metamapa
RUN mvn -DskipTests package

# Copiamos jars a /out/<modulo-lower>.jar
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
    Metamapa-Service \
  ; do \
    JAR="$(ls -1 "$d"/target/*.jar 2>/dev/null | grep -vE '(original|sources|javadoc)\.jar$' | head -n 1 || true)"; \
    if [ -n "$JAR" ]; then \
      OUT="/out/$(echo "$d" | tr '[:upper:]' '[:lower:]').jar"; \
      cp "$JAR" "$OUT"; \
      echo "copied $JAR -> $OUT"; \
    else \
      echo "no jar for $d"; \
    fi; \
  done; \
  ls -la /out


# ---- run ----
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /out /app

# por defecto corre Metamapa-Service => metamapa-service.jar
ENV APP=metamapa-service
EXPOSE 10000

CMD ["sh","-c","test -f /app/${APP}.jar || (echo \"Jar no encontrado: /app/${APP}.jar\" && echo \"Contenido de /app:\" && ls -la /app && exit 1); java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar /app/${APP}.jar"]
