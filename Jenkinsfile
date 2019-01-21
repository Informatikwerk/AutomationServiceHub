pipeline {
    agent {
        docker { image 'openjdk:8-jre-alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh './gradlew clean build -x test war --stacktrace'
            }
        }
    }
}