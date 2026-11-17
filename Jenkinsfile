pipeline {
    agent any

    // Names must match Manage Jenkins > Tools
    tools {
        maven 'maven3'
        jdk   'jdk11'
    }

    environment {
        APP_NAME     = 'ecommerce'
        DOCKER_IMAGE = 'YOUR_DOCKERHUB_USER/ecommerce'
    }

    triggers {
        githubPush()   // fires when GitHub sends the webhook
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/ecommerce.war', fingerprint: true
                }
            }
        }

        // Publish Over SSH server "ansible-master": set its Remote Directory to /opt/deploy
        stage('Send WAR + Dockerfile to Ansible master') {
            steps {
                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: 'ansible-master',
                        verbose: true,
                        transfers: [
                            sshTransfer(sourceFiles: 'target/ecommerce.war', removePrefix: 'target'),
                            sshTransfer(sourceFiles: 'Dockerfile')
                        ]
                    )
                ])
            }
        }

        stage('Deploy to Tomcat (Ansible)') {
            steps {
                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: 'ansible-master',
                        transfers: [
                            sshTransfer(execCommand: 'ansible-playbook /opt/deploy/tomcat-deploy.yml')
                        ]
                    )
                ])
            }
        }

        stage('Build and push Docker image (Ansible)') {
            steps {
                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: 'ansible-master',
                        transfers: [
                            sshTransfer(execCommand: 'ansible-playbook /opt/deploy/docker-build.yml')
                        ]
                    )
                ])
            }
        }

        // Publish Over SSH server "kube-master"
        // The Deployment must already exist: kubectl apply -f deploy.yml (once)
        stage('Deploy to Kubernetes') {
            steps {
                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: 'kube-master',
                        transfers: [
                            sshTransfer(execCommand: 'kubectl rollout restart deployment/ecommerce && kubectl rollout status deployment/ecommerce')
                        ]
                    )
                ])
            }
        }
    }

    post {
        success {
            echo 'Pipeline finished: WAR on Tomcat and new image on Kubernetes.'
        }
        failure {
            echo 'Pipeline failed. Check the stage logs above.'
        }
    }
}
