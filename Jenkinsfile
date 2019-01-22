pipeline {
    agent {
        dockerfile {
			args '-v ${PWD}:/usr/src/app -w /usr/src/app'	
		}			
    }
	environment {
        HOME = '.'
    }
    stages {
        stage('Test') {
            steps {
				sh 'pwd'
                sh './gradlew clean build -x test war'
            }
        }
    }
}