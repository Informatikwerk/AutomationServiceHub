pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
               dir ('D:/projekte/AutomationFarm/automationservicehub') { 
               bat 'gradlew clean build -x test war'
           }
        }
    }
}
}