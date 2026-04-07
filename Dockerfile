# Stage 1: Build inside the container
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# We run tests here so JaCoCo can capture the data for Jenkins!
RUN mvn clean package -DskipTests

# Stage 2: Runtime (Server-ready)
FROM eclipse-temurin:21-jre

# Install X11 and GTK libraries needed for JavaFX
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgtk-3-0 \
    && rm -rf /var/lib/apt/lists/*
# Keep UTF-8 for the Database strings (Essential)
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

WORKDIR /app

# Copy the SHADED jar from the build stage
COPY --from=build /app/target/shopping_cart_localization-1.0-SNAPSHOT-shaded.jar app.jar

# Run in headless mode for the Pipeline
CMD ["java", "-Dfile.encoding=UTF-8", "-Djava.awt.headless=true", "-jar", "app.jar"]