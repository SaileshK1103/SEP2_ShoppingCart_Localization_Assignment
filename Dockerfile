FROM eclipse-temurin:21-jdk

# 1. Install JavaFX SDK and GUI libraries
RUN apt-get update && apt-get install -y wget unzip libgtk-3-0 libgbm1 libx11-6 && \
    wget https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip && \
    unzip openjfx-21.0.2_linux-x64_bin-sdk.zip -d /opt/ && \
    rm openjfx-21.0.2_linux-x64_bin-sdk.zip

# 2. Localization Requirements
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
ENV DISPLAY=host.docker.internal:0.0

WORKDIR /app

# 3. Copy your 8MB JAR (Matches your artifactId-version)
COPY target/shopping_cart_localization-1.0-SNAPSHOT.jar app.jar

# 4. Entrypoint pointing to the libraries we just downloaded
CMD java -Djava.awt.headless=true \
    --module-path /opt/javafx-sdk-21.0.2/lib \
    --add-modules javafx.controls,javafx.fxml \
    -jar app.jar || tail -f /dev/null