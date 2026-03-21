# Use a lightweight Java 21 Runtime
FROM eclipse-temurin:21-jre-alpine

# Requirement: Focus on UTF-8
# We set the environment to UTF-8 to support Japanese/Finnish/Swedish
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8

WORKDIR /app

# Copy the JAR built by Jenkins (matches your pom.xml <finalName>)
COPY target/ShoppingCartApp.jar app.jar

# Run the application with UTF-8 flags
# This ensures the Japanese characters display correctly in any terminal
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-jar", "app.jar"]