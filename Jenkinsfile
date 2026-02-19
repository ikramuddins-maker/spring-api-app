pipeline {
    agent any
    tools { maven 'M3' }
    environment {
        CONFIG = ""
        MATCHED_ENV = ""
    }
    stages {
        stage('Initialize & Parse') {
            steps {
                script {
                    CONFIG = readYaml file: 'project.yaml'
                    MATCHED_ENV = CONFIG.deployment[0].environments.find { it.branch == env.BRANCH_NAME }
                    if (!MATCHED_ENV) { error "No config for branch ${env.BRANCH_NAME}" }
                }
            }
        }
        stage('Build & Sonar') {
            steps {
                sh 'mvn clean package -DskipTests'
                withSonarQubeEnv('sonarqube') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=${CONFIG.build.name} -X" // SonarQube to send logs to Console Output
                }
            }
        }
        stage('Quality Check') {
            steps {
                 timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                 }       
             }
        }

        stage('Docker Build & Push') {
            when { expression { return MATCHED_ENV.deployFlag == 'enable' } }
            steps {
                script {
                    def tag = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    sh "docker build -t ikramuddin001/spring-api-app:${tag} ."
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh "echo \$PASS | docker login -u \$USER --password-stdin"
                        sh "docker push ikramuddin001/spring-api-app:${tag}"
                    }
                }
            }
        }
        
        stage('Deploy to K8s') {
            when { expression { return MATCHED_ENV.deployFlag == 'enable' } }
            steps {
                sh "kubectl create namespace ${MATCHED_ENV.spaceName} --dry-run=client -o yaml | kubectl apply -f -"
                sh "echo 'Simulated Deployment of ${CONFIG.build.name} to ${MATCHED_ENV.spaceName}'"
                // For a real pod: sh "kubectl run my-app --image=ikramuddin001/spring-api-app:latest -n ${MATCHED_ENV.spaceName}"
            }
        }
    }
}