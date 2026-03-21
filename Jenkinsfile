pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKERHUB_REPO = 'saileshk1103/sep2_shoppingcart_localization_assignment'
        DOCKER_IMAGE_TAG = 'latest'
        // Ensures the buildx and docker commands are found on macOS/Linux
        PATH = "/usr/local/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pulls the code from your GitHub repository
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Compiles, runs JUnit 5 tests, and generates the JAR
                sh 'mvn clean package'
            }
            post {
                always {
                    // Publishes JUnit results and JaCoCo coverage to Jenkins Dashboard
                    junit '**/target/surefire-reports/*.xml'
                    jacoco()
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    // Securely login using Jenkins credentials
                    withCredentials([usernamePassword(credentialsId: env.DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        sh "docker login -u ${USER} -p ${PASS}"

                        // MULTI-ARCH BUILD (Requirement 3 & 5)
                        // This ensures it works on 'Play with Docker' (Intel) and your Mac (ARM)
                        sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${env.DOCKERHUB_REPO}:${env.DOCKER_IMAGE_TAG} . --push"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Successfully deployed to Docker Hub: ${env.DOCKERHUB_REPO}"
        }
        failure {
            echo "Pipeline failed. Please check the Console Output."
        }
    }
}