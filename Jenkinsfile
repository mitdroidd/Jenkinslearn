pipeline {
    agent any

    tools {
        // EDIT ME: name must match what you configure under
        // Manage Jenkins > Tools > NodeJS installations
        nodejs 'node20'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend: Build (Maven)') {
            steps {
                dir('backend') {
                    sh 'mvn -B clean package'
                }
            }
        }

        stage('Frontend: Install & Build (npm)') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'backend/target/*.war', fingerprint: true
                archiveArtifacts artifacts: 'frontend/build/**', fingerprint: true
            }
        }

        stage('Deploy to local Tomcat') {
            steps {
                // EDIT ME: adjust path/permissions to match your Tomcat setup.
                // Jenkins' service user needs write access to this folder,
                // e.g. `sudo usermod -aG tomcat jenkins` then a re-login,
                // or loosen webapps folder permissions for local practice only.
                sh 'cp backend/target/greeting-app.war /var/lib/tomcat10/webapps/'
            }
        }
    }

    post {
        success {
            echo 'Build and deploy succeeded.'
        }
        failure {
            echo 'Build failed - check the stage logs above.'
        }
    }
}
