# Stage 1: Build inside the container
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre

# --- CRITICAL FOR JAPANESE/ARABIC/FINNISH SUPPORT ---
# Force the container OS to use UTF-8 instead of ASCII
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# Install required Linux libraries for JavaFX and fonts for CJK characters
RUN apt-get update && apt-get install -y \
    libgtk-3-0 libgbm1 libx11-6 \
    fonts-noto-cjk \
    && apt-get clean

WORKDIR /app

# Copy the SHADED jar from the build stage
COPY --from=build /app/target/shopping_cart_localization-1.0-SNAPSHOT-shaded.jar app.jar

# --- AUTOMATIC START COMMAND ---
# -Dfile.encoding=UTF-8: Ensures Java reads DB strings correctly
# -Dprism.order=sw: Software rendering for X11/Docker compatibility
# -Djava.awt.headless=false: Required to open the JavaFX window
CMD ["java", "-Dfile.encoding=UTF-8", "-Dprism.order=sw", "-Djava.awt.headless=false", "-jar", "app.jar"]