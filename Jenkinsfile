{
    agent any
    environment{
        BUILDN = "${env.BUILD_ID}"
        
    } 
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
                        sh './gradlew sonarqube' 
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
         stage("Finding misconfig in Helm"){
            steps{
                script{
                    dir('/home/joajaen1/kube') {
                        withEnv(['DATREE_TOKEN=Ljh7zme9fLxhYgSE9eDbiM']) {
                            sh ' helm datree test finalhapp/'
                        }                       
                    }
                }
            }
        }
          stage("Pushing helm into nexus"){
            steps{
                script{
                    withCredentials([string(credentialsId: 'Docker-P', variable: 'Dpass')]) {
                        dir('/home/joajaen1/kube') {
                        sh '''
                        helmversion=$( helm show chart finalhapp | grep version | cut -d: -f 2 | tr -d ' ')
                        tar -czvf  finalhapp-${helmversion}.tgz finalhapp/
                          curl -u admin:$Dpass http://34.125.67.134:8081/repository/JSDNK-helm-hosted/ --upload-file finalhapp-${helmversion}.tgz -v
                    ''' 
                        }
                    }                   

                }
            }
        }
        stage("admin approval deployment?"){
             steps{
                script{
                    timeout(5) {
                        
                        mail to: 'jajae001fp@gmail.com',
                            from: '',
                            subject: "Pipeline Approval: ${currentBuild.fullDisplayName}",
                            body: " Hey Joy, Project: ${env.JOB_NAME} , Build: ${env.BUILD_NUMBER} Deployment needs approval ... Please check ${env.BUILD_URL}"
                        
                        input( message:"Would you like to deploy ${env.JOB_NAME} Build: ${env.BUILD_NUMBER} ?", ok: "Yes!Deploy")

                    }
                }
            }

        }
        stage('Kube cluster deployment') {
            steps {
                script{
                    dir('/home/joajaen1/kube') {
                    withCredentials([kubeconfigFile(credentialsId: 'kube-config', variable: 'KUBECONFIG')]) {
                            sh 'helm upgrade --install --set image.repository="34.125.67.134:8083/fproject" --set image.tag="${BUILDN}" springapplication finalhapp/ '
                        }  
                    }

                }
            }
        } 
          
            
    }
     post {	
        always {
        mail to: 'jajae001fp@gmail.com',
             from: '',
             subject: "Pipeline Status: ${currentBuild.fullDisplayName}, ${currentBuild.result}",
             body: " Hey Joy, Your Project: ${env.JOB_NAME} , Build: ${env.BUILD_NUMBER} is a ${currentBuild.result} ... Please check ${env.BUILD_URL}"
             
        }
	}
 
}