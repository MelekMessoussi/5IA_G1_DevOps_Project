pipeline {
    agent any 
    environment {
        NEXUS_URL = 'http://localhost:8081/repository/maven-releases/'
        NEXUS_ARTIFACT_PATH = 'tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar'
        DOCKER_HUB_USER = 'melekmessoussi'
        DOCKER_HUB_PASSWORD = credentials('docker_hub_password')
        NEXUS_CREDENTIALS = credentials('Nexus-credentials')
        EMAIL_CREDENTIALS = credentials('gmailcred')
        
    }
    stages {
        stage('GIT') {
            steps {
                echo "Pulling from Git..."
                git branch: 'MelekMessoussi_5IA_G1', url: 'https://github.com/MelekMessoussi/5IA_G1_DevOps_Project.git'
            }
        }

        stage('Maven') {
            steps {
                sh 'mvn clean'
                sh 'mvn compile'
            }
        }

        stage('SONARQUBE') {
            steps {
                withSonarQubeEnv('sonarqube1') { 
                    sh 'mvn sonar:sonar'
                }
            }
        }

         stage('JUnit') {
                    steps {
                        sh 'mvn test'
                    }
                }

        
        stage('Nexus') {
            steps {
                echo "Déploiement vers Nexus..."
                // Assuming you have set up your pom.xml with the correct distributionManagement configuration
                sh 'mvn deploy -DaltDeploymentRepository=deploymentRepo::default::${NEXUS_URL}'
            }
        }
        
        stage('Nexus target') {
            steps {
                echo "Downloading latest artifact from Nexus..."
                
                sh 'curl -u ${NEXUS_CREDENTIALS_USR}:${NEXUS_CREDENTIALS_PSW} -o target/DevOps_Project-1.0.jar ${NEXUS_URL}${NEXUS_ARTIFACT_PATH}'
            }
        }
        
        stage('Docker Image') {

            steps {
            
                script {
                
                    sh 'mvn clean package'
                    sh 'docker build -t melekmessoussi/melekmessoussi_5ia_g1_devops_project:1.0.0 .'
                
                }
            
            }
        
        }
        
        
        
        stage('Docker Push') {
            steps {
                script {
                    echo "Pushing Docker image to Docker Hub..."
                    sh "echo ${DOCKER_HUB_PASSWORD} | docker login -u ${DOCKER_HUB_USER} --password-stdin"
                    sh 'docker push melekmessoussi/melekmessoussi_5ia_g1_devops_project:1.0.0'
                }
            }
        }
        
        
        stage('Docker Compose') {

            steps {
            
                script {
                
                
                    sh 'docker compose up -d'
                
                }
            
            }
        
        }
        
        
        



          stage('Email Notification') {
            steps {
                script {
                    def buildStatus = currentBuild.currentResult == 'SUCCESS' ? 'Success' : 'Failure'
                    
                    mail bcc: '', 
                         body: "Build ${buildStatus}: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}\n\nPlease check the details at ${env.BUILD_URL}.", 
                         cc: '', 
                         from: "${EMAIL_CREDENTIALS_USR}", 
                         replyTo: "${EMAIL_CREDENTIALS_USR}", 
                         subject: "Jenkins Job ${buildStatus}: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}", 
                         to: 'messoussi.melek@gmail.com', 
                         smtpAuthUser: "${EMAIL_CREDENTIALS_USR}", 
                         smtpAuthPassword: "${EMAIL_CREDENTIALS_PSW}"
                }
            }
        }

               stage('Grafana') {
            steps {
                script {
                    def jobResult = currentBuild.currentResult == 'SUCCESS' ? 1 : 0
                    def jobDuration = currentBuild.duration / 1000 

                    echo "Job Success Rate: ${jobResult}"
                    echo "Job Duration: ${jobDuration} seconds"
                    
                    echo "Job Success Rate: ${jobResult}"
                    echo "Job Duration: ${jobDuration} milliseconds"
                    
                    sh "curl -X POST -d 'job_duration=${jobDuration}' http://192.168.33.10:9090/metrics"
                    

                    
                }
            }
        }
  
    }
}
