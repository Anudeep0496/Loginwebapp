pipeline {
    agent any  // Runs on your local Windows machine

    environment {
        AWS_REGION = 'us-east-1'
        ECR_REGISTRY = '105306707871.dkr.ecr.us-east-1.amazonaws.com'
    }

    stages {
            stage('Check Docker') {
      steps {
        bat 'docker --version'
      }
    }
        stage('Login to AWS ECR') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'aws-credentials', 
                                                usernameVariable: 'AWS_ACCESS_KEY_ID', 
                                                passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                    bat """
                    aws configure set aws_access_key_id %AWS_ACCESS_KEY_ID%
                    aws configure set aws_secret_access_key %AWS_SECRET_ACCESS_KEY%
                    aws configure set region %AWS_REGION%

                    aws ecr get-login-password --region %AWS_REGION% | docker login --username AWS --password-stdin %ECR_REGISTRY%
                    """
                }
            }
        }
        // Add other stages here (build docker image, push etc.)
    }
}
