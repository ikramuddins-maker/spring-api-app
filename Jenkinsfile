// Define global variables here so they can hold Objects/Maps, not just Strings
def CONFIG = [:]
def MATCHED_ENV = [:]

pipeline {
    agent any
    tools { maven 'Maven305' }
    
    stages {
        stage('Initialize & Parse') {
            steps {
                script {
                    // readYaml returns a Map object
                    CONFIG = readYaml file: 'project.yaml'
                    
                    // Finding the correct environment block
                    def envMatch = CONFIG.deployment[0].environments.find { it.branch == env.BRANCH_NAME }
                    
                    if (envMatch) {
                        MATCHED_ENV = envMatch
                        echo "Found configuration for ${env.BRANCH_NAME}"
                    } else {
                        error "No config found in project.yaml for branch: ${env.BRANCH_NAME}"
                    }
                }
            }
        }
        
        stage('Build & Sonar') {
            steps {
                // Combined into one clean step. 
                // Note: withSonarQubeEnv provides the URL/Token context automatically from Jenkins global config
                withSonarQubeEnv('sonarqube') {
                    sh "mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                        -Dsonar.projectKey=devops-java-app \
                        -Dsonar.projectName='devops-java-app'"
                }
            }
        }

        stage('Quality Check') {
            steps {
                timeout(time: 10, unit: 'SECONDS') {
                    // This waits for SonarQube to finish processing and report back
                    waitForQualityGate abortPipeline: true
                }       
            }
        }

        stage('Notify') {
            steps {
                // Use double quotes to interpolate the Groovy Object property
                sh "echo Pipeline completed for branch ${env.BRANCH_NAME} with deploy flag ${MATCHED_ENV.deployFlag}"
            }
        }
    }
}