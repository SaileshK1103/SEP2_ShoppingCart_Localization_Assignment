# Stage 1: Build inside the container
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y libgtk-3-0 libgbm1 libx11-6 && apt-get clean

WORKDIR /app

# Copy the SHADED jar from the build stage
COPY --from=build /app/target/shopping_cart_localization-1.0-SNAPSHOT-shaded.jar app.jar

# Automatic Start Command
# -Dprism.order=sw: Forces software rendering for XQuartz/M1 compatibility
# -Djava.awt.headless=false: Tells Java to allow GUI windows
CMD ["java", "-Dprism.order=sw", "-Djava.awt.headless=false", "-jar", "app.jar"]