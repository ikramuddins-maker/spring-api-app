pipeline {
    agent any
    
    tools {
        maven 'M3'
    }

    stages {
        stage('Build & Sonar') {
            steps {
                sh 'mvn clean package -DskipTests'
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    def tag = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    def imageName = "ikramuddin001/spring-api-app"
                    
                    // Build
                    sh "docker build -t ${imageName}:${tag} ."
                    sh "docker build -t ${imageName}:latest ."
                    
                    // Push
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                        sh "docker push ${imageName}:${tag}"
                        sh "docker push ${imageName}:latest"
                    }
                }
            }
        }
    }
}