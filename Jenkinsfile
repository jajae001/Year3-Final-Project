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
         stage("Docker image building"){
            steps{
                script{
                    withCredentials([string(credentialsId: 'Docker-P', variable: 'Dpass')]) {
                        sh '''
                          docker build -t 34.125.67.134:8083/fproject:${BUILDN} .
                          docker login -u admin -p $Dpass 34.125.67.134:8083
                          docker push 34.125.67.134:8083/fproject:${BUILDN}
                          docker rmi 34.125.67.134:8083/fproject:${BUILDN}
                    ''' 
                    }
                   

                }
            }
        }
          
            
    }
 
}