pipeline {
    agent {
        docker { image 'node:7-alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh './gradlew clean build -x test war'
            }
        }
    }
}