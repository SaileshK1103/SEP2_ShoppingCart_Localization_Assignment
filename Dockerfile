# Use JDK image (Ubuntu-based) instead of Alpine to ensure JavaFX compatibility
FROM eclipse-temurin:21-jdk

# 1. Keep your Localization Requirements (UTF-8)
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
# 2. Add Teacher's Display Requirement
ENV DISPLAY=host.docker.internal:0.0

# 3. Install the GUI libraries your teacher included (essential for JavaFX)
RUN apt-get update && \
    apt-get install -y libgtk-3-0 libgbm1 libx11-6 && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# 4. Copy the JAR built by your Jenkins 'Build' stage
COPY target/ShoppingCartApp.jar app.jar

# 5. Entrypoint with your UTF-8 flags for perfect localization
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-jar", "app.jar"]