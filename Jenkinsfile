pipeline {
    agent none
	environment {
        SSH_IP = 'jenkins@157.97.108.196'
        SSH_PORT = '2807'
		REGISTRY = 'localhost:5000'
    }
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
					sh 'docker push ${REGISTRY}/automationservicehub'
					sh 'scp -r -P ${SSH_PORT} /opt/tomcat/automation/automationservicehub/src/main/docker/app.yml ${SSH_IP}:/media/app/automation/automationservicehub/app.yml'
					sh 'scp -r -P ${SSH_PORT} /opt/tomcat/automation/automationservicehub/src/main/docker/mysql.yml ${SSH_IP}:/media/app/automation/automationservicehub/mysql.yml'
					sh 'ssh -p ${SSH_PORT} -T -R  5000:${REGISTRY} ${SSH_IP} docker pull ${REGISTRY}/automationservicehub'
					sh 'ssh -p ${SSH_PORT} -T -R  5000:${REGISTRY} ${SSH_IP} docker-compose -f /media/app/automation/automationservicehub/app.yml up -d'
				}
			}
		}
    }
}
