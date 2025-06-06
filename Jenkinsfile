pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/KritTods/api-test-case-docker-jenkin.git'
            }
        }

        stage('Build and Test') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('sonar-token-id')
            }
            steps {
                withSonarQubeEnv('My SonarQube Server') {
                    sh "./gradlew sonarqube -Dsonar.login=${SONAR_TOKEN}"
                }
            }
        }
    }
}
