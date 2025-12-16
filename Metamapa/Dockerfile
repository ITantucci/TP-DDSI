# ---- build ----
FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /src
COPY . .

# Parent pom está en Metamapa/
WORKDIR /src/Metamapa

# Compilar SOLO estos 2 módulos (y lo necesario con -am)
RUN mvn -DskipTests -pl Metamapa-Service,M-Agregador-Service -am clean package

# Copiar jars a /out/<modulo>.jar
RUN set -eux; \
  mkdir -p /out; \
  for d in Metamapa-Service M-Agregador-Service; do \
    JAR="$(ls -1 "$d"/target/*.jar 2>/dev/null | grep -vE '(original|sources|javadoc)\.jar$' | head -n 1)"; \
    cp "$JAR" "/out/$d.jar"; \
  done; \
  ls -la /out

# ---- run ----
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /out /app

# Elegís cuál correr desde Render (default: Metamapa-Service)
ENV APP=Metamapa-Service
EXPOSE 10000

CMD ["sh","-c", "set -e; JAR=/app/${APP}.jar; test -f \"$JAR\" || (echo \"Jar no encontrado: $JAR\"; ls -la /app; exit 1); exec java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar \"$JAR\""]
