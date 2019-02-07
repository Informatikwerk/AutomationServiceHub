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
        stage('Deploy') {
            agent { label 'master' }
            steps {
                sh 'ASH_HOME=$PWD'
                sh 'echo Calling ------------- $ASH_HOME/gradlew bootRepackage -Pprod buildDocker'
                sh './gradlew bootRepackage -Pprod buildDocker --stacktrace'
                sh 'echo Calling ------------- $ASH_HOME/docker-compose -f src/main/docker/app.yml up -d'
		sh 'docker image tag gatewayeventhub localhost:5000/automationservicehub'
                sh 'docker-compose -f src/main/docker/app.yml up -d'
            }
        }
		stage('Remote Build') {
			agent { label 'master' }
			steps {
				sshagent (['b857f680-137f-4664-8478-c76098a49af7']) {
					sh 'docker push localhost:5000/automationservicehub'
					sh 'scp -r -P 22 /opt/tomcat/automation/automationservicehub/src/main/docker/app.yml eugen@192.168.175.49:/home/eugen/automation/automationservicehub/app.yml'
					sh 'scp -r -P 22 /opt/tomcat/automation/automationservicehub/src/main/docker/mysql.yml eugen@192.168.175.49:/home/eugen/automation/automationservicehub/mysql.yml'
					sh 'ssh -T -R 5000:localhost:5000 eugen@192.168.175.49 docker pull localhost:5000/automationservicehub'
					sh 'ssh -T -R 5000:localhost:5000 eugen@192.168.175.49 docker-compose -f /home/eugen/automation/automationservicehub/app.yml up -d'
				}
			}
		}
    }
}
