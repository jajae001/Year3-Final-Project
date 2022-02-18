pipeline{
    agent any
 
    stages{
        stage("Check SonarQube --QG"){
            agent {
                docker {
                    image'openjdk:11'
                   
                }
            }
            steps{
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-t') {
                        sh 'chmod +x gradlew'                      
                        sh './gradlew sonarqube --info' 
                    }
                    timeout(time: 45, unit: 'MINUTES') {
                      def qg = waitForQualityGate()
                      if (qg.status != 'OK') {
                           error "Pipeline aborted as q-gate failed: ${qg.status}"
                        }

                    }
                                    

                }
            }
        }
          
            
    }
 
}