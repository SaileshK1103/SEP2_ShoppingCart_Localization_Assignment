pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        // Global environment variables
        DB_URL = "${params.DB_URL}"
        DB_USERNAME = "${params.DB_USERNAME}"
        DB_PASSWORD = "${params.DB_PASSWORD}"
        // Configuration for the build
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKERHUB_REPO = 'saileshk1103/sep2_shoppingcart_localization_assignment'
        DOCKER_IMAGE_TAG = 'latest'
        // Helper for local tests
        DB_HOST = 'localhost'
        DB_NAME = 'shopping_cart_localization'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test & Coverage') {
            steps {
                sh 'mvn clean jacoco:prepare-agent test jacoco:report'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco()
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