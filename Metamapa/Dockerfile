# ---- build ----
FROM maven:3.9.11-eclipse-temurin-24 AS build

WORKDIR /app
COPY . .

# Compilamos SOLO el módulo que querés deployar
ARG MODULE=M-Agregador-Service
WORKDIR /app/Metamapa/${MODULE}

RUN mvn -DskipTests package

RUN set -eux; \
  mkdir -p /out; \
  JAR="$(ls -1 target/*.jar | grep -vE '(original|sources|javadoc)\.jar$' | head -n 1)"; \
  cp "$JAR" /out/app.jar; \
  ls -la /out

# ---- run ----
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /out/app.jar /app/app.jar

EXPOSE 10000
CMD ["sh","-c","java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
