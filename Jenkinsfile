pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                dockerfile {
                        args '-v /opt/tomcat/.jenkins/workspace/automationservicehub:/opt -w /opt'
                }
            }
             environment {
                HOME = '.'
            }
            steps {
                sh 'pwd'
                sh './gradlew clean build -x test war'
            }
        }
	stage('Remote Build') {
	    agent any
	    steps {
		sh 'docker push localhost:5000/automationservicehub'
		sh 'scp -r -P 22 /opt/tomcat/automation/automationservicehub/src/main/docker/app.yml eugen@192.168.175.44:/home/eugen/automation/automationservicehub/app.yml'
		sh 'scp -r -P 22 /opt/tomcat/automation/automationservicehub/src/main/docker/mysql.yml eugen@192.168.175.44:/home/eugen/automation/automationservicehub/mysql.yml'
		sh 'ssh -R 5000:localhost:5000 -l eugen 192.168.175.44'
		sh 'pwd'
		sh 'docker pull localhost:5000/automationservicehub'
		sh 'cd automation/automationservicehub'
		sh 'docker-compose -f app.yml up -d'
	    }
	}
    }

}
