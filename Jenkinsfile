pipeline {
    agent none
    stages {
        stage('Remote Build') {
			agent any
			steps {
				sshagent (b857f680-137f-4664-8478-c76098a49af7)
				sh 'ssh -T -R 5000:localhost:5000 -l eugen 192.168.175.46'
				sh 'whoami'
			}
		}
    }

}
