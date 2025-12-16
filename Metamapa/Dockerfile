ARG MODULE=Metamapa-Service

# ---- build ----
FROM maven:3.9.11-eclipse-temurin-24 AS build
ARG MODULE
WORKDIR /src
COPY . .

RUN mvn -f Metamapa/pom.xml -DskipTests -pl ${MODULE} -am clean package

RUN set -eux; \
  mkdir -p /out; \
  JAR="$(ls -1 Metamapa/${MODULE}/target/*.jar | grep -vE '(original|sources|javadoc)\.jar$' | head -n 1)"; \
  cp "$JAR" /out/app.jar

# ---- run ----
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /out/app.jar /app/app.jar
EXPOSE 10000
CMD ["sh","-c","exec java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
