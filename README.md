# Shopping Cart Localization Application
**SEP2 | Java GUI Application with Localization, Docker & Kubernetes**

## 🛒 Project Overview
A Java-based shopping cart application designed to demonstrate **Localization (i18n)**, **Database Persistence**, **Unit Testing (JUnit 5)**, and **Cloud-Native Deployment**. The application calculates total costs while supporting multiple languages and strict UTF-8 encoding for international character sets.

---

## 📂 Project Structure
```text
shopping_cart_localization/
├── src/
│   ├── main/
│   │   ├── java/com/assignment/
│   │   │   ├── data/DatabaseConnection.java     # MariaDB Connection Logic
│   │   │   ├── logic/CalculationLogic.java      # Core Business Logic (100% Coverage)
│   │   │   └── ui/ShoppingCartApp.java          # JavaFX GUI
│   │   └── resources/                           # Resource Bundles & FXML
│   └── test/java/com/assignment/                # JUnit 5 Test Suites
├── deployment.yaml                              # Kubernetes Manifest (App, DB, Service)
├── Jenkinsfile                                  # CI/CD Pipeline Configuration
├── Dockerfile                                   # Multi-stage Container Definition
├── pom.xml                                      # Maven Project & Dependency Mgmt
└── README.md                                    # Project Documentation
```

## 📅 Week 1-4: Core Logic, DB & CI/CD
Focused on building the calculation engine, database integration, and establishing an automated build pipeline.

### 🚀 Key Features
* **Multi-Language Support**: Supports English, Finnish (Suomi), Swedish (Svenska), Japanese (日本語), and Arabic (العربية).
* **Database Integration**: Connected to a **MariaDB** backend to store and retrieve localized data.
* **CI/CD Pipeline**: Fully automated build, test, and deployment via **Jenkins**.
* **Multi-Platform Docker**: Images built for both `amd64` and `arm64` (Apple Silicon) compatibility.

---

## 📅 Week 5: Kubernetes Orchestration & Quality Assurance
Transitioned the application to a containerized microservice architecture while meeting strict quality and testing standards.

### 🚀 Key Features
* **GUI Orchestration**: Successfully migrated the application to run within a **Kubernetes Pod** while maintaining native GUI interaction.
* **Secrets Management**: Implemented secure credential handling using **Kubernetes Secrets** for database authentication.
* **X11 Display Bridging**: Integrated with **XQuartz** to tunnel the JavaFX interface from the Linux K8s container to the host macOS.
* **Declarative Manifests**: Managed environment lifecycle via a unified `deployment.yaml` including App, Database, and Service definitions.

### 📊 Quality Assurance & Testing (Assignment Requirements)
* **SonarQube Analysis**: Verified code quality and security. The project maintains a **Total Coverage > 80%**, meeting the formal quality gate requirements.
* **Unit Testing**: Core business logic verified with **100% branch and line coverage** using JUnit 5 and JaCoCo.
* **UAT (User Acceptance Testing)**: Final project verified through a formal User Acceptance Test (UAT) suite, documenting UI behavior, localization accuracy, and database connectivity.

---

## 🛠️ Tech Stack
* **Language**: Java 21 (GraalVM/OpenJDK)
* **Database**: MariaDB 10.6
* **Orchestration**: Kubernetes (Docker Desktop K8s)
* **Automation**: Jenkins (Pipeline-as-Code) & SonarQube
* **GUI Bridge**: X11 Forwarding & XQuartz
* **Testing**: JUnit 5, JaCoCo & UAT

---

## 🐳 Deployment & Usage

### 1. Prerequisites
* Install **XQuartz** on macOS.
* In XQuartz Settings -> Security, check **"Allow connections from network clients"**.
* Restart XQuartz.

### 2. Deploy to Kubernetes
Run the following commands in your terminal to initialize the bridge and launch the cluster:

```bash
# 1. Authorize the screen connection
xhost +localhost

# 2. Apply the Kubernetes configuration (App, DB, and Secrets)
kubectl apply -f deployment.yaml

# 3. Follow logs to confirm successful startup and DB connection
kubectl logs -f deployment/shopping-cart-deployment