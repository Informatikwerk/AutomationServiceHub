pipeline {
    agent {
        dockerfile {
			args '-v /opt/tomcat/.jenkins/workspace/automationservicehub:/opt -w /opt'	
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
	stage('Build') {
	    steps {
		sh 'SSC_HOME=$PWD'
		sh 'installOnlyDocker.sh'
	    }		
        }
    }
	
}
