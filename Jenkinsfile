pipeline {
    agent any
    tools {
        jdk 'jdk21'
        maven 'Maven3'

    }

    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        // Global environment variables
        DB_USER = "${params.DB_USER}"
        DB_PASSWORD = "${params.DB_PASSWORD}"
        DB_NAME = 'shopping_cart_localization'
        DB_HOST = "db-service"
        DB_URL = "jdbc:mysql://${DB_HOST}:3307/${DB_NAME}"
        // Configuration for the build
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKERHUB_REPO = 'saileshk1103/sep2_shoppingcart_localization_assignment'
        DOCKER_IMAGE_TAG = 'latest'

    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test & Coverage') {
            steps {
                // This line is updated to use localhost specifically for the test phase
                sh 'mvn clean jacoco:prepare-agent test jacoco:report -Djava.awt.headless=true -DDB_HOST=localhost -DDB_USER=${DB_USER} -DDB_PASSWORD=${DB_PASSWORD}'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco()
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    withCredentials([string(credentialsId: 'SONAR_TOKEN_ID', variable: 'SONAR_TOKEN')]) {
                        sh "mvn sonar:sonar -Dsonar.projectKey=shopping_cart_localization -Dsonar.token=${SONAR_TOKEN}"
                    }
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: env.DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        sh "docker login -u ${USER} -p ${PASS}"

                        // Multi-platform build to support both Intel and Apple Silicon
                        sh "docker buildx build --platform linux/arm64 -t ${env.DOCKERHUB_REPO}:${env.DOCKER_IMAGE_TAG} . --push"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Apply the deployment and service files directly
                    sh 'kubectl apply -f deployment.yaml'

                    // Force Kubernetes to pull the new image you just pushed
                    sh 'kubectl rollout restart deployment shopping-cart-deployment'
                }
            }
        }
    }
    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build, Test, and Docker Push succeeded!'
        }
        failure {
            echo 'Pipeline failed. Please check the unit test results or Docker logs.'
        }
    }
}