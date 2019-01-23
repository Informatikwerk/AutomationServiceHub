pipeline {
    agent {
        dockerfile true 
    }
    stages {
        stage('Test') {
            steps {
                sh 'docker build -t eugen/ash-app .'
		sh 'docker run -it eugen/ash-app bash'
		sh './gradlew clean build -x test war'
            }
        }
    }
}
