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
		sh 'sudo who'
		sh 'echo $USER'
		sh 'pwd'
		sh 'cd /opt/'
		sh 'pwd'
		sh './gradlew clean build -x test war'		
            }
        }
    }
}
