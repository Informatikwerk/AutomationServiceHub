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
		sh 'echo build image...'
		sh 'docker build -t eugen/ssc-app $PWD/.'
		sh 'echo Delete old container...'
		sh 'docker rm -f ssc-app'
		sh 'echo Run new container...'
		sh 'docker run -p 8092:8000 -d --name ssc-app eugen/ssc-ap'
	    }		
        }
    }
	
}
