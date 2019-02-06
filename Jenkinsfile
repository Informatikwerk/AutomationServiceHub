pipeline {
    agent none
    stages {
        stage('Remote Build') {
			agent any
			steps {
				sh 'ssh -T -R 5000:localhost:5000 -l eugen 192.168.175.46'
				sh 'whoami'
			}
		}
    }
}
