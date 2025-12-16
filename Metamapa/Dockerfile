# ---- build ----
FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /src
COPY . .

# Compilar solo estos 2 módulos (y sus dependencias si hacen falta)
RUN mvn -f Metamapa/pom.xml -DskipTests -pl Metamapa-Service,M-Agregador-Service -am clean package

# Copiar jars a /out con nombres fijos
RUN set -eux; \
  mkdir -p /out; \
  for d in Metamapa-Service M-Agregador-Service; do \
    JAR="$(ls -1 Metamapa/$d/target/*.jar 2>/dev/null | grep -vE '(original|sources|javadoc)\.jar$' | head -n 1)"; \
    cp "$JAR" "/out/$d.jar"; \
  done; \
  ls -la /out

# ---- run ----
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /out /app

# Default (Render puede pisarlo)
ENV APP=Metamapa-Service
EXPOSE 10000

CMD ["sh","-c", "set -e; JAR=/app/${APP}.jar; test -f \"$JAR\" || (echo \"Jar no encontrado: $JAR\"; ls -la /app; exit 1); exec java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar \"$JAR\""]
