pipeline {
    agent any
    
    environment {
        // Read the YAML file into an object
        CONFIG = "" 
    }

    stages {
        stage('Initialize Configuration') {
            steps {
                script {
                    // Read and parse the YAML
                    def yamlData = readYaml file: 'project.yaml'
                    CONFIG = yamlData
                    echo "Starting build for: ${CONFIG.project.name}"
                }
            }
        }

        stage('Build & Sonar') {
            steps {
                // Use the tool name defined in your YAML
                sh 'mvn clean package -DskipTests'
                withSonarQubeEnv('sonarqube') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=${CONFIG.analysis.sonarProjectKey}"
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    def tag = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    // Dynamic image name from YAML!
                    def fullImage = "${CONFIG.docker.imageName}:${tag}"
                    
                    sh "docker build -t ${fullImage} ."
                    
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh "echo \$PASS | docker login -u \$USER --password-stdin"
                        sh "docker push ${fullImage}"
                    }
                }
            }
        }
    }
}