# ---- build ----
FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /src
COPY . .

# Build del reactor (parent pom dentro de Metamapa/)
RUN mvn -f Metamapa/pom.xml -DskipTests clean package

# Copiamos jars a /out/<nombre_modulo>.jar (nombre = carpeta del módulo)
RUN set -eux; \
  mkdir -p /out; \
  find Metamapa -type f -path "*/target/*.jar" \
    ! -name "*original*" ! -name "*sources*" ! -name "*javadoc*" \
  | while read -r jar; do \
      mod="$(echo "$jar" | cut -d/ -f2)"; \
      cp "$jar" "/out/${mod}.jar"; \
    done; \
  ls -la /out

# ---- run ----
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /out/ /app/

# Elegís qué jar correr con APP (por defecto uno)
ENV APP=metamapa-service
EXPOSE 10000

CMD ["sh","-c", "\
  set -e; \
  JAR=/app/${APP}.jar; \
  test -f \"$JAR\" || (echo \"Jar no encontrado: $JAR\"; echo \"Disponibles:\"; ls -1 /app/*.jar; exit 1); \
  exec java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar \"$JAR\" \
"]
