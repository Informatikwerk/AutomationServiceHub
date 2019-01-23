pipeline {
    agent {
        dockerfile {
			args '-v /opt/tomcat/.jenkins/workspace/ash_git:/opt -w /opt'	
		}			
    }
	environment {
        HOME = '.'
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
