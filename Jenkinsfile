pipeline{
    agent any
 
    stages{
        stage("Check SonarQube --QG"){
            agent {
                dockerr{
                    image'openjdk:08'
                   
                }
            }
            steps{
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-t') {
                        sh 'chmod +x gradlew'                      
                        sh './gradlew sonarqube --info' 
                    }
                                    

                }
            }
        }
          
            
    }
 
}