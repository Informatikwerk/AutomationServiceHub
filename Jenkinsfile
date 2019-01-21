pipeline {
    agent {
        docker { dockerfile true }
    }
    stages {
        stage('Test') {
            steps {
                sh './gradlew clean build -x test war'
            }
        }
    }
}