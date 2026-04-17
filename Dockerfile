# Stage 1: Build (Creates the JAR inside Jenkins)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# We build the JAR here so the next stage can find it
RUN mvn clean package -DskipTests

# Stage 2: Runtime (The final image with fonts)
FROM eclipse-temurin:21-jre

# Install X11, GTK, AND Japanese Fonts
RUN apt-get update && apt-get install -y \
    xvfb \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgtk-3-0 \
    libgl1 \
    libglu1-mesa \
    libglib2.0-0 \
    fonts-ipafont-gothic \
    && rm -rf /var/lib/apt/lists/*

# Keep UTF-8
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

WORKDIR /app

# Copy the JAR from the 'build' stage
COPY --from=build /app/target/shopping_cart.jar app.jar

# Run the app
CMD ["sh", "-c", "Xvfb :99 -screen 0 1024x768x16 & export DISPLAY=:99 && java -Dfile.encoding=UTF-8 -Djava.awt.headless=false -jar app.jar"]