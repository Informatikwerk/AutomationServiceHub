pipeline {
    agent {
        dockerfile true 
    }
	environment {
        HOME = '.'
    }
    stages {
        stage('Test') {
            steps {
                sh './gradlew clean build -x test war'
            }
        }
    }
}