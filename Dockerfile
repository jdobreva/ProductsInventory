FROM eclipse-temurin:21-jdk-alpine

WORKDIR /application

# Copy built JAR
COPY target/*.jar ProductsInventory.jar

# Optional JVM tuning (safe defaults)
ENV JAVA_OPTS="-Xms256m -Xmx1024m -XX:+UseContainerSupport"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /application/ProductsInventory.jar"]