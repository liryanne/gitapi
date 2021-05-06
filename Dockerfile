FROM clojure:openjdk-16-lein AS build
WORKDIR /app
COPY . .
RUN lein uberjar

FROM adoptopenjdk/openjdk16:ubi-minimal-jre
WORKDIR /app
COPY --from=build /app/target/gitapi-standalone.jar gitapi.jar
ENTRYPOINT ["java", "-jar", "gitapi.jar"]
