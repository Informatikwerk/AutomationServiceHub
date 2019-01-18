pipeline {
    agent {
        docker { image 'openjdk:8-jre-alpine' }
    }
	agent {
		docker { image 'node:8.15.0-alpine' }
	}
    stages {
        stage('Test') {
            steps {
                sh './gradlew clean build -x test war'
            }
        }
    }
}