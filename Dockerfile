# SKIP STAGE 1 (MAVEN BUILD) TO SAVE TIME
FROM eclipse-temurin:21-jre

# Install X11, GTK, AND Japanese Fonts (Very fast layer)
RUN apt-get update && apt-get install -y \
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

# COPY the JAR directly from your local folder (Since it's already built)
COPY target/shopping_cart_localization-1.0-SNAPSHOT-shaded.jar app.jar

# Run the app
CMD ["java", "-Dfile.encoding=UTF-8", "-Djava.awt.headless=false", "-jar", "app.jar"]