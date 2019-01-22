pipeline {
    agent {
        dockerfile true 
    }
	environment {
        HOME = '.'
    }e
    stages {
        stage('Test') {
            steps {
                sh './gradlew clean build -x test war'
            }
        }
    }
}