pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
               dir ('/home/eugen/automation/automationservicehub') { 
               sh 'gradlew'
           }
        }
    }
}
}