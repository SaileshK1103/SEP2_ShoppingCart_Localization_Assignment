# Shopping Cart Localization Application
**SEP2 | Java GUI Application with Localization, Docker & Kubernetes**

## 🛒 Project Overview
A Java-based shopping cart application designed to demonstrate **Localization (i18n)**, **Unit Testing (JUnit 5)**, and **Cloud-Native Deployment**. The application calculates the total cost of items while supporting multiple languages and strict UTF-8 encoding for international character sets.

---

## 📅 Week 1: Core Logic & CI/CD
The initial phase focused on building the core calculation engine and establishing an automated build pipeline.

### 🚀 Key Features
* **Multi-Language Support**: Supports English, Finnish (Suomi), Swedish (Svenska), and Japanese (日本語).
* **UTF-8 Focused**: Fully compatible with non-Latin characters (Kanji/Kana) across all environments.
* **Logic Separation**: Calculation logic isolated for 100% Unit Test coverage.
* **CI/CD Pipeline**: Automated build, test, and deployment via Jenkins.
* **Multi-Platform Docker**: Built for both `amd64` (Intel/AMD) and `arm64` (Apple Silicon) to ensure compatibility.

### 📊 Code Coverage
The core business logic (`calculateItemTotal`) is verified with **100% branch and line coverage** using JaCoCo.
* **Total Methods Tested**: 100%
* **Total Lines Covered**: 100% (Logic Class)

---

## 📅 Week 2: Containerization & Kubernetes Orchestration
The second phase transitioned the application from a console tool to a containerized GUI application orchestrated via Kubernetes.

### 🚀 Key Features
* **GUI Orchestration**: Successfully migrated the application to run within a **Kubernetes Pod** while maintaining native GUI interaction.
* **X11 Display Bridging**: Integrated with **XQuartz** to tunnel the JavaFX interface from the Linux container to the host macOS.
* **Advanced Localization**: Integrated **Google Noto CJK Fonts** at `/usr/share/fonts/opentype/noto` to ensure Japanese glyph rendering in the container.
* **Declarative Deployment**: Managed environment lifecycle via `deployment.yaml` and `service.yaml` manifests.

### ☸️ Kubernetes Status
* **Pod Stability**: Successfully deployed to a local K8s cluster with 1/1 readiness and stable status.
* **GUI Execution**: Verified the Shopping Cart App window running directly from the Kubernetes lifecycle via X11 forwarding.

---

## 🛠️ Tech Stack
* **Language**: Java 21 (GraalVM/OpenJDK)
* **Orchestration**: Kubernetes (Docker Desktop K8s)
* **Containerization**: Docker & Docker Compose
* **Automation**: Jenkins (Pipeline-as-Code)
* **GUI Bridge**: X11 Forwarding & XQuartz
* **Testing**: JUnit 5 & JaCoCo (Code Coverage)

---

## 🐳 Deployment & Usage
The image is hosted on Docker Hub: `saileshk1103/sep2_shoppingcart_localization_assignment`

### Run with Docker:
```bash
docker run -it saileshk1103/sep2_shoppingcart_localization_assignment:latest