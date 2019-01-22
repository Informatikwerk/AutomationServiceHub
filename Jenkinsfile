pipeline {
    agent {
        dockerfile {
			args '-v /opt/tomcat/.jenkins/workspace/ash_git:/usr/src/app -w /usr/src/app'	
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