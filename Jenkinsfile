pipeline {
    agent any
    
    tools {
        maven 'M3'
        'org.jenkinsci.plugins.docker.commons.tools.DockerTool' 'docker'
    }

    stages {
        stage('Build & Unit Test') {
            steps {
                // Generate the JAR
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def tag = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    sh "docker build -t ikramuddin001/spring-api-app:${tag} ."
                    sh "docker build -t ikramuddin001/spring-api-app:latest ."
                }
            }
        }
    }
}