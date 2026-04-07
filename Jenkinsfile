pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        PATH = "/usr/local/bin:${env.PATH}"

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
                sh 'mvn clean test jacoco:report'
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
                        sh "docker buildx build --platform linux/amd64,linux/arm64 -t ${env.DOCKERHUB_REPO}:${env.DOCKER_IMAGE_TAG} . --push"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
                    steps {
                        script {
                            // This pulls the secret from Jenkins and puts it into variables
                            withCredentials([usernamePassword(credentialsId: 'DB_CREDENTIALS', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASSWORD'),
                                             password(credentialsId: 'DB_ROOT_ID', variable: 'DB_ROOT_PASSWORD')]) {

                                // Use 'envsubst' to replace the ${VARIABLES} in your YAML with the real ones
                                sh "envsubst < deployment.yaml | kubectl apply -f -"
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